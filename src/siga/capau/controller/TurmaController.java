package siga.capau.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import siga.capau.dao.AlunoDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.TurmaDao;
import siga.capau.dao.TurmaDisciplinaDocenteDao;
import siga.capau.modelo.Curso;
import siga.capau.modelo.FiltroTurma;
import siga.capau.modelo.Turma;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/turma")
public class TurmaController {

	private List<Turma> lista_turmas;
	private List<Curso> lista_curso;
	private List<Integer> lista_anos;
	private Turma turma;
	private FiltroTurma filtro_turma;

	@Autowired
	TurmaDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	TurmaDisciplinaDocenteDao dao_turma_disciplina_docente;

	public TurmaController() {
		this.lista_anos = new ArrayList<>();
	}

	@RequestMapping("/nova")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String novaTurma(Model model) {
		this.lista_curso = dao_curso.lista();
		if (this.lista_curso.size() == 0) {
			return "redirect:/curso/novo";
		}

		listaUltimosCincoAnos();
		model.addAttribute("lista_anos", this.lista_anos);
		model.addAttribute("cursos", this.lista_curso);
		return "turma/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String adiciona(@Valid Turma turma, BindingResult result) {
		// Altera o nome da turma
		turma.setNome(dao_curso.buscaNomePorId(turma.getCurso().getId()) + " - " + turma.getAno_ingresso() + "."
				+ turma.getPeriodo_ingresso() + " - " + turma.getTipo_turma());

		if (result.hasErrors()) {
			return "redirect:nova";
		} else if (dao.buscaPorNome(turma.getNome()).size() > 0) {
			return "redirect:nova";
		}

		// Adiciona no banco de dados
		dao.adiciona(turma);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		listaUltimosCincoAnos();
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("lista_anos", this.lista_anos);
		model.addAttribute("turmas", qntAlunos(dao.listaTurmasAtivas()));
		return "turma/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String remove(Turma turma) {

		// Remove o turma caso não haja alunos cadastrados nesse turma
		if (dao_aluno.buscaAlunosPorTurma(turma.getId()).size() > 0) {
			return "redirect:lista";
		}

		dao_turma_disciplina_docente.removeTurmaDisciplinaDocentePelaTurmaId(turma.getId());
		dao.remove(turma.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("turma", dao.buscaPorId(id));
		model.addAttribute("disciplinas_docente",
				dao_turma_disciplina_docente.buscaTurmaDisciplinaDocentePorTurmaId(id));
		model.addAttribute("alunos_turma", dao_aluno.listaAlunosPorTurmaId(id));
		return "turma/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String edita(Long id, Model model) {

		// Busca a turma e altera o ano e periodo de ingresso
		this.turma = dao.buscaPorId(id);
		String nome[] = this.turma.getNome().replace(".", "#").split(" - ");
		String ingresso[] = nome[1].split("#");
		this.turma.setAno_ingresso(Integer.parseInt(ingresso[0]));
		this.turma.setPeriodo_ingresso(Integer.parseInt(ingresso[1]));
		this.turma.setTipo_turma(nome[2]);

		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("turma", this.turma);
		model.addAttribute("lista_anos", this.lista_anos);
		return "turma/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String altera(@Valid Turma turma, BindingResult result) {
		// Altera o nome da turma
		turma.setNome(dao_curso.buscaNomePorId(turma.getCurso().getId()) + " - " + turma.getAno_ingresso() + "."
				+ turma.getPeriodo_ingresso() + " - " + turma.getTipo_turma());

		this.lista_turmas = dao.buscaPorNome(turma.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + turma.getId();
		} else if (this.lista_turmas.size() > 0 && this.lista_turmas.get(0).getId() != turma.getId()) {
			return "redirect:edita?id=" + turma.getId();
		}

		dao.altera(turma);
		return "redirect:lista";
	}

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_turmas != null) {
			String nomeRelatorio = "Relatório de Turmas.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/cadastro/relatorio_turmas.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_turmas);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	private List<Integer> listaUltimosCincoAnos() {
		int ano = Calendar.getInstance().get(Calendar.YEAR);

		for (int i = 0; i < 6; i++) {
			this.lista_anos.add(ano);
			ano = ano - 1;
		}
		return this.lista_anos;
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("turmas", qntAlunos(dao.filtraTurma(trataParametrosRequest(request))));
		return "turma/import_lista/tabela";
	}

	private FiltroTurma trataParametrosRequest(HttpServletRequest request) {
		this.filtro_turma = new FiltroTurma();
		this.filtro_turma.setCurso(request.getParameter("curso"));
		this.filtro_turma.setAno_ingresso(request.getParameter("ano_ingresso"));
		this.filtro_turma.setPeriodo_ingresso(request.getParameter("periodo_ingresso"));
		this.filtro_turma.setTipo_turma(request.getParameter("tipo_turma"));
		this.filtro_turma.setSituacao(request.getParameter("situacao"));

		return this.filtro_turma;
	}

	private List<Turma> qntAlunos(List<Turma> lista) {
		this.lista_turmas = lista;
		for (Turma turma : this.lista_turmas) {
			turma.setQnt_alunos(dao_aluno.buscaQntAlunosPorTurmaId(turma.getId()));
		}
		return this.lista_turmas;
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

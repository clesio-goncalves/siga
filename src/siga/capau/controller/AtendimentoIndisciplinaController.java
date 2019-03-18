package siga.capau.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import siga.capau.dao.AtendimentoIndisciplinaDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.PerfilDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.AtendimentoIndisciplina;
import siga.capau.modelo.FiltroAtendimentoIndisciplina;
import siga.capau.modelo.Profissional;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/atendimento/indisciplina")
public class AtendimentoIndisciplinaController {

	private AtendimentoIndisciplina atendimento_indisciplina;
	private List<AtendimentoIndisciplina> lista_atendimentos_indisciplina;
	private List<Profissional> lista_profissional;
	private FiltroAtendimentoIndisciplina filtra_atendimento_indisciplina;

	@Autowired
	AtendimentoIndisciplinaDao dao;

	@Autowired
	ProfissionalDao dao_profissional;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	PerfilDao dao_perfil;

	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("/novo")
	@Secured("ROLE_Coordenação de Disciplina")
	public String novoAtendimentoIndisciplina(Model model) {
		this.lista_profissional = dao_profissional.buscaPorUsuario(retornaUsuarioLogado().getId());
		if (this.lista_profissional.size() == 0) {
			return "redirect:/profissional/novo";
		}
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("profissional", this.lista_profissional.get(0));
		return "atendimento_indisciplina/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured("ROLE_Coordenação de Disciplina")
	public String adiciona(@Valid AtendimentoIndisciplina atendimentoIndisciplina, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(atendimentoIndisciplina);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		this.lista_atendimentos_indisciplina = dao.lista();
		model.addAttribute("atendimentos_indisciplina", this.lista_atendimentos_indisciplina);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("alunos", dao_aluno.lista());
		model.addAttribute("profissionais", dao_profissional.buscaCoordenacaoDisciplina());
		return "atendimento_indisciplina/lista";
	}

	@RequestMapping("/remove")
	@Secured("ROLE_Coordenação de Disciplina")
	public String remove(AtendimentoIndisciplina AtendimentoIndisciplina) {
		dao.remove(AtendimentoIndisciplina.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model) {
		this.atendimento_indisciplina = dao.buscaPorId(id);
		model.addAttribute("atendimento_indisciplina", this.atendimento_indisciplina);
		return "atendimento_indisciplina/exibe";
	}

	@RequestMapping("/edita")
	@Secured("ROLE_Coordenação de Disciplina")
	public String edita(Long id, Model model) {
		this.atendimento_indisciplina = dao.buscaPorId(id);
		model.addAttribute("atendimento_indisciplina", this.atendimento_indisciplina);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("turmas",
				dao_turma.listaTurmaPorCursoId(this.atendimento_indisciplina.getAluno().getTurma().getCurso().getId()));
		model.addAttribute("alunos",
				dao_aluno.listaAlunosPorTurmaId(this.atendimento_indisciplina.getAluno().getTurma().getId()));
		return "atendimento_indisciplina/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured("ROLE_Coordenação de Disciplina")
	public String altera(@Valid AtendimentoIndisciplina atendimentoIndisciplina, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + atendimentoIndisciplina.getId();
		}

		// Altera no banco
		dao.altera(atendimentoIndisciplina);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_indisciplina/import_edita/turma";
		} else {
			return "atendimento_indisciplina/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_turma_lista_atendimento_indisciplina", method = RequestMethod.POST)
	public String filtrarTurmaEmListaAtendimentoIndisciplina(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		if (request.getParameter("curso") != null) {
			model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso"))));
		}
		return "atendimento_indisciplina/import_lista/import_filtro/turma";
	}

	@RequestMapping(value = "/filtro_aluno", method = RequestMethod.POST)
	public String filtrarAluno(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("turma_id") != null) {
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_indisciplina/import_edita/aluno";
		} else {
			return "atendimento_indisciplina/import_novo/aluno";
		}
	}

	@RequestMapping("/relatorio")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordenação de Disciplina" })
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_atendimentos_indisciplina != null) {
			String nomeRelatorio = "Ocorrências de Indisciplina.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/indisciplina/" + retornaCaminhoRelatorio() + ".jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_indisciplina);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	@RequestMapping(value = "/advertencia_escrita", method = RequestMethod.POST)
	@Secured("ROLE_Coordenação de Disciplina")
	public void advertenciaEscrita(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (this.atendimento_indisciplina != null) {
			this.lista_atendimentos_indisciplina = new ArrayList<AtendimentoIndisciplina>();
			this.lista_atendimentos_indisciplina.add(0, atendimento_indisciplina);
			String nomeRelatorio = "Termo de Advertência Escrita.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/indisciplina/termo_advertencia_escrita.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_indisciplina);

			parametros.put("motivo_advertencia", request.getParameter("motivo_advertencia"));
			parametros.put("precedida_advertencia_verbal", request.getParameter("precedida_advertencia_verbal"));
			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("exibe");
		}
	}

	@RequestMapping(value = "/advertencia_verbal", method = RequestMethod.POST)
	@Secured("ROLE_Coordenação de Disciplina")
	public void advertenciaVerbal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (this.atendimento_indisciplina != null) {
			this.lista_atendimentos_indisciplina = new ArrayList<AtendimentoIndisciplina>();
			this.lista_atendimentos_indisciplina.add(0, atendimento_indisciplina);
			String nomeRelatorio = "Registro de Ocorrência de Advertência Verbal.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/indisciplina/termo_advertencia_verbal.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_indisciplina);

			parametros.put("motivo_advertencia", request.getParameter("motivo_advertencia"));
			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("exibe");
		}
	}

	private String retornaCaminhoRelatorio() {
		switch (retornaUsuarioLogado().getPerfil().getId().toString()) {
		case "12":
			return "atendimento_indisciplina_cd";
		case "3":
			return "atendimento_indisciplina_diretor";
		default:
			return "atendimento_indisciplina";
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.lista_atendimentos_indisciplina = dao.filtraAtendimentoIndisciplina(trataParametrosRequest(request));
		model.addAttribute("atendimentos_indisciplina", this.lista_atendimentos_indisciplina);
		return "atendimento_indisciplina/import_lista/tabela";
	}

	private FiltroAtendimentoIndisciplina trataParametrosRequest(HttpServletRequest request) {
		this.filtra_atendimento_indisciplina = new FiltroAtendimentoIndisciplina();
		this.filtra_atendimento_indisciplina
				.setData_inicial_atendimento(request.getParameter("data_inicial_atendimento"));
		this.filtra_atendimento_indisciplina.setData_final_atendimento(request.getParameter("data_final_atendimento"));
		this.filtra_atendimento_indisciplina
				.setHorario_inicial_atendimento(request.getParameter("horario_inicial_atendimento"));
		this.filtra_atendimento_indisciplina
				.setHorario_final_atendimento(request.getParameter("horario_final_atendimento"));
		this.filtra_atendimento_indisciplina.setCurso(request.getParameter("curso"));
		this.filtra_atendimento_indisciplina.setTurma(request.getParameter("turma"));
		this.filtra_atendimento_indisciplina.setAdvertido(request.getParameter("advertido"));
		this.filtra_atendimento_indisciplina.setTipo_advertencia(request.getParameter("tipo_advertencia"));
		this.filtra_atendimento_indisciplina.setAluno(request.getParameter("aluno"));
		this.filtra_atendimento_indisciplina.setProfissional(request.getParameter("profissional"));
		this.filtra_atendimento_indisciplina.setDescricao(request.getParameter("descricao"));

		trataDatas();

		return this.filtra_atendimento_indisciplina;
	}

	private void trataDatas() {
		this.filtra_atendimento_indisciplina.setData_inicial_atendimento(
				trataDataInicial(this.filtra_atendimento_indisciplina.getData_inicial_atendimento()));
		this.filtra_atendimento_indisciplina.setData_final_atendimento(
				trataDataFinal(this.filtra_atendimento_indisciplina.getData_final_atendimento()));
	}

	private String trataDataInicial(String data_inicial) {
		// Se a data inicial não estiver sido informada, será atribuido 01/01/2018
		if (data_inicial.equals("")) {
			return "2019-01-01";
		} else {
			return this.filtra_atendimento_indisciplina.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final não estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_atendimento_indisciplina.retornaDataFinal();
		} else {
			return this.filtra_atendimento_indisciplina.formataData(data_final);
		}
	}

}

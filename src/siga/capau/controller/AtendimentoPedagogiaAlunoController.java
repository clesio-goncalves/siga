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
import siga.capau.dao.AtendimentoPedagogiaAlunoDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.PerfilDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.AtendimentoPedagogiaAluno;
import siga.capau.modelo.FiltroAtendimentoPedagogiaAluno;
import siga.capau.modelo.Profissional;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/atendimento/pedagogia/aluno")
public class AtendimentoPedagogiaAlunoController {

	private AtendimentoPedagogiaAluno atendimento_pedagogia;
	private List<AtendimentoPedagogiaAluno> lista_atendimentos_pedagogia;
	private List<Profissional> lista_profissional;
	private FiltroAtendimentoPedagogiaAluno filtra_atendimento_pedagogia;

	@Autowired
	AtendimentoPedagogiaAlunoDao dao;

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
	@Secured("ROLE_Pedagogia")
	public String novoAtendimentoPedagogiaAluno(Model model) {
		this.lista_profissional = dao_profissional.buscaPorUsuario(retornaUsuarioLogado().getId());
		if (this.lista_profissional.size() == 0) {
			return "redirect:/profissional/novo";
		}
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("profissional", this.lista_profissional.get(0));
		return "atendimento_pedagogia_aluno/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured("ROLE_Pedagogia")
	public String adiciona(@Valid AtendimentoPedagogiaAluno atendimento, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(atendimento);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordena????o de Disciplina" })
	public String lista(Model model) {
		this.lista_atendimentos_pedagogia = dao.lista();
		model.addAttribute("atendimentos_pedagogia", this.lista_atendimentos_pedagogia);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("alunos", dao_aluno.lista());
		model.addAttribute("profissionais", dao_profissional.buscaPedagogia());
		return "atendimento_pedagogia_aluno/lista";
	}

	@RequestMapping("/remove")
	@Secured("ROLE_Pedagogia")
	public String remove(AtendimentoPedagogiaAluno AtendimentoPedagogiaAluno) {
		dao.remove(AtendimentoPedagogiaAluno.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordena????o de Disciplina" })
	public String exibe(Long id, Model model) {
		this.atendimento_pedagogia = dao.buscaPorId(id);
		model.addAttribute("atendimento_pedagogia", dao.buscaPorId(id));
		return "atendimento_pedagogia_aluno/exibe";
	}

	@RequestMapping("/edita")
	@Secured("ROLE_Pedagogia")
	public String edita(Long id, Model model) {
		this.atendimento_pedagogia = dao.buscaPorId(id);
		model.addAttribute("atendimento_pedagogia", this.atendimento_pedagogia);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("turmas",
				dao_turma.listaTurmaPorCursoId(this.atendimento_pedagogia.getAluno().getTurma().getCurso().getId()));
		model.addAttribute("alunos",
				dao_aluno.listaAlunosPorTurmaId(this.atendimento_pedagogia.getAluno().getTurma().getId()));
		return "atendimento_pedagogia_aluno/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured("ROLE_Pedagogia")
	public String altera(@Valid AtendimentoPedagogiaAluno atendimento, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + atendimento.getId();
		}

		// Altera no banco
		dao.altera(atendimento);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_pedagogia_aluno/import_edita/turma";
		} else {
			return "atendimento_pedagogia_aluno/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_turma_lista_atendimento_pedagogia", method = RequestMethod.POST)
	public String filtrarTurmaEmListaAtendimentoPedagogiaAluno(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		if (request.getParameter("curso") != null) {
			model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso"))));
		}
		return "atendimento_pedagogia_aluno/import_lista/import_filtro/turma";
	}

	@RequestMapping(value = "/filtro_aluno", method = RequestMethod.POST)
	public String filtrarAluno(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("turma_id") != null) {
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_pedagogia_aluno/import_edita/aluno";
		} else {
			return "atendimento_pedagogia_aluno/import_novo/aluno";
		}
	}

	@RequestMapping("/registro_atendimento_aluno")
	@Secured("ROLE_Pedagogia")
	public void registroAtendimentoFamilia(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (this.atendimento_pedagogia != null) {
			this.lista_atendimentos_pedagogia = new ArrayList<AtendimentoPedagogiaAluno>();
			this.lista_atendimentos_pedagogia.add(0, atendimento_pedagogia);
			String nomeRelatorio = "Registro de atendimetno a fam??lia do aluno.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/pedagogia_aluno/registro_atendimento_aluno.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_pedagogia);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("exibe");
		}
	}

	@RequestMapping("/relatorio")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordena????o de Disciplina" })
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_atendimentos_pedagogia != null) {
			String nomeRelatorio = "Atendimento de Pedagogia - Aluno.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/pedagogia_aluno/" + retornaCaminhoRelatorio() + ".jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_pedagogia);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	private String retornaCaminhoRelatorio() {
		switch (retornaUsuarioLogado().getPerfil().getId().toString()) {
		case "7":
			return "atendimento_pedagogia_aluno_cp";
		case "3":
			return "atendimento_pedagogia_aluno_diretor";
		default:
			return "atendimento_pedagogia_aluno";
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.lista_atendimentos_pedagogia = dao.filtraAtendimentoPedagogiaAluno(trataParametrosRequest(request));
		model.addAttribute("atendimentos_pedagogia", this.lista_atendimentos_pedagogia);
		return "atendimento_pedagogia_aluno/import_lista/tabela";
	}

	private FiltroAtendimentoPedagogiaAluno trataParametrosRequest(HttpServletRequest request) {
		this.filtra_atendimento_pedagogia = new FiltroAtendimentoPedagogiaAluno();
		this.filtra_atendimento_pedagogia.setData_inicial_atendimento(request.getParameter("data_inicial_atendimento"));
		this.filtra_atendimento_pedagogia.setData_final_atendimento(request.getParameter("data_final_atendimento"));
		this.filtra_atendimento_pedagogia
				.setHorario_inicial_atendimento(request.getParameter("horario_inicial_atendimento"));
		this.filtra_atendimento_pedagogia
				.setHorario_final_atendimento(request.getParameter("horario_final_atendimento"));
		this.filtra_atendimento_pedagogia.setCurso(request.getParameter("curso"));
		this.filtra_atendimento_pedagogia.setTurma(request.getParameter("turma"));
		this.filtra_atendimento_pedagogia.setAssunto(request.getParameter("assunto"));
		this.filtra_atendimento_pedagogia.setAluno(request.getParameter("aluno"));
		this.filtra_atendimento_pedagogia.setProfissional(request.getParameter("profissional"));
		this.filtra_atendimento_pedagogia.setExposicao_motivos(request.getParameter("exposicao_motivos"));
		this.filtra_atendimento_pedagogia.setEncaminhamento(request.getParameter("encaminhamento"));

		trataDatas();

		return this.filtra_atendimento_pedagogia;
	}

	private void trataDatas() {
		this.filtra_atendimento_pedagogia.setData_inicial_atendimento(
				trataDataInicial(this.filtra_atendimento_pedagogia.getData_inicial_atendimento()));
		this.filtra_atendimento_pedagogia.setData_final_atendimento(
				trataDataFinal(this.filtra_atendimento_pedagogia.getData_final_atendimento()));
	}

	private String trataDataInicial(String data_inicial) {
		// Se a data inicial n??o estiver sido informada, ser?? atribuido 01/01/2019
		if (data_inicial.equals("")) {
			return "2019-01-01";
		} else {
			return this.filtra_atendimento_pedagogia.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final n??o estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_atendimento_pedagogia.retornaDataFinal();
		} else {
			return this.filtra_atendimento_pedagogia.formataData(data_final);
		}
	}

}

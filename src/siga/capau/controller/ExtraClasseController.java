package siga.capau.controller;

import java.io.IOException;
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
import siga.capau.dao.AlunoExtraClasseDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.Aluno;
import siga.capau.modelo.Docente;
import siga.capau.modelo.ExtraClasse;
import siga.capau.modelo.FiltroExtraClasse;
import siga.capau.modelo.Turma;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/atendimento/extra-classe")
public class ExtraClasseController {

	private ExtraClasse extra_classe;
	private List<ExtraClasse> lista_extra_classe;
	private FiltroExtraClasse filtra_extra_classe;
	private List<Aluno> lista_alunos;
	private Turma turma;
	private Usuario usuario;
	private Docente docente;
	private List<Docente> lista_docente;
	private boolean possui_permissao_editar = false;
	String alunos_id[];

	@Autowired
	ExtraClasseDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	DocenteDao dao_docente;

	@Autowired
	AlunoExtraClasseDao dao_aluno_extraclasse;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String novo(Model model) {
		this.usuario = retornaUsuarioLogado();

		// Caso o usu??rio seja um docente
		if (this.usuario.getPerfil().getId() == 9) {
			this.lista_docente = dao_docente.buscaPorUsuario(this.usuario.getId());
			if (this.lista_docente.size() == 1) {
				model.addAttribute("docente", this.lista_docente.get(0));
				model.addAttribute("cursos",
						dao_curso.listaCursosPorDisciplinasDoDocenteId(this.lista_docente.get(0).getId()));
			} else {
				return "redirect:/docente/novo";
			}
		} else {
			model.addAttribute("cursos", dao_curso.lista());
		}
		return "extra_classe/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String adiciona(@Valid ExtraClasse extraClasse, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		this.extra_classe = dao.adiciona(extraClasse);

		// Vincula os alunos ao atendimento de extraclasse
		adicionaAlunoExtraClasse(extraClasse);

		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordena????o de Disciplina" })
	public String lista(Model model) {
		this.usuario = retornaUsuarioLogado();

		// Se o usuario logado for docente s?? exibe os extraclasse dele
		if (this.usuario.getPerfil().getId() == 9) {
			this.lista_docente = dao_docente.buscaPorUsuario(this.usuario.getId());
			if (this.lista_docente.size() == 1) { // se h?? docente para o usu??rio cadastrado
				this.lista_extra_classe = dao.buscaPeloDocenteId(this.lista_docente.get(0).getId());
				model.addAttribute("cursos",
						dao_curso.listaCursosPorDisciplinasDoDocenteId(this.lista_docente.get(0).getId()));
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorDocenteId(this.lista_docente.get(0).getId()));
				model.addAttribute("docente", this.lista_docente.get(0));
			} else {
				return "redirect:/docente/novo";
			}
		} else {
			this.lista_extra_classe = dao.lista();
			model.addAttribute("cursos", dao_curso.lista());
			model.addAttribute("disciplinas", dao_disciplina.lista());
			model.addAttribute("docentes", dao_docente.lista());
		}
		alteraAlunosExtraClasse();
		model.addAttribute("extra_classes", this.lista_extra_classe);
		model.addAttribute("alunos", dao_aluno.lista());

		return "extra_classe/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String remove(ExtraClasse extraClasse, HttpServletResponse response) {
		if (possuiPermissao(extraClasse.getId())) {
			dao_aluno_extraclasse.removePeloAtendimentoId(extraClasse.getId());
			dao.remove(extraClasse.getId());
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordena????o de Disciplina" })
	public String exibe(Long id, Model model, HttpServletResponse response) {
		if (possuiPermissao(id)) {
			this.extra_classe = dao.buscaPorId(id);
			model.addAttribute("extra_classe", this.extra_classe);
			dadosExibir(id, model);
			return "extra_classe/exibe";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String edita(Long id, Model model, HttpServletResponse response) {
		if (possuiPermissao(id)) {
			this.extra_classe = dao.buscaPorId(id);
			model.addAttribute("extra_classe", this.extra_classe);
			this.possui_permissao_editar = true;

			if (this.usuario.getPerfil().getId() == 9) { // Docente
				model.addAttribute("docente", this.extra_classe.getDocente());
				model.addAttribute("cursos",
						dao_curso.listaCursosPorDisciplinasDoDocenteId(this.extra_classe.getDocente().getId()));

				// Se for informado que houve atendimento
				if (this.extra_classe.isStatus_atendimento() == false) {
					buscaDadosAtendimento(model);
					buscaDadosAtendimentoParaDocente(model);
				}
			} else {
				model.addAttribute("cursos", dao_curso.lista());

				// Se for informado que houve atendimento
				if (this.extra_classe.isStatus_atendimento() == false) {
					buscaDadosAtendimento(model);
					buscaDadosAtendimentoParaOutros(model);
				} else {
					model.addAttribute("docentes", dao_docente.lista());
				}
			}
			return "extra_classe/edita";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String altera(@Valid ExtraClasse extraClasse, BindingResult result, HttpServletResponse response) {
		if (this.possui_permissao_editar) {
			if (result.hasErrors()) {
				return "redirect:edita?id=" + extraClasse.getId();
			}

			// Se na edi????o marcar atendimento como n??o realizado
			if (extraClasse.isStatus_atendimento()) {
				extraClasse.setConteudo("-");
				extraClasse.setDificuldades_diagnosticadas("-");
			}

			// Altera no banco
			dao.altera(extraClasse);

			// Desvincula os alunos de AlunoExtraClasse
			dao_aluno_extraclasse.removePeloAtendimentoId(extraClasse.getId());

			// Vincula os alunos ao atendimento de extraclasse
			adicionaAlunoExtraClasse(extraClasse);

			this.possui_permissao_editar = false;
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			if (this.usuario.getPerfil().getId() == 9) { // Se Docente
				model.addAttribute("turmas",
						dao_turma.listaTurmaPorCursoIdDisciplinasDocenteId(
								Long.parseLong(request.getParameter("curso_id")),
								Long.parseLong(request.getParameter("docente_id"))));
			} else {
				model.addAttribute("turmas",
						dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
			}
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "extra_classe/import_edita/turma";
		} else {
			return "extra_classe/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_turma_lista_atendimento_extraclasse", method = RequestMethod.POST)
	public String filtrarTurmaEmListaAtendimentoExtraClasse(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		if (request.getParameter("curso") != null) {
			model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso"))));
		}
		return "extra_classe/import_lista/import_filtro/turma";
	}

	@RequestMapping(value = "/filtro_aluno", method = RequestMethod.POST)
	public String filtrarAluno(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("turma_id") != null) {
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "extra_classe/import_edita/aluno";
		} else {
			return "extra_classe/import_novo/aluno";
		}
	}

	@RequestMapping(value = "/filtro_disciplina", method = RequestMethod.POST)
	public String filtrarDisciplina(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("turma_id") != null) {
			if (this.usuario.getPerfil().getId() == 9) { // Se Docente
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorTurmaIdDisciplinasDoDocenteId(
								Long.parseLong(request.getParameter("turma_id")),
								Long.parseLong(request.getParameter("docente_id"))));
			} else {
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
			}
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "extra_classe/import_edita/disciplina";
		} else {
			return "extra_classe/import_novo/disciplina";
		}
	}

	@RequestMapping(value = "/filtro_docente", method = RequestMethod.POST)
	public String filtrarDocente(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("disciplina_id") != null) {
			model.addAttribute("docentes",
					dao_docente.listaDocentesPorDisciplinaId(Long.parseLong(request.getParameter("disciplina_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "extra_classe/import_edita/docente";
		} else {
			return "extra_classe/import_novo/docente";
		}
	}

	@RequestMapping(value = "/lista_docente", method = RequestMethod.POST)
	public String listaDocente(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("docentes", dao_docente.lista());
		if (request.getParameter("contexto").equals("edita")) {
			return "extra_classe/import_edita/docente";
		} else {
			return "extra_classe/import_novo/docente";
		}
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.lista_extra_classe = dao.filtraExtraClasse(trataParametrosRequest(request));
		alteraAlunosExtraClasse();
		model.addAttribute("extra_classes", this.lista_extra_classe);
		return "extra_classe/import_lista/tabela";
	}

	@RequestMapping("/relatorio")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordena????o de Disciplina" })
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_extra_classe != null) {
			String nomeRelatorio = "Atendimento Extraclasse.pdf";

			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/extraclasse/" + retornaCaminhoRelatorio() + ".jasper");

			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_extra_classe);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", this.usuario.getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	private String retornaCaminhoRelatorio() {
		switch (this.usuario.getPerfil().getId().toString()) {
		case "9":
			return "atendimento_extraclasse_docente";
		case "3":
			return "atendimento_extraclasse_diretor";
		case "2":
			return "atendimento_extraclasse_coordenador";
		default:
			return "atendimento_extraclasse";
		}
	}

	private FiltroExtraClasse trataParametrosRequest(HttpServletRequest request) {
		this.filtra_extra_classe = new FiltroExtraClasse();
		this.filtra_extra_classe.setData_inicial_atendimento(request.getParameter("data_inicial_atendimento"));
		this.filtra_extra_classe.setData_final_atendimento(request.getParameter("data_final_atendimento"));
		this.filtra_extra_classe.setHorario_inicial_atendimento(request.getParameter("horario_inicial_atendimento"));
		this.filtra_extra_classe.setHorario_final_atendimento(request.getParameter("horario_final_atendimento"));
		this.filtra_extra_classe.setCurso(request.getParameter("curso"));
		this.filtra_extra_classe.setTurma(request.getParameter("turma"));
		this.filtra_extra_classe.setAluno(request.getParameter("aluno"));
		this.filtra_extra_classe.setDisciplina(request.getParameter("disciplina"));
		this.filtra_extra_classe.setDocente(request.getParameter("docente"));
		this.filtra_extra_classe.setLocal(request.getParameter("local"));
		this.filtra_extra_classe.setConteudo(request.getParameter("conteudo"));
		this.filtra_extra_classe.setDificuldades_diagnosticadas(request.getParameter("dificuldades_diagnosticadas"));
		this.filtra_extra_classe.setStatus_atendimento(request.getParameter("status_atendimento"));

		trataDatas();

		return this.filtra_extra_classe;
	}

	private void trataDatas() {
		this.filtra_extra_classe
				.setData_inicial_atendimento(trataDataInicial(this.filtra_extra_classe.getData_inicial_atendimento()));
		this.filtra_extra_classe
				.setData_final_atendimento(trataDataFinal(this.filtra_extra_classe.getData_final_atendimento()));
	}

	private String trataDataInicial(String data_inicial) {
		// Se a data inicial n??o estiver sido informada, ser?? atribuido 01/01/2019
		if (data_inicial.equals("")) {
			return "2019-01-01";
		} else {
			return this.filtra_extra_classe.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final n??o estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_extra_classe.retornaDataFinal();
		} else {
			return this.filtra_extra_classe.formataData(data_final);
		}
	}

	private boolean possuiPermissao(Long id) {
		this.usuario = retornaUsuarioLogado(); // Pego o usu??rio logado
		// O docente s?? realiza a a????o se for dono do atendimento
		if (this.usuario.getPerfil().getId() == 9) {
			this.docente = dao_docente.buscaPorUsuario(this.usuario.getId()).get(0);
			if (this.docente.getId() == dao.buscaDocenteIdPeloExtraClasseId(id)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private void adicionaAlunoExtraClasse(ExtraClasse extraClasse) {
		if (!extraClasse.isStatus_atendimento()) {
			// Split lista de alunos
			this.alunos_id = extraClasse.getAlunos().split(",");

			// Adiciona Vinculo em AlunoExtraClasse
			for (String aluno_id : this.alunos_id) {
				this.dao_aluno_extraclasse.adiciona(Long.parseLong(aluno_id), this.extra_classe.getId());
			}
		}
	}

	private void dadosExibir(Long id, Model model) {
		if (!this.extra_classe.isStatus_atendimento()) {
			this.extra_classe.setAlunos("");
			for (String nome_aluno : dao_aluno.buscaNomeAlunoPorExtraClasseId(this.extra_classe.getId())) {
				this.extra_classe.setAlunos(this.extra_classe.getAlunos() + "- " + nome_aluno + "<br>");
			}
			this.turma = dao_turma.buscaTurmaPorExtraClasseId(id);
			model.addAttribute("turma", this.turma);
			model.addAttribute("curso", dao_curso.buscaPorTurmaId(this.turma.getId()));
		}
	}

	private void buscaDadosAtendimento(Model model) {
		this.lista_alunos = this.dao_aluno.buscaAlunoPorExtraClasseId(this.extra_classe.getId());
		model.addAttribute("turma_atendimento", this.lista_alunos.get(0).getTurma().getId());
		model.addAttribute("curso_atendimento", this.lista_alunos.get(0).getTurma().getCurso().getId());

		this.extra_classe.setAlunos("");
		for (Aluno aluno : this.lista_alunos) {
			this.extra_classe.setAlunos(this.extra_classe.getAlunos() + aluno.getId() + " ");
		}
	}

	private void buscaDadosAtendimentoParaDocente(Model model) {
		model.addAttribute("turmas", dao_turma.listaTurmaPorCursoIdDisciplinasDocenteId(
				this.lista_alunos.get(0).getTurma().getCurso().getId(), this.extra_classe.getDocente().getId()));
		model.addAttribute("alunos", dao_aluno.listaAlunosPorTurmaId(this.lista_alunos.get(0).getTurma().getId()));
		model.addAttribute("disciplinas", dao_disciplina.listaDisciplinasPorTurmaIdDisciplinasDoDocenteId(
				this.lista_alunos.get(0).getTurma().getId(), this.extra_classe.getDocente().getId()));
	}

	private void buscaDadosAtendimentoParaOutros(Model model) {
		model.addAttribute("turmas",
				dao_turma.listaTurmaPorCursoId(this.lista_alunos.get(0).getTurma().getCurso().getId()));
		model.addAttribute("alunos", dao_aluno.listaAlunosPorTurmaId(this.lista_alunos.get(0).getTurma().getId()));
		model.addAttribute("disciplinas",
				dao_disciplina.listaDisciplinasPorTurmaId(this.lista_alunos.get(0).getTurma().getId()));
		model.addAttribute("docentes",
				dao_docente.listaDocentesPorDisciplinaId(this.extra_classe.getDisciplina().getId()));
	}

	private void alteraAlunosExtraClasse() {
		for (ExtraClasse atendimento : this.lista_extra_classe) {
			if (!atendimento.isStatus_atendimento()) {
				atendimento.setAlunos("");
				for (String nome_aluno : dao_aluno.buscaNomeAlunoPorExtraClasseId(atendimento.getId())) {
					atendimento.setAlunos(atendimento.getAlunos() + "- " + nome_aluno + "<br>");
				}
			}
		}
	}

}

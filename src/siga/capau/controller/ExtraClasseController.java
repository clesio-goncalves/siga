package siga.capau.controller;

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

import siga.capau.dao.AlunoDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.ExtraClasse;
import siga.capau.modelo.FiltroExtraClasse;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/atendimento/extra-classe")
public class ExtraClasseController {

	private ExtraClasse extra_classe;
	private FiltroExtraClasse filtra_extra_classe;
	private Usuario usuario;

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

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String novoExtraClasse(Model model) {
		model.addAttribute("cursos", dao_curso.lista());
		return "extra_classe/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String adiciona(@Valid ExtraClasse extraClasse, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(extraClasse);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		this.usuario = retornaUsuarioLogado();

		// Se o usuario logado for docente só exibe os extraclasse dele
		if (this.usuario.getPerfil().getId() == 9) {
			model.addAttribute("extra_classes", dao.buscaPeloDocenteId(this.usuario.getId()));
		} else {
			model.addAttribute("extra_classes", dao.lista());
		}

		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("turmas", dao_turma.lista());
		model.addAttribute("alunos", dao_aluno.lista());
		model.addAttribute("disciplinas", dao_disciplina.lista());
		model.addAttribute("docentes", dao_docente.lista());
		return "extra_classe/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String remove(ExtraClasse extraClasse) {
		dao.remove(extraClasse.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model) {
		model.addAttribute("extra_classe", dao.buscaPorId(id));
		return "extra_classe/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String edita(Long id, Model model) {
		this.extra_classe = dao.buscaPorId(id);
		model.addAttribute("extra_classe", this.extra_classe);
		model.addAttribute("cursos", dao_curso.lista());
		// Se for informado que houve atendimento
		if (this.extra_classe.isStatus_atendimento() == false) {
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(this.extra_classe.getAluno().getTurma().getCurso().getId()));
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(this.extra_classe.getAluno().getTurma().getId()));
			model.addAttribute("disciplinas",
					dao_disciplina.listaDisciplinasPorTurmaId(this.extra_classe.getAluno().getTurma().getId()));
			model.addAttribute("docentes",
					dao_docente.listaDocentesPorDisciplinaId(this.extra_classe.getDisciplina().getId()));
		} else {
			model.addAttribute("docentes", dao_docente.lista());
		}
		return "extra_classe/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String altera(@Valid ExtraClasse extraClasse, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + extraClasse.getId();
		}

		// Se na edição marcar atendimento como não realizado
		if (extraClasse.isStatus_atendimento()) {
			extraClasse.setConteudo("-");
		}

		// Altera no banco
		dao.altera(extraClasse);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "extra_classe/import_edita/turma";
		} else {
			return "extra_classe/import_novo/turma";
		}
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
			model.addAttribute("disciplinas",
					dao_disciplina.listaDisciplinasPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
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
		model.addAttribute("extra_classes", dao.filtraExtraClasse(trataParametrosRequest(request)));
		return "extra_classe/import_lista/tabela";
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
		// Se a data inicial não estiver sido informada, será atribuido 01/01/2018
		if (data_inicial.equals("")) {
			return "2018-01-01";
		} else {
			return this.filtra_extra_classe.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final não estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_extra_classe.retornaDataFinal();
		} else {
			return this.filtra_extra_classe.formataData(data_final);
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

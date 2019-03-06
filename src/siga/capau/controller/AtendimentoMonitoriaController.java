package siga.capau.controller;

import java.util.List;

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
import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.AtendimentoMonitoria;
import siga.capau.modelo.FiltroAtendimentoMonitoria;
import siga.capau.modelo.Monitor;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/atendimento/monitoria")
public class AtendimentoMonitoriaController {

	private AtendimentoMonitoria atendimento_monitoria;
	private FiltroAtendimentoMonitoria filtra_atendimento_monitoria;
	private Usuario usuario;
	private List<Monitor> lista_monitor;

	@Autowired
	AtendimentoMonitoriaDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	MonitorDao dao_monitor;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	CursoDao dao_curso;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String novo(Model model) {
		this.usuario = retornaUsuarioLogado();
		// Caso o usuário seja um monitor
		if (this.usuario.getPerfil().getId() == 10) {
			this.lista_monitor = dao_monitor.buscaPorUsuario(this.usuario.getId());
			if (this.lista_monitor.size() == 1) {
				model.addAttribute("monitor", this.lista_monitor.get(0));
				model.addAttribute("cursos", dao_curso.listaCursosPorMonitorId(this.lista_monitor.get(0).getId()));
			} else {
				return "redirect:/monitor/novo";
			}
		} else {
			model.addAttribute("cursos", dao_curso.lista());
		}
		return "atendimento_monitoria/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String adiciona(@Valid AtendimentoMonitoria AtendimentoMonitoria, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("Erro ao salvar: " + result);
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(AtendimentoMonitoria);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Monitor", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {

		this.usuario = retornaUsuarioLogado();

		// Se o usuario logado for monitor só exibe os atendimentos dele
		if (this.usuario.getPerfil().getId() == 10) {
			this.lista_monitor = dao_monitor.buscaPorUsuario(this.usuario.getId());
			if (this.lista_monitor.size() == 1) { // se há monitor para o usuário cadastrado
				model.addAttribute("atendimento_monitorias", dao.buscaPeloMonitorId(this.lista_monitor.get(0).getId()));
				model.addAttribute("cursos", dao_curso.listaCursosPorMonitorId(this.lista_monitor.get(0).getId()));
				model.addAttribute("turmas", dao_turma.listaTurmasPorMonitorId(this.lista_monitor.get(0).getId()));
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorMonitorId(this.lista_monitor.get(0).getId()));
				model.addAttribute("monitor", this.lista_monitor.get(0));
			} else {
				return "redirect:/monitor/novo";
			}
		} else {
			model.addAttribute("atendimento_monitorias", dao.lista());
			model.addAttribute("cursos", dao_curso.lista());
			model.addAttribute("turmas", dao_turma.listaTurmasAtivas());
			model.addAttribute("disciplinas", dao_disciplina.lista());
			model.addAttribute("monitores", dao_monitor.lista());
		}
		model.addAttribute("alunos", dao_aluno.lista());
		return "atendimento_monitoria/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String remove(AtendimentoMonitoria AtendimentoMonitoria) {
		dao.remove(AtendimentoMonitoria.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Monitor", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model) {
		model.addAttribute("atendimento_monitoria", dao.buscaPorId(id));
		return "atendimento_monitoria/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String edita(Long id, Model model) {
		this.atendimento_monitoria = dao.buscaPorId(id);
		model.addAttribute("atendimento_monitoria", this.atendimento_monitoria);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("monitor_disciplina", this.atendimento_monitoria.getDisciplina().getMonitor().getNome());

		// Se for informado que houve atendimento
		if (this.atendimento_monitoria.isStatus_atendimento() == false) {
			model.addAttribute("turmas", dao_turma
					.listaTurmaPorCursoId(this.atendimento_monitoria.getAluno().getTurma().getCurso().getId()));
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(this.atendimento_monitoria.getAluno().getTurma().getId()));
			model.addAttribute("disciplinas", dao_disciplina
					.listaDisciplinasPorTurmaId(this.atendimento_monitoria.getAluno().getTurma().getId()));
		} else {
			model.addAttribute("disciplinas", dao_disciplina.lista());
		}

		return "atendimento_monitoria/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String altera(@Valid AtendimentoMonitoria atendimentoMonitoria, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + atendimentoMonitoria.getId();
		}

		// Se na edição marcar atendimento como não realizado
		if (atendimentoMonitoria.isStatus_atendimento()) {
			atendimentoMonitoria.setConteudo("-");
			atendimentoMonitoria.setDificuldades_diagnosticadas("-");
		}

		// Altera no banco
		dao.altera(atendimentoMonitoria);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			if (this.usuario.getPerfil().getId() == 10) { // Se Monitor
				model.addAttribute("turmas",
						dao_turma.listaTurmaPorCursoIdMonitorId(Long.parseLong(request.getParameter("curso_id")),
								Long.parseLong(request.getParameter("monitor_id"))));
			} else {
				model.addAttribute("turmas",
						dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
			}
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/turma";
		} else {
			return "atendimento_monitoria/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_aluno", method = RequestMethod.POST)
	public String filtrarAluno(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("turma_id") != null) {
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/aluno";
		} else {
			return "atendimento_monitoria/import_novo/aluno";
		}
	}

	@RequestMapping(value = "/filtro_disciplina", method = RequestMethod.POST)
	public String filtrarDisciplina(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("turma_id") != null) {
			if (this.usuario.getPerfil().getId() == 10) { // Se Monitor
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorTurmaIdMonitorId(
								Long.parseLong(request.getParameter("turma_id")),
								Long.parseLong(request.getParameter("monitor_id"))));
			} else {
				model.addAttribute("disciplinas", dao_disciplina
						.listaDisciplinasPorTurmaIdMonitorNotNull(Long.parseLong(request.getParameter("turma_id"))));
			}
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/disciplina";
		} else {
			return "atendimento_monitoria/import_novo/disciplina";
		}
	}

	@RequestMapping(value = "/filtro_monitor", method = RequestMethod.POST)
	public String filtrarMonitor(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("disciplina_id") != null) {
			model.addAttribute("monitor_disciplina",
					dao_disciplina.buscaMonitorPorDisciplinaId(Long.parseLong(request.getParameter("disciplina_id"))));
		}
		return "atendimento_monitoria/import_novo/monitor";
	}

	@RequestMapping(value = "/lista_disciplinas", method = RequestMethod.POST)
	public String listaMonitor(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("disciplinas", dao_disciplina.listaDisciplinasMonitorNotNull());
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/disciplina";
		} else {
			return "atendimento_monitoria/import_novo/disciplina";
		}
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("atendimento_monitorias", dao.filtraAtendimentoMonitoria(trataParametrosRequest(request)));
		return "atendimento_monitoria/import_lista/tabela";
	}

	private FiltroAtendimentoMonitoria trataParametrosRequest(HttpServletRequest request) {
		this.filtra_atendimento_monitoria = new FiltroAtendimentoMonitoria();
		this.filtra_atendimento_monitoria.setData_inicial_atendimento(request.getParameter("data_inicial_atendimento"));
		this.filtra_atendimento_monitoria.setData_final_atendimento(request.getParameter("data_final_atendimento"));
		this.filtra_atendimento_monitoria
				.setHorario_inicial_atendimento(request.getParameter("horario_inicial_atendimento"));
		this.filtra_atendimento_monitoria
				.setHorario_final_atendimento(request.getParameter("horario_final_atendimento"));
		this.filtra_atendimento_monitoria.setCurso(request.getParameter("curso"));
		this.filtra_atendimento_monitoria.setTurma(request.getParameter("turma"));
		this.filtra_atendimento_monitoria.setAluno(request.getParameter("aluno"));
		this.filtra_atendimento_monitoria.setDisciplina(request.getParameter("disciplina"));
		this.filtra_atendimento_monitoria.setMonitor(request.getParameter("monitor"));
		this.filtra_atendimento_monitoria.setLocal(request.getParameter("local"));
		this.filtra_atendimento_monitoria.setConteudo(request.getParameter("conteudo"));
		this.filtra_atendimento_monitoria
				.setDificuldades_diagnosticadas(request.getParameter("dificuldades_diagnosticadas"));
		this.filtra_atendimento_monitoria.setStatus_atendimento(request.getParameter("status_atendimento"));

		trataDatas();

		return this.filtra_atendimento_monitoria;
	}

	private void trataDatas() {
		this.filtra_atendimento_monitoria.setData_inicial_atendimento(
				trataDataInicial(this.filtra_atendimento_monitoria.getData_inicial_atendimento()));
		this.filtra_atendimento_monitoria.setData_final_atendimento(
				trataDataFinal(this.filtra_atendimento_monitoria.getData_final_atendimento()));
	}

	private String trataDataInicial(String data_inicial) {
		// Se a data inicial não estiver sido informada, será atribuido 01/01/2019
		if (data_inicial.equals("")) {
			return "2019-01-01";
		} else {
			return this.filtra_atendimento_monitoria.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final não estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_atendimento_monitoria.retornaDataFinal();
		} else {
			return this.filtra_atendimento_monitoria.formataData(data_final);
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

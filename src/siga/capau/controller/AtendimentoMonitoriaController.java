package siga.capau.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AlunoDao;
import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.MonitorDao;
import siga.capau.modelo.AtendimentoMonitoria;
import siga.capau.modelo.FiltroAtendimentoMonitoria;

@Transactional
@Controller
@RequestMapping("/atendimento/monitoria")
public class AtendimentoMonitoriaController {

	private Long turma_id;
	private AtendimentoMonitoria atendimento_monitoria;
	private FiltroAtendimentoMonitoria filtra_atendimento_monitoria;

	@Autowired
	AtendimentoMonitoriaDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	MonitorDao dao_monitor;

	@RequestMapping("/novo")
	public String novoAtendimentoMonitoria(Model model) {
		model.addAttribute("alunos", dao_aluno.lista());
		return "atendimento_monitoria/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	public String adiciona(@Valid AtendimentoMonitoria AtendimentoMonitoria, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(AtendimentoMonitoria);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("atendimento_monitorias", dao.lista());
		model.addAttribute("alunos", dao_aluno.lista());
		model.addAttribute("disciplinas", dao_disciplina.lista());
		model.addAttribute("monitores", dao_monitor.lista());
		return "atendimento_monitoria/lista";
	}

	@RequestMapping("/remove")
	public String remove(AtendimentoMonitoria AtendimentoMonitoria) {
		dao.remove(AtendimentoMonitoria.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("atendimento_monitoria", dao.buscaPorId(id));
		return "atendimento_monitoria/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		this.atendimento_monitoria = dao.buscaPorId(id);
		model.addAttribute("atendimento_monitoria", this.atendimento_monitoria);
		model.addAttribute("alunos", dao_aluno.lista());
		model.addAttribute("disciplinas", dao_disciplina
				.listaDisciplinasPorTurmaIdMonitorNotNull(this.atendimento_monitoria.getAluno().getTurma().getId()));
		model.addAttribute("monitor_disciplina", this.atendimento_monitoria.getDisciplina().getMonitor().getNome());
		return "atendimento_monitoria/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	public String altera(@Valid AtendimentoMonitoria AtendimentoMonitoria, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + AtendimentoMonitoria.getId();
		}

		// Altera no banco
		dao.altera(AtendimentoMonitoria);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro_disciplina", method = RequestMethod.POST)
	public String filtrarDisciplina(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("aluno_id") != null) {
			this.turma_id = dao_aluno.buscaTurmaIdPorAlunoId(Long.parseLong(request.getParameter("aluno_id")));
			model.addAttribute("disciplinas", dao_disciplina.listaDisciplinasPorTurmaIdMonitorNotNull(this.turma_id));
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
					dao_disciplina.buscaMonitorPelaDisciplinaId(Long.parseLong(request.getParameter("disciplina_id"))));
		}
		return "atendimento_monitoria/import_novo/monitor";
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
		this.filtra_atendimento_monitoria.setAluno(request.getParameter("aluno"));
		this.filtra_atendimento_monitoria.setDisciplina(request.getParameter("disciplina"));
		this.filtra_atendimento_monitoria.setMonitor(request.getParameter("monitor"));
		this.filtra_atendimento_monitoria
				.setDificuldades_diagnosticadas(request.getParameter("dificuldades_diagnosticadas"));
		this.filtra_atendimento_monitoria.setLocal(request.getParameter("local"));
		this.filtra_atendimento_monitoria.setConteudo(request.getParameter("conteudo"));

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
		// Se a data inicial não estiver sido informada, será atribuido 01/01/2018
		if (data_inicial.equals("")) {
			return "2018-01-01";
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
}

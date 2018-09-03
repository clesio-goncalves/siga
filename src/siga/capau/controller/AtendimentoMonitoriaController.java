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
import siga.capau.modelo.AtendimentoMonitoria;

@Transactional
@Controller
@RequestMapping("/atendimento/monitoria")
public class AtendimentoMonitoriaController {

	private Long turma_id;
	private AtendimentoMonitoria atendimento_monitoria;

	@Autowired
	AtendimentoMonitoriaDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	DisciplinaDao dao_disciplina;

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
}

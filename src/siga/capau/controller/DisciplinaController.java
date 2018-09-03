package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.TurmaDao;
import siga.capau.dao.TurmaDisciplinaDocenteDao;
import siga.capau.modelo.Disciplina;
import siga.capau.modelo.Docente;
import siga.capau.modelo.Turma;

@Transactional
@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {

	private List<Turma> lista_turma;
	private List<Long> lista_id_turma;
	private List<Disciplina> lista_disciplina;
	private List<Docente> lista_docente;
	private Disciplina disciplina;
	private String[] turmas_id;
	private String[] docentes_id;

	@Autowired
	DisciplinaDao dao;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	DocenteDao dao_docente;

	@Autowired
	MonitorDao dao_monitor;

	@Autowired
	TurmaDisciplinaDocenteDao dao_turma_disciplina_docente;

	@Autowired
	ExtraClasseDao dao_extraclasse;

	@Autowired
	AtendimentoMonitoriaDao dao_atendimento_monitoria;

	@RequestMapping("/nova")
	public String disciplina(Disciplina disciplina, Model model) {
		this.lista_docente = dao_docente.lista();
		this.lista_turma = dao_turma.lista();

		if (this.lista_docente.size() == 0) {
			return "redirect:/docente/novo";
		} else if (this.lista_turma.size() == 0) {
			return "redirect:/turma/nova";
		}

		model.addAttribute("turmas", this.lista_turma);
		model.addAttribute("docentes", this.lista_docente);
		model.addAttribute("monitores", dao_monitor.lista());
		return "disciplina/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	public String adiciona(@Valid Disciplina disciplina, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return "redirect:nova";
		} else if (dao.buscaPorNome(disciplina.getNome()).size() > 0) {
			return "redirect:nova";
		} else if (disciplina.getTurmas() == null) {
			return "redirect:nova";
		} else if (disciplina.getDocentes() == null) {
			return "redirect:nova";
		}

		// Adiciona disciplina
		this.disciplina = dao.adiciona(disciplina);

		// Pega as turmas e diciplinas enviadas
		this.turmas_id = disciplina.getTurmas().split(",");
		this.docentes_id = disciplina.getDocentes().split(",");

		// Intera sobre os IDs dos turmas recebidos da view
		for (int i = 0; i < turmas_id.length; i++) {
			this.dao_turma_disciplina_docente.adiciona(this.disciplina.getId(), Long.parseLong(this.turmas_id[i]),
					Long.parseLong(this.docentes_id[i]));
		}

		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("disciplinas", dao.lista());
		return "disciplina/lista";
	}

	@RequestMapping("/remove")
	public String remove(Disciplina disciplina) {
		dao_turma_disciplina_docente.removeTurmaDisciplinaDocentePelaDisciplinaId(disciplina.getId());
		dao.remove(disciplina.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("disciplina", dao.buscaPorId(id));
		model.addAttribute("monitores", dao_monitor.lista());
		model.addAttribute("turmas_docentes",
				dao_turma_disciplina_docente.buscaTurmaDisciplinaDocentePorDisciplinaId(id));
		model.addAttribute("atendimentos_extraclasse", dao_extraclasse.buscaPelaDisciplinaId(id));
		model.addAttribute("atendimentos_monitoria", dao_atendimento_monitoria.buscaPelaDisciplinaId(id));
		return "disciplina/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		// Pega a disciplina
		model.addAttribute("disciplina", dao.buscaPorId(id));

		// Pega as turmas e docentes
		model.addAttribute("turmas_docentes",
				dao_turma_disciplina_docente.buscaTurmaDisciplinaDocentePorDisciplinaId(id));

		// Pega todas as turmas e docentes sem vinculo com na disciplina em
		// TurmaDisciplinaDocente
		model.addAttribute("turmas_sem_vinculo", dao_turma.buscaTurmaSemVinculoEmTurmaDisciplinaDocente(id));
		model.addAttribute("docentes_sem_vinculo", dao_docente.buscaDocenteSemVinculoEmTurmaDisciplinaDocente(id));
		model.addAttribute("docentes", dao_docente.lista());
		model.addAttribute("monitores", dao_monitor.lista());

		return "disciplina/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	public String altera(@Valid Disciplina disciplina, BindingResult result) {
		this.lista_disciplina = dao.buscaPorNome(disciplina.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (this.lista_disciplina.size() > 0 && this.lista_disciplina.get(0).getId() != disciplina.getId()) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (disciplina.getTurmas() == null) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (disciplina.getDocentes() == null) {
			return "redirect:edita?id=" + disciplina.getId();
		}

		// Altera a disciplina
		dao.altera(disciplina);

		// Pega as turmas e diciplinas enviadas
		this.turmas_id = disciplina.getTurmas().split(",");
		this.docentes_id = disciplina.getDocentes().split(",");

		// Buscar todos as turmas ID por disciplina
		this.lista_id_turma = dao_turma_disciplina_docente.buscaTurmaPorDisciplinaId(disciplina.getId());

		// Adiciona ou altera todos as TurmaDisciplinaDocente foram passados pela view
		for (int i = 0; i < turmas_id.length; i++) {
			// Se a turma tiver cadastrada, só altera o docente
			if (this.lista_id_turma.contains(Long.parseLong(this.turmas_id[i]))) {
				this.dao_turma_disciplina_docente.alteraDocente(disciplina.getId(), Long.parseLong(this.turmas_id[i]),
						Long.parseLong(this.docentes_id[i]));
			} else { // Se a turma não tiver cadastrada, cadastrá-la
				this.dao_turma_disciplina_docente.adiciona(disciplina.getId(), Long.parseLong(this.turmas_id[i]),
						Long.parseLong(this.docentes_id[i]));
			}

			// Remove da lista_id_turma
			if (this.lista_id_turma.size() > 0) {
				this.lista_id_turma.remove(this.lista_id_turma.indexOf(Long.parseLong(this.turmas_id[i])));
			}
		}

		// Remove todas as turmas q foram removidas na alteração
		for (Long id : this.lista_id_turma) {
			System.out.println("remove turmas_id");
			this.dao_turma_disciplina_docente.removeTurmaDisciplinaDocentePelaDisciplinaIdAndTurmaId(disciplina.getId(),
					id);
		}

		return "redirect:lista";
	}

}

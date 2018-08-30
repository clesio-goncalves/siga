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

import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.TurmaDao;
import siga.capau.dao.TurmaDisciplinaDocenteDao;
import siga.capau.modelo.Disciplina;
import siga.capau.modelo.Docente;
import siga.capau.modelo.Turma;
import siga.capau.modelo.TurmaDisciplinaDocente;

@Transactional
@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {

	private List<Turma> lista_turma;
	private List<Long> lista_id_turma;
	private List<Disciplina> lista_disciplina;
	private List<Docente> lista_docente;
	private Turma turma;
	private Disciplina disciplina;
	private Docente docente;
	private TurmaDisciplinaDocente turma_disciplina_docente;
	private String[] turmas_id;
	private String[] docentes_id;

	@Autowired
	DisciplinaDao dao;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	DocenteDao dao_docente;

	@Autowired
	TurmaDisciplinaDocenteDao dao_turma_disciplina_docente;

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
			this.turma = dao_turma.buscaPorId(Long.parseLong(this.turmas_id[i]));
			this.docente = dao_docente.buscaPorId(Long.parseLong(this.docentes_id[i]));

			// Adiciona os relacionamentos na tabela TurmaDisciplinaDocente
			this.turma_disciplina_docente = new TurmaDisciplinaDocente();
			this.turma_disciplina_docente.setDisciplina(this.disciplina);
			this.turma_disciplina_docente.setTurma(this.turma);
			this.turma_disciplina_docente.setDocente(this.docente);
			this.dao_turma_disciplina_docente.adiciona(this.turma_disciplina_docente);
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
		dao.remove(disciplina);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("disciplina", dao.buscaPorId(id));
		model.addAttribute("turmas_docentes",
				dao_turma_disciplina_docente.buscaTurmaDisciplinaDocentePorDisciplinaId(id));
		return "disciplina/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {

		// Pega a disciplina
		model.addAttribute("disciplina", dao.buscaPorId(id));

		// Pega as turmas e docentes
		model.addAttribute("turmas_docentes",
				dao_turma_disciplina_docente.buscaTurmaDisciplinaDocentePorDisciplinaId(id));

		// Pega todas as turmas e docentes sem vinculo com TurmaDisciplinaDocente
		model.addAttribute("turmas_sem_vinculo", dao_turma.buscaTurmaSemVinculoEmTurmaDisciplinaDocente());
		model.addAttribute("docentes_sem_vinculo", dao_docente.buscaDocenteSemVinculoEmTurmaDisciplinaDocente());

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

		// Adiciona ou altera todos as TurmaDisciplinaDocente foram passados pela view e
		for (int i = 0; i < turmas_id.length; i++) {
			this.turma = dao_turma.buscaPorId(Long.parseLong(this.turmas_id[i]));
			this.docente = dao_docente.buscaPorId(Long.parseLong(this.docentes_id[i]));

			// Se a turma tiver cadastrada, só altera o docente
			if (this.lista_id_turma.contains(Long.parseLong(this.turmas_id[i]))) {

			} else { // Se a turma não tiver cadastrada, cadastrá-la

				// Adiciona os relacionamentos na tabela TurmaDisciplinaDocente
				this.turma_disciplina_docente = new TurmaDisciplinaDocente();
				this.turma_disciplina_docente.setDisciplina(this.lista_disciplina.get(0));
				this.turma_disciplina_docente.setTurma(this.turma);
				this.turma_disciplina_docente.setDocente(this.docente);
				this.dao_turma_disciplina_docente.adiciona(this.turma_disciplina_docente);
			}

			// Remove da lista_id_turma
			this.lista_id_turma.remove(this.lista_id_turma.indexOf(Long.parseLong(this.turmas_id[i])));
		}

		// -------------

		// Intera sobre os IDs dos turmas recebidos da view
		for (int i = 0; i < turmas_id.length; i++) {

			// Remove todos as TurmaDisciplinaDocente que não foram passados pela view e
			// estão cadastradas

			this.turma = dao_turma.buscaPorId(Long.parseLong(this.turmas_id[i]));
			this.docente = dao_docente.buscaPorId(Long.parseLong(this.docentes_id[i]));

			// Adiciona os relacionamentos na tabela TurmaDisciplinaDocente
			this.turma_disciplina_docente = new TurmaDisciplinaDocente();
			this.turma_disciplina_docente.setDisciplina(this.lista_disciplina.get(0));
			this.turma_disciplina_docente.setTurma(this.turma);
			this.turma_disciplina_docente.setDocente(this.docente);
			this.dao_turma_disciplina_docente.adiciona(this.turma_disciplina_docente);
		}

		// Buscar todos as turmas por disciplina
		// this.lista_turma = dao_turma.buscaTurmaPorDisciplinaId(disciplina.getId());

		// Remove todos as TurmaDisciplinaDocente que não foram passados pela view e
		// estão
		// cadastrados
//		for (Turma turma : this.lista_turma) {
//			if (!disciplina.getLista_turmas().contains(turma.getNome())) {
//				dao_turma_disciplina_docente.removeTurmaDisciplinaDocente(turma.getId(), disciplina.getId(), null);
//			} else {
//				disciplina.getLista_turmas().remove(disciplina.getLista_turmas().indexOf(turma.getNome()));
//			}
//		}

//		// Insere novos turmas para a disciplina que foram recebidos da view
//		for (String nome_turma : disciplina.getLista_turmas()) {
//			this.turma = dao_turma.buscaTurmaPorNome(nome_turma);
//			// Adiciona os relacionamentos na tabela TurmaDisciplina
//			this.turma_disciplina_docente = new TurmaDisciplinaDocente();
//			this.turma_disciplina_docente.setDisciplina(disciplina);
//			this.turma_disciplina_docente.setTurma(this.turma);
//			this.dao_turma_disciplina_docente.adiciona(this.turma_disciplina_docente);
//		}

		return "redirect:lista";
	}

}

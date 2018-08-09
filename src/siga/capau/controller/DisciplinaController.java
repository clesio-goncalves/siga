package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.TurmaDao;
import siga.capau.dao.TurmaDisciplinaDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.modelo.Turma;
import siga.capau.modelo.TurmaDisciplina;
import siga.capau.modelo.Disciplina;

@Transactional
@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {

	private List<Turma> lista_turma;
	private List<Disciplina> lista_disciplina;
	private Turma turma;
	private TurmaDisciplina turma_disciplina;
	private Disciplina disciplina;

	@Autowired
	DisciplinaDao dao;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	TurmaDisciplinaDao dao_turma_disciplina;

	@RequestMapping("/nova")
	public String disciplina(Disciplina disciplina, Model model) {
		this.lista_turma = dao_turma.lista();

		if (this.lista_turma.size() == 0) {
			return "redirect:/turma/novo";
		}

		disciplina.setTurma(this.lista_turma);
		model.addAttribute("disciplina", disciplina);
		return "disciplina/novo";
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid Disciplina disciplina, BindingResult result) {

		if (result.hasErrors() || dao.buscaPorNome(disciplina.getNome()).size() > 0
				|| disciplina.getLista_turmas().size() == 0) {
			return "redirect:nova";
		}

		// Adiciona disciplina
		this.disciplina = dao.adiciona(disciplina);

		// Intera sobre os IDs dos turmas recebidos da view
		for (String id_turma : disciplina.getLista_turmas()) {
			this.turma = dao_turma.buscaPorId(Long.parseLong(id_turma));

			// Adiciona os relacionamentos na tabela TurmaDisciplina
			this.turma_disciplina = new TurmaDisciplina();
			this.turma_disciplina.setDisciplina(this.disciplina);
			this.turma_disciplina.setTurma(this.turma);
			this.dao_turma_disciplina.adiciona(this.turma_disciplina);
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
		dao_turma_disciplina.removeTurmaDisciplinaPelaDisciplinaId(disciplina.getId());
		dao.remove(disciplina);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		this.disciplina = dao.buscaPorId(id);
		this.disciplina.setTurma(dao_turma.buscaTurmaPorDisciplinaId(id));

		model.addAttribute("disciplina", this.disciplina);
		return "disciplina/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {

		// Pega a disciplina com todos os turmas
		this.disciplina = dao.buscaPorId(id);
		this.disciplina.setLista_turmas(dao_turma.buscaTurmaPorDisciplinaIdString(id));
		model.addAttribute("disciplina", this.disciplina);

		// Pega todos os turmas
		this.lista_turma = dao_turma.lista();
		model.addAttribute("lista_todos_turmas", this.lista_turma);

		return "disciplina/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid Disciplina disciplina, BindingResult result) {
		this.lista_disciplina = dao.buscaPorNome(disciplina.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (disciplina.getLista_turmas().size() == 0) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (this.lista_disciplina.size() > 0 && this.lista_disciplina.get(0).getId() != disciplina.getId()) {
			return "redirect:edita?id=" + disciplina.getId();
		}

		// Altera a disciplina
		dao.altera(disciplina);

		// Buscar todos os turmas por disciplina
		this.lista_turma = dao_turma.buscaTurmaPorDisciplinaId(disciplina.getId());

		// Remove todos os TurmaDisciplina que não foram passados pela view e estão
		// cadastrados
		for (Turma turma : this.lista_turma) {
			if (!disciplina.getLista_turmas().contains(turma.getNome())) {
				dao_turma_disciplina.removeTurmaDisciplina(turma.getId(), disciplina.getId());
			} else {
				disciplina.getLista_turmas().remove(disciplina.getLista_turmas().indexOf(turma.getNome()));
			}
		}

		// Insere novos turmas para a disciplina que foram recebidos da view
		for (String nome_turma : disciplina.getLista_turmas()) {
			this.turma = dao_turma.buscaTurmaPorNome(nome_turma);
			// Adiciona os relacionamentos na tabela TurmaDisciplina
			this.turma_disciplina = new TurmaDisciplina();
			this.turma_disciplina.setDisciplina(disciplina);
			this.turma_disciplina.setTurma(this.turma);
			this.dao_turma_disciplina.adiciona(this.turma_disciplina);
		}

		return "redirect:lista";
	}

}

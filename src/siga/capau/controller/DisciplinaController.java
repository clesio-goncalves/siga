package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.CursoDao;
import siga.capau.dao.CursoDisciplinaDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.modelo.Curso;
import siga.capau.modelo.CursoDisciplina;
import siga.capau.modelo.Disciplina;

@Transactional
@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {

	private List<Curso> lista_curso;
	private List<Disciplina> lista_disciplina;
	private Curso curso;
	private CursoDisciplina curso_disciplina;
	private Disciplina disciplina;

	@Autowired
	DisciplinaDao dao;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	CursoDisciplinaDao dao_curso_disciplina;

	@RequestMapping("/nova")
	public String disciplina(Disciplina disciplina, Model model) {
		this.lista_curso = dao_curso.lista();

		if (this.lista_curso.size() == 0) {
			return "redirect:/curso/novo";
		}

		disciplina.setCurso(this.lista_curso);
		model.addAttribute("disciplina", disciplina);
		return "disciplina/novo";
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid Disciplina disciplina, BindingResult result) {

		if (result.hasErrors() || dao.buscaPorNome(disciplina.getNome()).size() > 0
				|| disciplina.getLista_cursos().size() == 0) {
			return "redirect:nova";
		}

		// Adiciona disciplina
		this.disciplina = dao.adiciona(disciplina);

		// Intera sobre os IDs dos cursos recebidos da view
		for (String id_curso : disciplina.getLista_cursos()) {
			this.curso = dao_curso.buscaPorId(Long.parseLong(id_curso));

			// Adiciona os relacionamentos na tabela CursoDisciplina
			this.curso_disciplina = new CursoDisciplina();
			this.curso_disciplina.setDisciplina(this.disciplina);
			this.curso_disciplina.setCurso(this.curso);
			this.dao_curso_disciplina.adiciona(this.curso_disciplina);
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
		dao_curso_disciplina.removeCursoDisciplinaPelaDisciplinaId(disciplina.getId());
		dao.remove(disciplina);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		this.disciplina = dao.buscaPorId(id);
		this.disciplina.setCurso(dao_curso.buscaCursoPorDisciplinaId(id));

		model.addAttribute("disciplina", this.disciplina);
		return "disciplina/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {

		// Pega a disciplina com todos os cursos
		this.disciplina = dao.buscaPorId(id);
		this.disciplina.setLista_cursos(dao_curso.buscaCursoPorDisciplinaIdString(id));
		model.addAttribute("disciplina", this.disciplina);

		// Pega todos os cursos
		this.lista_curso = dao_curso.lista();
		model.addAttribute("lista_todos_cursos", this.lista_curso);

		return "disciplina/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid Disciplina disciplina, BindingResult result) {
		this.lista_disciplina = dao.buscaPorNome(disciplina.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (disciplina.getLista_cursos().size() == 0) {
			return "redirect:edita?id=" + disciplina.getId();
		} else if (this.lista_disciplina.size() > 0 && this.lista_disciplina.get(0).getId() != disciplina.getId()) {
			return "redirect:edita?id=" + disciplina.getId();
		}

		// Altera a disciplina
		dao.altera(disciplina);

		// Buscar todos os cursos por disciplina
		this.lista_curso = dao_curso.buscaCursoPorDisciplinaId(disciplina.getId());

		// Remove todos os CursoDisciplina que não foram passados pela view e estão
		// cadastrados
		for (Curso curso : this.lista_curso) {
			if (!disciplina.getLista_cursos().contains(curso.getNome())) {
				dao_curso_disciplina.removeCursoDisciplina(curso.getId(), disciplina.getId());
			} else {
				disciplina.getLista_cursos().remove(disciplina.getLista_cursos().indexOf(curso.getNome()));
			}
		}

		// Insere novos cursos para a disciplina que foram recebidos da view
		for (String nome_curso : disciplina.getLista_cursos()) {
			this.curso = dao_curso.buscaCursoPorNome(nome_curso);
			// Adiciona os relacionamentos na tabela CursoDisciplina
			this.curso_disciplina = new CursoDisciplina();
			this.curso_disciplina.setDisciplina(disciplina);
			this.curso_disciplina.setCurso(this.curso);
			this.dao_curso_disciplina.adiciona(this.curso_disciplina);
		}

		return "redirect:lista";
	}

}

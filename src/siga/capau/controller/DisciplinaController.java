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
public class DisciplinaController {

	private List<Curso> lista_curso;
	private Curso curso;
	private CursoDisciplina curso_disciplina;
	private Disciplina disciplina;

	@Autowired
	DisciplinaDao dao;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	CursoDisciplinaDao dao_curso_disciplina;

	@RequestMapping("novaDisciplina")
	public String disciplina(Disciplina disciplina, Model model) {
		lista_curso = dao_curso.lista();

		if (lista_curso.size() == 0) {
			return "redirect:novoCurso";
		}

		disciplina.setCurso(lista_curso);
		model.addAttribute("disciplina", disciplina);
		return "disciplina/novo";
	}

	@RequestMapping("adicionaDisciplina")
	public String adiciona(@Valid Disciplina disciplina, BindingResult result) {

		if (result.hasErrors() || dao.buscaPorNome(disciplina.getNome()).size() > 0
				|| disciplina.getLista_cursos().size() == 0) {
			return "redirect:novaDisciplina";
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

		return "redirect:listaDisciplinas";
	}

	@RequestMapping("listaDisciplinas")
	public String lista(Model model) {
		model.addAttribute("disciplinas", dao.lista());
		return "disciplina/lista";
	}

	@RequestMapping("removeDisciplina")
	public String remove(Disciplina disciplina) {
		dao.remove(disciplina);
		return "redirect:listaDisciplinas";
	}

	@RequestMapping("exibeDisciplina")
	public String exibe(Long id, Model model) {
		model.addAttribute("disciplina", dao.buscaPorId(id));
		return "disciplina/exibe";
	}

	@RequestMapping("editaDisciplina")
	public String edita(Long id, Model model) {
		model.addAttribute("disciplina", dao.buscaPorId(id));
		return "disciplina/edita";
	}

	@RequestMapping("alteraDisciplina")
	public String altera(@Valid Disciplina disciplina, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:editaDisciplina?id=" + disciplina.getId();
		}

		dao.altera(disciplina);
		return "redirect:listaDisciplinas";
	}

}

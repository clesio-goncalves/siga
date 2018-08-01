package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.AlunoDao;
import siga.capau.dao.CursoDao;
import siga.capau.modelo.Curso;

@Transactional
@Controller
public class CursoController {

	private List<Curso> lista_cursos;

	@Autowired
	CursoDao dao;
	
	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("novoCurso")
	public String curso() {
		return "curso/novo";
	}

	@RequestMapping("adicionaCurso")
	public String adiciona(@Valid Curso curso, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novoCurso";
		}

		// Adiciona no banco de dados
		dao.adiciona(curso);
		return "redirect:listaCursos";
	}

	@RequestMapping("listaCursos")
	public String lista(Model model) {
		lista_cursos = dao.lista();
		model.addAttribute("cursos", lista_cursos);
		return "curso/lista";
	}

	@RequestMapping("removeCurso")
	public String remove(Curso curso) {

		// Remove o curso caso não haja alunos cadastrados nesse curso
		if (dao_aluno.buscaAlunosPorCurso(curso.getId()).size() > 0) {
			return "redirect:listaCursos";
		}

		dao.remove(curso);
		return "redirect:listaCursos";
	}

	@RequestMapping("exibeCurso")
	public String exibe(Long id, Model model) {
		model.addAttribute("curso", dao.buscaPorId(id));
		return "curso/exibe";
	}

	@RequestMapping("editaCurso")
	public String edita(Long id, Model model) {
		model.addAttribute("curso", dao.buscaPorId(id));
		return "curso/edita";
	}

	@RequestMapping("alteraCurso")
	public String altera(@Valid Curso curso, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:editaCurso?id=" + curso.getId();
		}

		dao.altera(curso);
		return "redirect:listaCursos";
	}

}

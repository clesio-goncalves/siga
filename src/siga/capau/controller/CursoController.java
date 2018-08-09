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
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.Curso;

@Transactional
@Controller
@RequestMapping("/curso")
public class CursoController {

	private List<Curso> lista_curso;

	@Autowired
	CursoDao dao;

	@Autowired
	TurmaDao dao_turma;

	@RequestMapping("/novo")
	public String curso() {
		return "curso/novo";
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid Curso curso, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorNome(curso.getNome()).size() > 0) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(curso);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("cursos", dao.lista());
		return "curso/lista";
	}

	@RequestMapping("/remove")
	public String remove(Curso curso) {

		// Remove o curso caso não haja turmas cadastrados nesse curso
		if (dao_turma.buscaTurmaPorCurso(curso.getId()).size() > 0) {
			return "redirect:lista";
		}

		dao.remove(curso);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("curso", dao.buscaPorId(id));
		return "curso/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("curso", dao.buscaPorId(id));
		return "curso/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid Curso curso, BindingResult result) {
		this.lista_curso = dao.buscaPorNome(curso.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + curso.getId();
		} else if (this.lista_curso.size() > 0 && this.lista_curso.get(0).getId() != curso.getId()) {
			return "redirect:edita?id=" + curso.getId();
		}

		dao.altera(curso);
		return "redirect:lista";
	}

}

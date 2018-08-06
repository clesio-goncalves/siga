package siga.capau.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.CursoDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.modelo.Disciplina;

@Transactional
@Controller
public class DisciplinaController {

	@Autowired
	DisciplinaDao dao;

	@Autowired
	CursoDao dao_curso;

	@RequestMapping("novaDisciplina")
	public String disciplina(Disciplina disciplina, Model model) {
		if (dao_curso.lista().size() == 0) {
			return "redirect:novoCurso";
		}

		disciplina.setCursos(dao_curso.lista());
		model.addAttribute("disciplina", disciplina);
		return "disciplina/novo";
	}

	@RequestMapping("adicionaDisciplina")
	public String adiciona(@Valid Disciplina disciplina, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("Erro no cadastro da disciplina: " + result);
			return "redirect:novaDisciplina";
		}

		// Adiciona no banco de dados
		dao.adiciona(disciplina);
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

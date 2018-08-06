package siga.capau.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.AlunoDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Aluno;

@Transactional
@Controller
public class AlunoController {

	@Autowired
	AlunoDao dao;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	UsuarioDao dao_usuario;

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("novoAluno")
	public String novoAluno(Model model) {
		// Testa se há cursos cadastrados
		if (dao_curso.lista().size() == 0) {
			return "redirect:novoCurso";
		}
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAlunoSemVinculo());
		return "aluno/novo";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("adicionaAluno")
	public String adiciona(@Valid Aluno aluno, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novoAluno";
		}

		// Testa se o id do usuário é null
		if (aluno.getUsuario().getId() == null) {
			aluno.setUsuario(null);
		}

		// Adiciona no banco de dados
		dao.adiciona(aluno);
		return "redirect:listaAlunos";
	}

	@RequestMapping("listaAlunos")
	public String lista(Model model) {
		model.addAttribute("alunos", dao.lista());
		return "aluno/lista";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("removeAluno")
	public String remove(Aluno aluno) {
		dao.remove(aluno);
		return "redirect:listaAlunos";
	}

	@RequestMapping("exibeAluno")
	public String exibe(Long id, Model model) {
		model.addAttribute("aluno", dao.buscaPorId(id));
		return "aluno/exibe";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("editaAluno")
	public String edita(Long id, Model model) {

		// Testa se há cursos cadastrados
		if (dao_curso.lista().size() == 0) {
			return "redirect:novoCurso";
		}

		model.addAttribute("aluno", dao.buscaPorId(id));
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAlunoSemVinculo());
		return "aluno/edita";

	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("alteraAluno")
	public String altera(@Valid Aluno aluno, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:editaAluno?id=" + aluno.getId();
		}

		// Testa se o id do usuário é null
		if (aluno.getUsuario().getId() == null) {
			aluno.setUsuario(null);
		}

		// Altera no banco
		dao.altera(aluno);
		return "redirect:listaAlunos";

	}
}

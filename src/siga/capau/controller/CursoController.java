package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AlunoDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.Curso;
import siga.capau.modelo.Turma;

@Transactional
@Controller
@RequestMapping("/curso")
public class CursoController {

	private List<Curso> lista_curso;
	private List<Turma> lista_turma;

	@Autowired
	CursoDao dao;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String curso() {
		return "curso/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
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

		this.lista_curso = dao.lista();
		for (Curso curso : this.lista_curso) {
			curso.setQnt_turmas(dao_turma.buscaQntTurmasPorCursoId(curso.getId()));
		}

		model.addAttribute("cursos", dao.lista());
		return "curso/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String remove(Curso curso) {

		// Remove o curso caso não haja turmas cadastrados nesse curso
		if (dao_turma.listaTurmaPorCursoId(curso.getId()).size() > 0) {
			return "redirect:lista";
		}

		dao.remove(curso.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		// QNT Alunos por turma
		this.lista_turma = dao_turma.listaTurmaPorCursoId(id);
		for (Turma turma : this.lista_turma) {
			turma.setQnt_alunos(dao_aluno.buscaQntAlunosPorTurmaId(turma.getId()));
		}
		model.addAttribute("turmas_curso", dao_turma.listaTurmaPorCursoId(id));
		
		model.addAttribute("curso", dao.buscaPorId(id));
		model.addAttribute("qnt_turmas", dao_turma.buscaQntTurmasPorCursoId(id));
		return "curso/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String edita(Long id, Model model) {
		model.addAttribute("curso", dao.buscaPorId(id));
		return "curso/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
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

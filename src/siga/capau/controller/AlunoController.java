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
import siga.capau.dao.AtendimentoIndisciplinaDao;
import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.AtendimentoSaudeDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.dao.TurmaDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Aluno;
import siga.capau.modelo.Turma;

@Transactional
@Controller
@RequestMapping("/aluno")
public class AlunoController {

	private List<Turma> lista_turma;

	@Autowired
	AlunoDao dao;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	ExtraClasseDao dao_extraclasse;

	@Autowired
	AtendimentoMonitoriaDao dao_atendimento_monitoria;

	@Autowired
	AtendimentoSaudeDao dao_atendimento_saude;

	@Autowired
	AtendimentoIndisciplinaDao dao_atendimento_indisciplina;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String novoAluno(Model model) {
		this.lista_turma = dao_turma.listaTurmasAtivas();
		if (this.lista_turma.size() == 0) {
			return "redirect:/turma/nova";
		}
		model.addAttribute("turmas", this.lista_turma);
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAlunoSemVinculo());
		return "aluno/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String adiciona(@Valid Aluno aluno, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Testa se o id do usuário é null
		if (aluno.getUsuario().getId() == null) {
			aluno.setUsuario(null);
		}

		// Adiciona no banco de dados
		dao.adiciona(aluno);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("alunos", dao.lista());
		return "aluno/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String remove(Aluno aluno) {
		dao.remove(aluno.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("aluno", dao.buscaPorId(id));
		model.addAttribute("atendimentos_extraclasse", dao_extraclasse.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_monitoria", dao_atendimento_monitoria.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_saude", dao_atendimento_saude.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_indisciplina", dao_atendimento_indisciplina.buscaPeloAlunoId(id));
		return "aluno/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String edita(Long id, Model model) {

		// Testa se há turmas cadastrados
		if (dao_turma.listaTurmasAtivas().size() == 0) {
			return "redirect:/turma/nova";
		}

		model.addAttribute("aluno", dao.buscaPorId(id));
		model.addAttribute("turmas", dao_turma.listaTurmasAtivas());
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAlunoSemVinculo());
		return "aluno/edita";

	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String altera(@Valid Aluno aluno, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:edita?id=" + aluno.getId();
		}

		// Testa se o id do usuário é null
		if (aluno.getUsuario().getId() == null) {
			aluno.setUsuario(null);
		}

		// Altera no banco
		dao.altera(aluno);
		return "redirect:lista";

	}
}

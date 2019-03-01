package siga.capau.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.AdministracaoDao;
import siga.capau.dao.AlunoDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.modelo.Administracao;
import siga.capau.modelo.Aluno;
import siga.capau.modelo.Docente;
import siga.capau.modelo.Monitor;
import siga.capau.modelo.Profissional;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/perfil")
public class PerfilController {

	private Usuario usuario;
	private List<Administracao> lista_administracao;
	private List<Profissional> lista_profissional;
	private List<Docente> lista_docente;
	private List<Monitor> lista_monitor;
	private List<Aluno> lista_aluno;

	@Autowired
	AdministracaoDao dao_administracao;

	@Autowired
	ProfissionalDao dao_profissional;

	@Autowired
	DocenteDao dao_docente;

	@Autowired
	MonitorDao dao_monitor;

	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("/usuario")
	public String perfil(Model model, HttpServletResponse response) {
		return retornaContaVinculadaPeloUsuarioId(response);
	}

	private String retornaContaVinculadaPeloUsuarioId(HttpServletResponse response) {
		this.usuario = retornaUsuarioLogado();
		switch (Integer.parseInt(this.usuario.getPerfil().getId().toString())) {
		case 1: // Administrador
			return administracao(response);
		case 2: // Coordenador
			return administracao(response);
		case 3: // Diretor
			return administracao(response);
		case 4: // Psicologia
			return profissional(response);
		case 5: // Assistência Social
			return profissional(response);
		case 6: // Enfermagem
			return profissional(response);
		case 7: // Pedagogia
			return profissional(response);
		case 8: // Odontologia
			return profissional(response);
		case 9: // Docente
			return docente(response);
		case 10: // Monitor
			return monitor(response);
		case 11: // Aluno
			return aluno(response);
		case 12: // Coordenação de Disciplina
			return profissional(response);
		default:
			response.setStatus(404);
			return "redirect:/404";
		}
	}

	private String administracao(HttpServletResponse response) {
		this.lista_administracao = dao_administracao.buscaPorUsuario(this.usuario.getId());
		if (this.lista_administracao.size() == 1) {
			return "redirect:/administracao/exibe?id=" + this.lista_administracao.get(0).getId();
		} else {
			response.setStatus(404);
			return "redirect:/404";
		}
	}

	private String profissional(HttpServletResponse response) {
		this.lista_profissional = dao_profissional.buscaPorUsuario(this.usuario.getId());
		if (this.lista_profissional.size() == 1) {
			return "redirect:/profissional/exibe?id=" + this.lista_profissional.get(0).getId();
		} else {
			response.setStatus(404);
			return "redirect:/404";
		}
	}

	private String docente(HttpServletResponse response) {
		this.lista_docente = dao_docente.buscaPorUsuario(this.usuario.getId());
		if (this.lista_docente.size() == 1) {
			return "redirect:/docente/exibe?id=" + this.lista_docente.get(0).getId();
		} else {
			response.setStatus(404);
			return "redirect:/404";
		}
	}

	private String monitor(HttpServletResponse response) {
		this.lista_monitor = dao_monitor.buscaPorUsuario(this.usuario.getId());
		if (this.lista_monitor.size() == 1) {
			return "redirect:/monitor/exibe?id=" + this.lista_monitor.get(0).getId();
		} else {
			response.setStatus(404);
			return "redirect:/404";
		}
	}

	private String aluno(HttpServletResponse response) {
		this.lista_aluno = dao_aluno.buscaPorUsuario(this.usuario.getId());
		if (this.lista_aluno.size() == 1) {
			return "redirect:/aluno/exibe?id=" + this.lista_aluno.get(0).getId();
		} else {
			response.setStatus(404);
			return "redirect:/404";
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

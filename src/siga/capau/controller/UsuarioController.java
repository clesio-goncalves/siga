package siga.capau.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AdministracaoDao;
import siga.capau.dao.PerfilDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private List<Usuario> lista_usuario;

	@Autowired
	UsuarioDao dao;

	@Autowired
	PerfilDao dao_perfil;

	@Autowired
	AdministracaoDao dao_administracao;

	@Autowired
	AdministracaoDao dao_coordenador;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String novoUsuario(Model model) {
		switch (retornaUsuarioLogado().getPerfil().getNome()) {
		case "ROLE_Administrador":
			model.addAttribute("perfis", dao_perfil.lista());
			break;
		case "ROLE_Coordenador":

			break;
		case "ROLE_Diretor":

			break;
		case "ROLE_Psicologia":

			break;
		case "ROLE_Assistência Social":

			break;
		case "ROLE_Enfermagem":

			break;
		case "ROLE_Pedagogia":

			break;
		case "ROLE_Odontologia":

			break;
		case "ROLE_Docente":

			break;
		case "ROLE_Monitor":

			break;
		case "ROLE_Coordenação de Disciplina":

			break;

		default:
			break;
		}

		return "usuario/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String adiciona(@Valid Usuario usuario, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (usuario.comparaSenhas() == false) {
			return "redirect:novo";
		} else if (dao.buscaPorEmail(usuario.getEmail()).size() > 0) {
			return "redirect:novo";
		}
		// aplica o hash a senha fornecida
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

		// Adiciona no banco de dados
		dao.adiciona(usuario);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		model.addAttribute("usuarios", dao.lista());
		return "usuario/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String remove(Usuario usuario, HttpServletResponse response) {
		dao.remove(usuario.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model) {
		model.addAttribute("usuario", dao.buscaPorId(id));
		return "usuario/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String edita(Long id, Model model) {
		model.addAttribute("usuario", dao.buscaPorId(id));
		model.addAttribute("perfis", dao_perfil.lista());
		return "usuario/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String altera(@Valid Usuario usuario, BindingResult result) {
		this.lista_usuario = dao.buscaPorEmail(usuario.getEmail());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + usuario.getId();
		} else if (usuario.comparaSenhas() == false) {
			return "redirect:edita?id=" + usuario.getId();
		} else if (this.lista_usuario.size() > 0 && this.lista_usuario.get(0).getId() != usuario.getId()) {
			return "redirect:edita?id=" + usuario.getId();
		}

		// aplica o hash a senha fornecida
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

		// Altera no banco
		dao.altera(usuario);
		return "redirect:lista";

	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
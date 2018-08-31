package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

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

	// @Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("/novo")
	public String novoUsuario(Model model) {
		model.addAttribute("perfis", dao_perfil.lista());
		return "usuario/novo";
	}

	// @Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("/adiciona")
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
	public String lista(Model model) {
		model.addAttribute("usuarios", dao.lista());
		return "usuario/lista";
	}

	// @Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("/remove")
	public String remove(Usuario usuario) {
		dao.remove(usuario.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("usuario", dao.buscaPorId(id));
		return "usuario/exibe";
	}

	// @Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("/edita")
	public String edita(Long id, Model model) {

		model.addAttribute("usuario", dao.buscaPorId(id));
		model.addAttribute("perfis", dao_perfil.lista());
		return "usuario/edita";
	}

	// @Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("/altera")
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

}
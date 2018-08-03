package siga.capau.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
public class UsuarioController {

	@Autowired
	UsuarioDao dao;

	@Autowired
	PerfilDao dao_perfil;

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("novoUsuario")
	public String novoUsuario(Model model) {

		model.addAttribute("perfis", dao_perfil.lista());

		return "usuario/novo";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("adicionaUsuario")
	public String adiciona(@Valid Usuario usuario, BindingResult result) {

		if (result.hasErrors() || usuario.comparaSenhas() == false) {
			return "redirect:novoUsuario";
		}

		// aplica o hash a senha fornecida
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

		// Adiciona no banco de dados
		dao.adiciona(usuario);
		return "redirect:listaUsuarios";
	}

	@RequestMapping("listaUsuarios")
	public String lista(Model model) {
		model.addAttribute("usuarios", dao.lista());
		return "usuario/lista";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("removeUsuario")
	public String remove(Usuario usuario) {
		dao.remove(usuario);
		return "redirect:listaUsuarios";
	}

	@RequestMapping("exibeUsuario")
	public String exibe(Long id, Model model) {
		model.addAttribute("usuario", dao.buscaPorId(id));
		return "usuario/exibe";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("editaUsuario")
	public String edita(Long id, Model model) {

		model.addAttribute("usuario", dao.buscaPorId(id));
		model.addAttribute("perfis", dao_perfil.lista());
		return "usuario/edita";
	}

	@Secured("hasRole('ROLE_Administrador')")
	@RequestMapping("alteraUsuario")
	public String altera(@Valid Usuario usuario, BindingResult result) {

		if (result.hasErrors() || usuario.comparaSenhas() == false) {
			return "redirect:editaUsuario?id=" + usuario.getId();
		}

		// aplica o hash a senha fornecida
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));

		// Altera no banco
		dao.altera(usuario);
		return "redirect:listaUsuarios";

	}

}

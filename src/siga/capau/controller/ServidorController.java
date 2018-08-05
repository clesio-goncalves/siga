package siga.capau.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.ServidorDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Servidor;

@Transactional
@Controller
public class ServidorController {
	@Autowired
	ServidorDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("novoServidor")
	public String novoServidor(Model model) {
		// Testa se há usuários cadastrados
		if (dao_usuario.listaUsuarioServidorSemVinculo().size() == 0) {
			return "redirect:novoUsuario";
		} else {
			model.addAttribute("usuarios", dao_usuario.listaUsuarioServidorSemVinculo("Coordenador"));
			return "servidor/novo";
		}
	}

	@RequestMapping("adicionaServidor")
	public String adiciona(@Valid Servidor servidor, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novoServidor";
		}

		// Adiciona no banco de dados
		dao.adiciona(servidor);
		return "redirect:listaServidores";
	}

	@RequestMapping("listaServidores")
	public String lista(Model model) {
		model.addAttribute("servidores", dao.lista());
		return "servidor/lista";
	}

	@RequestMapping("removeServidor")
	public String remove(Servidor servidor) {
		dao.remove(servidor);
		return "redirect:listaServidores";
	}

	@RequestMapping("exibeServidor")
	public String exibe(Long id, Model model) {
		model.addAttribute("servidor", dao.buscaPorId(id));
		return "servidor/exibe";
	}

	@RequestMapping("editaServidor")
	public String edita(Long id, Model model) {
		model.addAttribute("servidor", dao.buscaPorId(id));
		return "servidor/edita";
	}

	@RequestMapping("alteraServidor")
	public String altera(@Valid Servidor servidor, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:editaServidor?id=" + servidor.getId();
		}

		// Altera no banco
		dao.altera(servidor);
		return "redirect:listaServidores";
	}

	@RequestMapping(value = "filtrarUsuariosServidor", method = RequestMethod.POST)
	public String filtrar(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("usuarios", dao_usuario.listaUsuarioServidorSemVinculo(request.getParameter("funcao")));
		return "servidor/lista_usuarios";
	}

}

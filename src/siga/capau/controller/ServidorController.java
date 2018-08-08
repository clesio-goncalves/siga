package siga.capau.controller;

import java.util.List;

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
@RequestMapping("/servidor")
public class ServidorController {

	private List<Servidor> lista_servidor;

	@Autowired
	ServidorDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("/novo")
	public String novoServidor(Model model) {
		// Testa se há usuários cadastrados
		if (dao_usuario.listaUsuarioServidorSemVinculo().size() == 0) {
			return "redirect:/usuario/novo";
		} else {
			model.addAttribute("usuarios", dao_usuario.listaUsuarioServidorSemVinculo("Coordenador"));
			return "servidor/novo";
		}
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid Servidor servidor, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorSiape(servidor.getSiape()).size() > 0) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(servidor);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("servidores", dao.lista());
		return "servidor/lista";
	}

	@RequestMapping("/remove")
	public String remove(Servidor servidor) {
		dao.remove(servidor);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("servidor", dao.buscaPorId(id));
		return "servidor/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("servidor", dao.buscaPorId(id));
		return "servidor/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid Servidor servidor, BindingResult result) {
		this.lista_servidor = dao.buscaPorSiape(servidor.getSiape());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + servidor.getId();
		} else if (this.lista_servidor.size() > 0 && this.lista_servidor.get(0).getId() != servidor.getId()) {
			return "redirect:edita?id=" + servidor.getId();
		}

		// Altera no banco
		dao.altera(servidor);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro", method = RequestMethod.POST)
	public String filtrar(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("usuarios", dao_usuario.listaUsuarioServidorSemVinculo(request.getParameter("funcao")));
		return "servidor/lista_usuarios";
	}

}

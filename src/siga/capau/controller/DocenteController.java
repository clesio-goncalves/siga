package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.DocenteDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Docente;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/docente")
public class DocenteController {

	private List<Docente> lista_docente;
	private List<Usuario> lista_usuario;

	@Autowired
	DocenteDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("/novo")
	public String novoDocente(Model model) {
		this.lista_usuario = dao_usuario.listaUsuarioDocenteSemVinculo();
		if (this.lista_usuario.size() == 0) {
			return "redirect:/usuario/novo";
		} else {
			model.addAttribute("usuarios", this.lista_usuario);
			return "docente/novo";
		}
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid Docente docente, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorSiape(docente.getSiape()).size() > 0) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(docente);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("docentes", dao.lista());
		return "docente/lista";
	}

	@RequestMapping("/remove")
	public String remove(Docente docente) {
		dao.remove(docente);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("docente", dao.buscaPorId(id));
		return "docente/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("docente", dao.buscaPorId(id));
		return "docente/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid Docente docente, BindingResult result) {
		this.lista_docente = dao.buscaPorSiape(docente.getSiape());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + docente.getId();
		} else if (this.lista_docente.size() > 0 && this.lista_docente.get(0).getId() != docente.getId()) {
			return "redirect:edita?id=" + docente.getId();
		}

		// Altera no banco
		dao.altera(docente);
		return "redirect:lista";
	}

}

package siga.capau.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.ExtraClasseDao;
import siga.capau.modelo.ExtraClasse;

@Transactional
@Controller
@RequestMapping("/atendimento/extra-classe")
public class ExtraClasseController {

	@Autowired
	ExtraClasseDao dao;

	@RequestMapping("/novo")
	public String novoExtraClasse(Model model) {
		return "extra_classe/novo";
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid ExtraClasse extraClasse, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(extraClasse);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("extra_classes", dao.lista());
		return "extra_classe/lista";
	}

	@RequestMapping("/remove")
	public String remove(ExtraClasse extraClasse) {
		dao.remove(extraClasse.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("extra_classe", dao.buscaPorId(id));
		return "extra_classe/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("extra_classe", dao.buscaPorId(id));
		return "extra_classe/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid ExtraClasse extraClasse, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:edita?id=" + extraClasse.getId();
		}

		// Altera no banco
		dao.altera(extraClasse);
		return "redirect:lista";

	}

}

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

import siga.capau.dao.ProfissionalSaudeDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.ProfissionalSaude;

@Transactional
@Controller
@RequestMapping("/profissional")
public class ProfissionalSaudeController {

	@Autowired
	ProfissionalSaudeDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("/novo")
	public String novoProfissionalSaude(Model model) {

		// Testa se há usuários cadastrados
		if (dao_usuario.listaUsuarioProfissionalSaudeSemVinculo().size() == 0) {
			return "redirect:/usuario/novo";
		} else {
			model.addAttribute("usuarios", dao_usuario.listaUsuarioProfissionalSaudeSemVinculo("Psicólogo"));
			return "profissional_saude/novo";
		}
	}

	@RequestMapping("/adiciona")
	public String adiciona(@Valid ProfissionalSaude profissionalSaude, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(profissionalSaude);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("profissionais_saude", dao.lista());
		return "profissional_saude/lista";
	}

	@RequestMapping("/remove")
	public String remove(ProfissionalSaude profissionalSaude) {
		dao.remove(profissionalSaude);
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("profissional_saude", dao.buscaPorId(id));
		return "profissional_saude/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("profissional_saude", dao.buscaPorId(id));
		return "profissional_saude/edita";
	}

	@RequestMapping("/altera")
	public String altera(@Valid ProfissionalSaude profissionalSaude, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + profissionalSaude.getId();
		}

		// Altera no banco
		dao.altera(profissionalSaude);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro", method = RequestMethod.POST)
	public String filtrar(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("usuarios",
				dao_usuario.listaUsuarioProfissionalSaudeSemVinculo(request.getParameter("tipo_profissional")));
		return "profissional_saude/lista_usuarios";
	}

}

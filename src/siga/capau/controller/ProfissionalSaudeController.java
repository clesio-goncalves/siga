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
public class ProfissionalSaudeController {

	@Autowired
	ProfissionalSaudeDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("novoProfissionalSaude")
	public String novoProfissionalSaude(Model model) {

		// Testa se há usuários cadastrados
		if (dao_usuario.listaUsuarioProfissionalSaudeSemVinculo().size() == 0) {
			return "redirect:novoUsuario";
		} else {
			model.addAttribute("usuarios", dao_usuario.listaUsuarioProfissionalSaudeSemVinculo("Psicólogo"));
			return "profissional_saude/novo";
		}
	}

	@RequestMapping("adicionaProfissionalSaude")
	public String adiciona(@Valid ProfissionalSaude profissionalSaude, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novoProfissionalSaude";
		}

		// Adiciona no banco de dados
		dao.adiciona(profissionalSaude);
		return "redirect:listaProfissionalSaude";
	}

	@RequestMapping("listaProfissionalSaude")
	public String lista(Model model) {
		model.addAttribute("profissionais_saude", dao.lista());
		return "profissional_saude/lista";
	}

	@RequestMapping("removeProfissionalSaude")
	public String remove(ProfissionalSaude profissionalSaude) {
		dao.remove(profissionalSaude);
		return "redirect:listaProfissionalSaude";
	}

	@RequestMapping("exibeProfissionalSaude")
	public String exibe(Long id, Model model) {
		model.addAttribute("profissional_saude", dao.buscaPorId(id));
		return "profissional_saude/exibe";
	}

	@RequestMapping("editaProfissionalSaude")
	public String edita(Long id, Model model) {
		model.addAttribute("profissional_saude", dao.buscaPorId(id));
		return "profissional_saude/edita";
	}

	@RequestMapping("alteraProfissionalSaude")
	public String altera(@Valid ProfissionalSaude profissionalSaude, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:editaProfissionalSaude?id=" + profissionalSaude.getId();
		}

		// Altera no banco
		dao.altera(profissionalSaude);
		return "redirect:listaProfissionalSaude";

	}

	@RequestMapping(value = "filtrarUsuarios", method = RequestMethod.POST)
	public String filtrar(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		model.addAttribute("usuarios",
				dao_usuario.listaUsuarioProfissionalSaudeSemVinculo(request.getParameter("tipo_profissional")));
		return "profissional_saude/lista_usuario";

	}

}

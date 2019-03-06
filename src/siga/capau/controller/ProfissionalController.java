package siga.capau.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AtendimentoIndisciplinaDao;
import siga.capau.dao.AtendimentoSaudeDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Profissional;

@Transactional
@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

	private List<Profissional> lista_profissional;
	private Profissional profissional;

	@Autowired
	ProfissionalDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	AtendimentoSaudeDao dao_atendimento_saude;

	@Autowired
	AtendimentoIndisciplinaDao dao_atendimento_indisciplina;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String novo(Model model) {
		// Testa se há usuários cadastrados
		if (dao_usuario.listaUsuarioProfissionalSemVinculo().size() == 0) {
			return "redirect:/usuario/novo";
		}
		model.addAttribute("usuarios", dao_usuario.listaUsuarioProfissionalSemVinculo("Psicologia"));
		return "profissional/novo";

	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String adiciona(@Valid Profissional profissional, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorSiape(profissional.getSiape()).size() > 0) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(profissional);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("profissionais", dao.lista());
		return "profissional/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String remove(Profissional profissional) {
		dao.remove(profissional.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		this.profissional = dao.buscaPorId(id);
		model.addAttribute("profissional", this.profissional);
		if (this.profissional.getTipo_atendimento().equals("Coordenação de Disciplina")) {
			model.addAttribute("atendimentos_indisciplina", dao_atendimento_indisciplina.buscaPeloProfissionalId(id));
			return "profissional/exibe";
		} else if (this.profissional.getTipo_atendimento().equals("Pedagogia")) {
			return "profissional/exibe";
		} else {
			model.addAttribute("atendimentos_saude", dao_atendimento_saude.buscaPeloProfissionalId(id));
			return "profissional/exibe";
		}
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String edita(Long id, Model model) {
		model.addAttribute("profissional", dao.buscaPorId(id));
		return "profissional/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String altera(@Valid Profissional profissional, BindingResult result) {
		this.lista_profissional = dao.buscaPorSiape(profissional.getSiape());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + profissional.getId();
		} else if (this.lista_profissional.size() > 0
				&& this.lista_profissional.get(0).getId() != profissional.getId()) {
			return "redirect:edita?id=" + profissional.getId();
		}

		// Altera no banco
		dao.altera(profissional);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String filtrar(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("usuarios",
				dao_usuario.listaUsuarioProfissionalSemVinculo(request.getParameter("tipo_atendimento")));
		return "profissional/lista_usuarios";
	}

}

package siga.capau.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AdministracaoDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Administracao;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/administracao")
public class AdministracaoController {

	private List<Administracao> lista_administracao;
	private Usuario usuario;

	@Autowired
	AdministracaoDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String novoAdministracao(Model model) {
		// Testa se há usuários cadastrados
		if (dao_usuario.listaUsuarioAdministracaoSemVinculo().size() == 0) {
			return "redirect:/usuario/novo";
		} else {
			model.addAttribute("usuarios", dao_usuario.listaUsuarioAdministracaoSemVinculo("Coordenador"));
			return "administracao/novo";
		}
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String adiciona(@Valid Administracao administracao, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorSiape(administracao.getSiape()).size() > 0) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(administracao);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("lista_administracao", dao.lista());
		return "administracao/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String remove(Administracao administracao, HttpServletResponse response) {
		if (possuiPermissaoAdministracao(administracao.getId())) {
			dao.remove(administracao.getId());
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("administracao", dao.buscaPorId(id));
		return "administracao/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String edita(Long id, Model model, HttpServletResponse response) {
		if (possuiPermissaoAdministracao(id)) {
			model.addAttribute("administracao", dao.buscaPorId(id));
			return "administracao/edita";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Diretor" })
	public String altera(@Valid Administracao administracao, BindingResult result, HttpServletResponse response) {
		if (possuiPermissaoAdministracao(administracao.getId())) {
			this.lista_administracao = dao.buscaPorSiape(administracao.getSiape());
			if (result.hasErrors()) {
				return "redirect:edita?id=" + administracao.getId();
			} else if (this.lista_administracao.size() > 0
					&& this.lista_administracao.get(0).getId() != administracao.getId()) {
				return "redirect:edita?id=" + administracao.getId();
			}

			// Altera no banco
			dao.altera(administracao);
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/filtro", method = RequestMethod.POST)
	public String filtrar(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAdministracaoSemVinculo(request.getParameter("funcao")));
		return "administracao/lista_usuarios";
	}

	private boolean possuiPermissaoAdministracao(Long id) {
		this.usuario = retornaUsuarioLogado(); // Pego o usuário logado
		// O diretor só realiza a ação se o usuário não for igual a administrador ou
		// diretor
		if (this.usuario.getPerfil().getId() == 3) {
			Long perfil_id = dao.buscarPerfilIdPelaAdministracaoId(id);
			if (perfil_id == 1 || perfil_id == 3) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

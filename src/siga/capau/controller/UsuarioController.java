package siga.capau.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AdministracaoDao;
import siga.capau.dao.AlunoDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.PerfilDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private List<Usuario> lista_usuario;
	private Usuario usuario;
	private Long perfil_id;

	@Autowired
	UsuarioDao dao;

	@Autowired
	PerfilDao dao_perfil;

	@Autowired
	AdministracaoDao dao_administracao;

	@Autowired
	ProfissionalDao dao_profissional;

	@Autowired
	MonitorDao dao_monitor;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	DocenteDao dao_docente;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String novoUsuario(Model model) {
		model.addAttribute("perfis", dao_perfil.lista());
		return "usuario/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
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
		return retornaPaginaCadastro(usuario.getPerfil().getId());
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		return retornaListaUsuariosManipulaveis(model);
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String remove(Usuario usuario, HttpServletResponse response) {
		if (possuiPermissaoUsuario(usuario.getId())) {
			if (usuarioPossuiVinculo(usuario.getId()) == 0) {
				dao.remove(usuario.getId());
			}
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model, HttpServletResponse response) {
		if (possuiPermissaoUsuario(id)) {
			model.addAttribute("usuario", dao.buscaPorId(id));
			return "usuario/exibe";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String edita(Long id, Model model, HttpServletResponse response) {
		if (possuiPermissaoUsuario(id)) {
			model.addAttribute("usuario", dao.buscaPorId(id));
			return "usuario/edita";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String altera(@Valid Usuario usuario, BindingResult result, HttpServletResponse response) {
		if (possuiPermissaoUsuario(usuario.getId())) {
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
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	private String retornaListaUsuariosManipulaveis(Model model) {
		this.usuario = retornaUsuarioLogado();
		switch (this.usuario.getPerfil().getNome()) {
		case "ROLE_Administrador":
			model.addAttribute("usuarios_manipulaveis", dao.lista());
			break;
		case "ROLE_Coordenador":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioManipulavelPorCoordenadorPedagogia());
			break;
		case "ROLE_Diretor":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioManipulavelPorDiretor());
			break;
		case "ROLE_Psicologia":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioAlunoManipulavel());
			break;
		case "ROLE_Assistência Social":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioAlunoManipulavel());
			break;
		case "ROLE_Enfermagem":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioAlunoManipulavel());
			break;
		case "ROLE_Odontologia":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioAlunoManipulavel());
			break;
		case "ROLE_Pedagogia":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioManipulavelPorCoordenadorPedagogia());
			break;
		case "ROLE_Docente":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioManipulavelPorDocente());
			break;
		case "ROLE_Monitor":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioAlunoManipulavel());
			break;
		case "ROLE_Coordenação de Disciplina":
			model.addAttribute("usuarios_manipulaveis", dao.listaUsuarioManipulavelPorCD());
			break;
		default:
			break;
		}

		return "usuario/lista";
	}

	private boolean possuiPermissaoUsuario(Long id) {
		this.usuario = retornaUsuarioLogado(); // Pego o usuário logado
		this.perfil_id = dao.buscarPerfilIdPeloUsuarioId(id);
		// possui permissão de editar o usuário, caso seja ele mesmo ou seu subordinado
		switch (this.usuario.getPerfil().getNome()) {
		case "ROLE_Administrador":
			return true;
		case "ROLE_Coordenador":
			if (this.perfil_id == 9 || this.perfil_id == 10 || this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Diretor":
			if (this.perfil_id == 1 || this.perfil_id == 3) {
				return false;
			}
			return true;
		case "ROLE_Psicologia":
			if (this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Assistência Social":
			if (this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Enfermagem":
			if (this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Odontologia":
			if (this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Pedagogia":
			if (this.perfil_id == 9 || this.perfil_id == 10 || this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Docente":
			if (this.perfil_id == 10 || this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Monitor":
			if (this.perfil_id == 11) {
				return true;
			}
			return false;
		case "ROLE_Coordenação de Disciplina":
			if (this.perfil_id == 9 || this.perfil_id == 11) {
				return true;
			}
			return false;
		default:
			return false;
		}
	}

	private Long usuarioPossuiVinculo(Long id) {
		this.perfil_id = dao.buscarPerfilIdPeloUsuarioId(id);
		switch (Integer.parseInt(this.perfil_id.toString())) {
		case 1:
			return this.dao_administracao.usuarioAdministracao(id);
		case 2:
			return this.dao_administracao.usuarioAdministracao(id);
		case 3:
			return this.dao_administracao.usuarioAdministracao(id);
		case 4:
			return this.dao_profissional.usuarioProfissional(id);
		case 5:
			return this.dao_profissional.usuarioProfissional(id);
		case 6:
			return this.dao_profissional.usuarioProfissional(id);
		case 7:
			return this.dao_profissional.usuarioProfissional(id);
		case 8:
			return this.dao_profissional.usuarioProfissional(id);
		case 9:
			return this.dao_docente.usuarioDocente(id);
		case 10:
			return this.dao_monitor.usuarioMonitor(id);
		case 11:
			return this.dao_aluno.usuarioAluno(id);
		case 12:
			return this.dao_profissional.usuarioProfissional(id);
		default:
			return (long) 1;
		}
	}

	private String retornaPaginaCadastro(Long perfil_id) {
		switch (Integer.parseInt(perfil_id.toString())) {
		case 1:
			return "redirect:/administracao/novo";
		case 2:
			return "redirect:/administracao/novo";
		case 3:
			return "redirect:/administracao/novo";
		case 4:
			return "redirect:/profissional/novo";
		case 5:
			return "redirect:/profissional/novo";
		case 6:
			return "redirect:/profissional/novo";
		case 7:
			return "redirect:/profissional/novo";
		case 8:
			return "redirect:/profissional/novo";
		case 9:
			return "redirect:/docente/novo";
		case 10:
			return "redirect:/monitor/novo";
		case 11:
			return "redirect:/aluno/novo";
		case 12:
			return "redirect:/profissional/novo";
		default:
			return "redirect:lista";
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
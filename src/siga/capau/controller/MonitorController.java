package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Monitor;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/monitor")
public class MonitorController {

	private List<Usuario> lista_usuario;
	private List<Monitor> lista_monitor;

	@Autowired
	MonitorDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	AtendimentoMonitoriaDao dao_atendimento_monitoria;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String novoMonitor(Model model) {
		this.lista_usuario = dao_usuario.listaUsuarioMonitorSemVinculo();

		// Testa se há usuários do tipo monitor ou disciplinas cadastradas
		if (this.lista_usuario.size() == 0) {
			return "redirect:/usuario/novo";
		}

		model.addAttribute("usuarios", this.lista_usuario);
		return "monitor/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String adiciona(@Valid Monitor monitor, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorMatricula(monitor.getMatricula()).size() > 0) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(monitor);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		model.addAttribute("monitores", dao.lista());
		return "monitor/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String remove(Monitor monitor) {
		dao.remove(monitor.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model) {
		model.addAttribute("monitor", dao.buscaPorId(id));
		model.addAttribute("disciplinas_monitor", dao_disciplina.listaDisciplinasPorMonitorId(id));
		model.addAttribute("atendimentos_monitoria", dao_atendimento_monitoria.buscaPeloMonitorId(id));
		return "monitor/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String edita(Long id, Model model) {
		model.addAttribute("monitor", dao.buscaPorId(id));
		return "monitor/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String altera(@Valid Monitor monitor, BindingResult result) {
		this.lista_monitor = dao.buscaPorMatricula(monitor.getMatricula());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + monitor.getId();
		} else if (this.lista_monitor.size() > 0 && this.lista_monitor.get(0).getId() != monitor.getId()) {
			return "redirect:edita?id=" + monitor.getId();
		}

		// Altera no banco
		dao.altera(monitor);
		return "redirect:lista";
	}
}

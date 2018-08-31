package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Disciplina;
import siga.capau.modelo.Monitor;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
@RequestMapping("/monitor")
public class MonitorController {

	private List<Usuario> lista_usuario;
	private List<Disciplina> lista_disciplina;
	private List<Monitor> lista_monitor;

	@Autowired
	MonitorDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	DisciplinaDao dao_disciplina;

	@RequestMapping("/novo")
	public String novoMonitor(Model model) {
		this.lista_usuario = dao_usuario.listaUsuarioMonitorSemVinculo();
		this.lista_disciplina = dao_disciplina.lista();

		// Testa se há usuários do tipo monitor ou disciplinas cadastradas
		if (this.lista_usuario.size() == 0) {
			return "redirect:/usuario/novo";
		} else if (this.lista_disciplina.size() == 0) {
			return "redirect:/disciplina/nova";
		}

		model.addAttribute("disciplinas", this.lista_disciplina);
		model.addAttribute("usuarios", this.lista_usuario);
		return "monitor/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
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
	public String lista(Model model) {
		model.addAttribute("monitores", dao.lista());
		return "monitor/lista";
	}

	@RequestMapping("/remove")
	public String remove(Monitor monitor) {
		dao.remove(monitor.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("monitor", dao.buscaPorId(id));
		return "monitor/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("monitor", dao.buscaPorId(id));
		model.addAttribute("disciplinas", dao_disciplina.lista());
		return "monitor/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
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

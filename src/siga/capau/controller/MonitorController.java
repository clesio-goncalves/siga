package siga.capau.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Monitor;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/monitor")
public class MonitorController {

	private List<Usuario> lista_usuario;
	private List<Monitor> lista_monitores;

	@Autowired
	MonitorDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	AtendimentoMonitoriaDao dao_atendimento_monitoria;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
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
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String adiciona(@Valid Monitor monitor, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorMatricula(monitor.getMatricula()).size() > 0) {
			return "redirect:novo";
		}

		// Insere o nome em maiusculo
		monitor.setNome(monitor.getNome().toUpperCase());

		// Adiciona no banco de dados
		dao.adiciona(monitor);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		this.lista_monitores = dao.lista();
		model.addAttribute("monitores", this.lista_monitores);
		return "monitor/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String remove(Monitor monitor) {
		dao.remove(monitor.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("monitor", dao.buscaPorId(id));
		model.addAttribute("disciplinas_monitor", dao_disciplina.listaDisciplinasPorMonitorId(id));
		model.addAttribute("atendimentos_monitoria", dao_atendimento_monitoria.buscaPeloMonitorId(id));
		return "monitor/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String edita(Long id, Model model) {
		model.addAttribute("monitor", dao.buscaPorId(id));
		return "monitor/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Docente" })
	public String altera(@Valid Monitor monitor, BindingResult result) {
		this.lista_monitores = dao.buscaPorMatricula(monitor.getMatricula());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + monitor.getId();
		} else if (this.lista_monitores.size() > 0 && this.lista_monitores.get(0).getId() != monitor.getId()) {
			return "redirect:edita?id=" + monitor.getId();
		}

		// Insere o nome em maiusculo
		monitor.setNome(monitor.getNome().toUpperCase());

		// Altera no banco
		dao.altera(monitor);
		return "redirect:lista";
	}

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_monitores != null) {
			String nomeRelatorio = "Relatório de Monitores.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/cadastro/relatorio_monitores.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_monitores);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}

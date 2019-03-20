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
import siga.capau.dao.DocenteDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.dao.TurmaDisciplinaDocenteDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Docente;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/docente")
public class DocenteController {

	private List<Docente> lista_docentes;
	private List<Usuario> lista_usuario;

	@Autowired
	DocenteDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	TurmaDisciplinaDocenteDao dao_turma_disciplina_docente;

	@Autowired
	ExtraClasseDao dao_extraclasse;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String novoDocente(Model model) {
		this.lista_usuario = dao_usuario.listaUsuarioDocenteSemVinculo();
		if (this.lista_usuario.size() == 0) {
			return "redirect:/usuario/novo";
		} else {
			model.addAttribute("usuarios", this.lista_usuario);
			return "docente/novo";
		}
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String adiciona(@Valid Docente docente, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorSiape(docente.getSiape()).size() > 0) {
			return "redirect:novo";
		}

		// Insere o nome em maiusculo
		docente.setNome(docente.getNome().toUpperCase());

		// Adiciona no banco de dados
		dao.adiciona(docente);

		// Ativa o status de vinculado
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		this.lista_docentes = dao.lista();
		model.addAttribute("docentes", this.lista_docentes);
		return "docente/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String remove(Docente docente) {
		dao_turma_disciplina_docente.removeTurmaDisciplinaDocentePeloDocenteId(docente.getId());
		dao.remove(docente.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("atendimentos_extraclasse", dao_extraclasse.buscaPeloDocenteId(id));
		model.addAttribute("docente", dao.buscaPorId(id));
		model.addAttribute("disciplinas_turma",
				dao_turma_disciplina_docente.buscaTurmaDisciplinaDocentePorDocenteId(id));
		return "docente/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String edita(Long id, Model model) {
		model.addAttribute("docente", dao.buscaPorId(id));
		return "docente/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia",
			"ROLE_Coordenação de Disciplina" })
	public String altera(@Valid Docente docente, BindingResult result) {
		this.lista_docentes = dao.buscaPorSiape(docente.getSiape());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + docente.getId();
		} else if (this.lista_docentes.size() > 0 && this.lista_docentes.get(0).getId() != docente.getId()) {
			return "redirect:edita?id=" + docente.getId();
		}

		// Insere o nome em maiusculo
		docente.setNome(docente.getNome().toUpperCase());

		// Altera no banco
		dao.altera(docente);
		return "redirect:lista";
	}

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_docentes != null) {
			String nomeRelatorio = "Relatório de Docentes.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/cadastro/relatorio_docentes.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_docentes);

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

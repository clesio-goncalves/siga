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
import siga.capau.dao.AlunoDao;
import siga.capau.dao.BeneficioDao;
import siga.capau.modelo.Beneficio;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/beneficio")
public class BeneficioController {

	private List<Beneficio> lista_beneficios;

	@Autowired
	BeneficioDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("/novo")
	@Secured("ROLE_Administrador")
	public String nova() {
		return "beneficio/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured("ROLE_Administrador")
	public String adiciona(@Valid Beneficio beneficio, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorNome(beneficio.getNome()).size() > 0) {
			return "redirect:novo";
		}
		// Adiciona no banco de dados
		dao.adiciona(beneficio);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured("ROLE_Administrador")
	public String lista(Model model) {
		model.addAttribute("beneficios", qntAlunos(dao.lista()));
		return "beneficio/lista";
	}

	@RequestMapping("/exibe")
	@Secured("ROLE_Administrador")
	public String exibe(Long id, Model model) {
		model.addAttribute("beneficio", dao.buscaPorId(id));
		model.addAttribute("alunos_beneficio", dao_aluno.listaAlunosPorBeneficioId(id));
		return "beneficio/exibe";
	}

	@RequestMapping("/remove")
	@Secured("ROLE_Administrador")
	public String remove(Beneficio beneficio) {
		// Remove a situação caso não haja alunos vinculados a esse beneficio
		if (dao_aluno.listaAlunosPorBeneficioId(beneficio.getId()).size() > 0) {
			return "redirect:lista";
		}
		dao.remove(beneficio.getId());
		return "redirect:lista";
	}

	@RequestMapping("/edita")
	@Secured("ROLE_Administrador")
	public String edita(Long id, Model model) {
		model.addAttribute("beneficio", dao.buscaPorId(id));
		return "beneficio/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured("ROLE_Administrador")
	public String altera(@Valid Beneficio beneficio, BindingResult result) {
		this.lista_beneficios = dao.buscaPorNome(beneficio.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + beneficio.getId();
		} else if (this.lista_beneficios.size() > 0 && this.lista_beneficios.get(0).getId() != beneficio.getId()) {
			return "redirect:edita?id=" + beneficio.getId();
		}

		dao.altera(beneficio);
		return "redirect:lista";
	}

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (this.lista_beneficios != null) {
			String nomeRelatorio = "Relatório de Benefícios Assistenciais.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/cadastro/relatorio_beneficios.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_beneficios);

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

	private List<Beneficio> qntAlunos(List<Beneficio> lista) {
		this.lista_beneficios = lista;
		for (Beneficio beneficio : this.lista_beneficios) {
			beneficio.setQnt_alunos(dao_aluno.buscaQntAlunosPorBeneficioId(beneficio.getId()));
		}
		return this.lista_beneficios;
	}

}

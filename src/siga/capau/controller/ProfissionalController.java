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
import siga.capau.dao.AtendimentoIndisciplinaDao;
import siga.capau.dao.AtendimentoPedagogiaAlunoDao;
import siga.capau.dao.AtendimentoSaudeDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Profissional;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

	private List<Profissional> lista_profissionais;
	private Profissional profissional;

	@Autowired
	ProfissionalDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	AtendimentoSaudeDao dao_atendimento_saude;

	@Autowired
	AtendimentoIndisciplinaDao dao_atendimento_indisciplina;

	@Autowired
	AtendimentoPedagogiaAlunoDao dao_atendimento_pedagogia_aluno;

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
		this.lista_profissionais = dao.lista();
		model.addAttribute("profissionais", this.lista_profissionais);
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
			model.addAttribute("atendimentos_pedagogia_aluno", dao_atendimento_pedagogia_aluno.buscaPeloProfissionalId(id));
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
		this.lista_profissionais = dao.buscaPorSiape(profissional.getSiape());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + profissional.getId();
		} else if (this.lista_profissionais.size() > 0
				&& this.lista_profissionais.get(0).getId() != profissional.getId()) {
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

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_profissionais != null) {
			String nomeRelatorio = "Relatório de Profissionais.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/relatorio_profissionais.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_profissionais);

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

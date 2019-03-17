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
import siga.capau.dao.AlunoSituacaoDao;
import siga.capau.dao.SituacaoDao;
import siga.capau.modelo.Situacao;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/situacao")
public class SituacaoController {

	private List<Situacao> lista_situacoes;

	@Autowired
	SituacaoDao dao;

	@Autowired
	AlunoSituacaoDao dao_aluno_situacao;

	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("/nova")
	@Secured("ROLE_Administrador")
	public String nova() {
		return "situacao/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured("ROLE_Administrador")
	public String adiciona(@Valid Situacao situacao, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorNome(situacao.getNome()).size() > 0) {
			return "redirect:novo";
		}
		// Adiciona no banco de dados
		dao.adiciona(situacao);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured("ROLE_Administrador")
	public String lista(Model model) {
		model.addAttribute("situacoes", qntAlunos(dao.lista()));
		return "situacao/lista";
	}

	@RequestMapping("/exibe")
	@Secured("ROLE_Administrador")
	public String exibe(Long id, Model model) {
		model.addAttribute("situacao", dao.buscaPorId(id));
		model.addAttribute("alunos_situacao", dao_aluno.listaAlunosPorTurmaId(id));
		return "situacao/exibe";
	}

	@RequestMapping("/remove")
	@Secured("ROLE_Administrador")
	public String remove(Situacao situacao) {
		// Remove a situação caso não haja alunos vinculados a essa situação
		if (dao_aluno_situacao.buscaAlunoSituacaoPorSituacaoId(situacao.getId()).size() > 0) {
			return "redirect:lista";
		}
		dao.remove(situacao.getId());
		return "redirect:lista";
	}

	@RequestMapping("/edita")
	@Secured("ROLE_Administrador")
	public String edita(Long id, Model model) {
		model.addAttribute("situacao", dao.buscaPorId(id));
		return "situacao/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured("ROLE_Administrador")
	public String altera(@Valid Situacao situacao, BindingResult result) {
		this.lista_situacoes = dao.buscaPorNome(situacao.getNome());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + situacao.getId();
		} else if (this.lista_situacoes.size() > 0 && this.lista_situacoes.get(0).getId() != situacao.getId()) {
			return "redirect:edita?id=" + situacao.getId();
		}

		dao.altera(situacao);
		return "redirect:lista";
	}

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_situacoes != null) {
			String nomeRelatorio = "Relatório de Situacões dos alunos.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/cadastro/relatorio_situacoes.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_situacoes);

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

	private List<Situacao> qntAlunos(List<Situacao> lista) {
		this.lista_situacoes = lista;
		for (Situacao situacao : this.lista_situacoes) {
			situacao.setQnt_alunos(dao_aluno.buscaQntAlunosPorSituacaoId(situacao.getId()));
		}
		return this.lista_situacoes;
	}

}

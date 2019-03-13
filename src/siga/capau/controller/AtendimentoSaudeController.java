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
import siga.capau.dao.AtendimentoSaudeDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.PerfilDao;
import siga.capau.dao.ProfissionalDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.AtendimentoSaude;
import siga.capau.modelo.FiltroAtendimentoSaude;
import siga.capau.modelo.Profissional;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/atendimento/saude")
public class AtendimentoSaudeController {

	private AtendimentoSaude atendimento_saude;
	private List<AtendimentoSaude> lista_atendimentos_saude;
	private Usuario usuario;
	private List<Profissional> lista_profissional;
	private FiltroAtendimentoSaude filtra_atendimento_saude;
	private boolean possui_permissao_editar = false;

	@Autowired
	AtendimentoSaudeDao dao;

	@Autowired
	ProfissionalDao dao_profissional;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	PerfilDao dao_perfil;

	@Autowired
	AlunoDao dao_aluno;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Psicologia", "ROLE_Assistência Social", "ROLE_Enfermagem", "ROLE_Odontologia" })
	public String novoAtendimentoSaude(Model model) {
		this.lista_profissional = dao_profissional.buscaPorUsuario(retornaUsuarioLogado().getId());
		if (this.lista_profissional.size() == 0) {
			return "redirect:/profissional/novo";
		}
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("profissional", this.lista_profissional.get(0));
		return "atendimento_saude/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Psicologia", "ROLE_Assistência Social", "ROLE_Enfermagem", "ROLE_Odontologia" })
	public String adiciona(@Valid AtendimentoSaude atendimentoSaude, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(atendimentoSaude);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		this.lista_profissional = dao_profissional.buscaPorUsuario(retornaUsuarioLogado().getId());
		if (this.lista_profissional.size() == 0) {
			return "redirect:/profissional/novo";
		}
		this.lista_atendimentos_saude = dao.lista();
		model.addAttribute("atendimentos_saude", this.lista_atendimentos_saude);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("alunos", dao_aluno.lista());
		model.addAttribute("profissionais", dao_profissional.buscaSetorSaude());
		possuiPermissaoProfissionalSaude(model); // adiciona o model tipo_atendimento na view
		return "atendimento_saude/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Psicologia", "ROLE_Assistência Social", "ROLE_Enfermagem", "ROLE_Odontologia" })
	public String remove(AtendimentoSaude AtendimentoSaude, HttpServletResponse response) {
		this.atendimento_saude = AtendimentoSaude;
		if (possuiPermissao()) {
			dao.remove(AtendimentoSaude.getId());
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/exibe")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordenação de Disciplina" })
	public String exibe(Long id, Model model) {
		model.addAttribute("atendimento_saude", dao.buscaPorId(id));
		possuiPermissaoProfissionalSaude(model); // adiciona o model tipo_atendimento na view
		return "atendimento_saude/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Psicologia", "ROLE_Assistência Social", "ROLE_Enfermagem", "ROLE_Odontologia" })
	public String edita(Long id, Model model, HttpServletResponse response) {
		this.atendimento_saude = dao.buscaPorId(id);
		if (possuiPermissao()) {
			this.possui_permissao_editar = true;
			model.addAttribute("atendimento_saude", this.atendimento_saude);
			model.addAttribute("cursos", dao_curso.lista());
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(this.atendimento_saude.getAluno().getTurma().getCurso().getId()));
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(this.atendimento_saude.getAluno().getTurma().getId()));
			return "atendimento_saude/edita";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Psicologia", "ROLE_Assistência Social", "ROLE_Enfermagem", "ROLE_Odontologia" })
	public String altera(@Valid AtendimentoSaude atendimentoSaude, BindingResult result, HttpServletResponse response) {
		if (this.possui_permissao_editar) {
			if (result.hasErrors()) {
				return "redirect:edita?id=" + atendimentoSaude.getId();
			}

			// Altera no banco
			dao.altera(atendimentoSaude);
			this.possui_permissao_editar = false;
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/relatorio")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Coordenação de Disciplina" })
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (this.lista_atendimentos_saude != null) {
			String nomeRelatorio = "Atendimento de Serviço de Saúde.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/atendimento_saude.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_saude);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_saude/import_edita/turma";
		} else {
			return "atendimento_saude/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_turma_lista_atendimento_saude", method = RequestMethod.POST)
	public String filtrarTurmaEmListaAtendimentoSaude(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		if (request.getParameter("curso") != null) {
			model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso"))));
		}
		return "atendimento_saude/import_lista/import_filtro/turma";
	}

	@RequestMapping(value = "/filtro_aluno", method = RequestMethod.POST)
	public String filtrarAluno(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("turma_id") != null) {
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_saude/import_edita/aluno";
		} else {
			return "atendimento_saude/import_novo/aluno";
		}
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.lista_atendimentos_saude = dao.filtraAtendimentoSaude(trataParametrosRequest(request));
		model.addAttribute("atendimentos_saude", this.lista_atendimentos_saude);
		return "atendimento_saude/import_lista/tabela";
	}

	private FiltroAtendimentoSaude trataParametrosRequest(HttpServletRequest request) {
		this.filtra_atendimento_saude = new FiltroAtendimentoSaude();
		this.filtra_atendimento_saude.setData_inicial_atendimento(request.getParameter("data_inicial_atendimento"));
		this.filtra_atendimento_saude.setData_final_atendimento(request.getParameter("data_final_atendimento"));
		this.filtra_atendimento_saude
				.setHorario_inicial_atendimento(request.getParameter("horario_inicial_atendimento"));
		this.filtra_atendimento_saude.setHorario_final_atendimento(request.getParameter("horario_final_atendimento"));
		this.filtra_atendimento_saude.setCurso(request.getParameter("curso"));
		this.filtra_atendimento_saude.setTurma(request.getParameter("turma"));
		this.filtra_atendimento_saude.setTipo_atendimento(request.getParameter("tipo_atendimento"));
		this.filtra_atendimento_saude.setAluno(request.getParameter("aluno"));
		this.filtra_atendimento_saude.setProfissional(request.getParameter("profissional"));
		this.filtra_atendimento_saude.setPossui_problema(request.getParameter("possui_problema"));
		this.filtra_atendimento_saude
				.setEsse_problema_dificulta_aprendizado(request.getParameter("esse_problema_dificulta_aprendizado"));

		trataDatas();

		return this.filtra_atendimento_saude;
	}

	private void trataDatas() {
		this.filtra_atendimento_saude.setData_inicial_atendimento(
				trataDataInicial(this.filtra_atendimento_saude.getData_inicial_atendimento()));
		this.filtra_atendimento_saude
				.setData_final_atendimento(trataDataFinal(this.filtra_atendimento_saude.getData_final_atendimento()));
	}

	private String trataDataInicial(String data_inicial) {
		// Se a data inicial não estiver sido informada, será atribuido 01/01/2019
		if (data_inicial.equals("")) {
			return "2019-01-01";
		} else {
			return this.filtra_atendimento_saude.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final não estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_atendimento_saude.retornaDataFinal();
		} else {
			return this.filtra_atendimento_saude.formataData(data_final);
		}
	}

	private boolean possuiPermissao() {
		this.usuario = retornaUsuarioLogado(); // Pego o usuário logado
		// O profissional da saúde só realiza a ação se for do mesmo tipo de atendimento
		if (this.atendimento_saude.getProfissional().getTipo_atendimento()
				.equals(dao_profissional.buscaPorUsuario(this.usuario.getId()).get(0).getTipo_atendimento())) {
			return true;
		}
		return false;
	}

	private void possuiPermissaoProfissionalSaude(Model model) {
		this.usuario = retornaUsuarioLogado();
		if (this.usuario.getPerfil().getId() == 4 || this.usuario.getPerfil().getId() == 5
				|| this.usuario.getPerfil().getId() == 6 || this.usuario.getPerfil().getId() == 8) {
			model.addAttribute("tipo_atendimento",
					dao_profissional.buscaPorUsuario(this.usuario.getId()).get(0).getTipo_atendimento());
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

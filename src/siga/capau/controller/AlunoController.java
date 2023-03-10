package siga.capau.controller;

import java.io.IOException;
import java.util.Date;
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
import siga.capau.dao.AtendimentoIndisciplinaDao;
import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.AtendimentoPedagogiaAlunoDao;
import siga.capau.dao.AtendimentoPedagogiaFamiliaDao;
import siga.capau.dao.AtendimentoSaudeDao;
import siga.capau.dao.BeneficioDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.dao.SituacaoDao;
import siga.capau.dao.TurmaDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.Aluno;
import siga.capau.modelo.FiltroAluno;
import siga.capau.modelo.Turma;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/aluno")
public class AlunoController {

	private List<Turma> lista_turma;
	private List<Aluno> lista_alunos;
	private Aluno aluno;
	private FiltroAluno filtro_aluno;

	@Autowired
	AlunoDao dao;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	UsuarioDao dao_usuario;

	@Autowired
	ExtraClasseDao dao_extraclasse;

	@Autowired
	AtendimentoMonitoriaDao dao_atendimento_monitoria;

	@Autowired
	AtendimentoSaudeDao dao_atendimento_saude;

	@Autowired
	AtendimentoIndisciplinaDao dao_atendimento_indisciplina;

	@Autowired
	AtendimentoPedagogiaAlunoDao dao_atendimento_pedagogia_aluno;

	@Autowired
	AtendimentoPedagogiaFamiliaDao dao_atendimento_pedagogia_familia;

	@Autowired
	SituacaoDao dao_situacao;

	@Autowired
	AlunoSituacaoDao dao_aluno_situacao;

	@Autowired
	BeneficioDao dao_beneficio;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordena????o de Disciplina" })
	public String novoAluno(Model model) {
		this.lista_turma = dao_turma.listaTurmasAtivas();
		if (this.lista_turma.size() == 0) {
			return "redirect:/turma/nova";
		}
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAlunoSemVinculo());
		model.addAttribute("situacoes", dao_situacao.lista());
		model.addAttribute("beneficios", dao_beneficio.lista());
		return "aluno/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordena????o de Disciplina" })
	public String adiciona(@Valid Aluno aluno, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		} else if (dao.buscaPorMatricula(aluno.getMatricula()).size() > 0) {
			return "redirect:novo";
		}

		// Insere o nome em maiusculo
		aluno.setNome(aluno.getNome().toUpperCase());

		// Testa se o id do usu??rio ?? null
		if (aluno.getUsuario().getId() == null) {
			aluno.setUsuario(null);
		}

		// Testa se o id do beneficio ?? null
		if (aluno.getBeneficio().getId() == null) {
			aluno.setBeneficio(null);
		}

		// Adiciona no banco de dados
		this.aluno = dao.adiciona(aluno);

		// Adiciona Vinculo em AlunoSitua????o
		this.dao_aluno_situacao.adiciona(this.aluno.getId(), this.aluno.getSituacao().getId(), new Date());

		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		this.lista_alunos = dao.lista();
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("alunos", this.lista_alunos); // todos os aluno ativos
		model.addAttribute("situacoes", dao_situacao.lista());
		model.addAttribute("beneficios", dao_beneficio.lista());
		return "aluno/lista";
	}

	@RequestMapping("/remove")
	@Secured("ROLE_Administrador")
	public String remove(Aluno aluno) {
		dao_aluno_situacao.removePeloAlunoId(aluno.getId()); // AlunoSituacao
		dao_atendimento_indisciplina.removePeloAlunoId(aluno.getId()); // AtendimentoIndisciplina
		dao_atendimento_monitoria.removePeloAlunoId(aluno.getId()); // AtendimentoMonitoria
		dao_atendimento_pedagogia_aluno.removePeloAlunoId(aluno.getId()); // AtendimentoPedagogiaAluno
		dao_atendimento_pedagogia_familia.removePeloAlunoId(aluno.getId()); // AtendimentoPedagogiaFamilia
		dao_atendimento_saude.removePeloAlunoId(aluno.getId()); // AtendimentoSaude
		dao_extraclasse.removePeloAlunoId(aluno.getId()); // ExtraClasse
		dao.remove(aluno.getId()); // Aluno
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("aluno", dao.buscaPorId(id));
		model.addAttribute("atendimentos_extraclasse", dao_extraclasse.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_monitoria", dao_atendimento_monitoria.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_saude", dao_atendimento_saude.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_indisciplina", dao_atendimento_indisciplina.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_pedagogia_aluno", dao_atendimento_pedagogia_aluno.buscaPeloAlunoId(id));
		model.addAttribute("atendimentos_pedagogia_familia", dao_atendimento_pedagogia_familia.buscaPeloAlunoId(id));
		return "aluno/exibe";
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordena????o de Disciplina" })
	public String edita(Long id, Model model) {
		this.aluno = dao.buscaPorId(id);
		model.addAttribute("aluno", this.aluno);
		model.addAttribute("cursos", dao_curso.lista());
		model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(this.aluno.getTurma().getCurso().getId()));
		model.addAttribute("usuarios", dao_usuario.listaUsuarioAlunoSemVinculo());
		model.addAttribute("situacoes", dao_situacao.lista());
		model.addAttribute("beneficios", dao_beneficio.lista());
		return "aluno/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assist??ncia Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordena????o de Disciplina" })
	public String altera(@Valid Aluno aluno, BindingResult result) {

		this.lista_alunos = dao.buscaPorMatricula(aluno.getMatricula());
		if (result.hasErrors()) {
			return "redirect:edita?id=" + aluno.getId();
		} else if (this.lista_alunos.size() > 0 && this.lista_alunos.get(0).getId() != aluno.getId()) {
			return "redirect:edita?id=" + aluno.getId();
		}

		// Insere o nome em maiusculo
		aluno.setNome(aluno.getNome().toUpperCase());

		// Testa se o id do usu??rio ?? null
		if (aluno.getUsuario().getId() == null) {
			aluno.setUsuario(null);
		}

		// Testa se o id do beneficio ?? null
		if (aluno.getBeneficio().getId() == null) {
			aluno.setBeneficio(null);
		}

		// Se a situa????o informada for diferente, ent??o insere em AlunoSitua????o
		if (dao.buscaSituacaoAtualPorAluno(aluno.getId()) != aluno.getSituacao().getId()) {
			this.dao_aluno_situacao.adiciona(aluno.getId(), aluno.getSituacao().getId(), new Date());
		}

		// Altera no banco
		dao.altera(aluno);
		return "redirect:lista";

	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			model.addAttribute("turmas",
					dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "aluno/import_edita/turma";
		} else {
			return "aluno/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_turma_em_listagem", method = RequestMethod.POST)
	public String filtrarTurmaEmListagem(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("curso") != null) {
			model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso"))));
		}
		return "aluno/import_lista/import_filtro/turma";
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.lista_alunos = dao.filtraAluno(trataParametrosRequest(request));
		model.addAttribute("alunos", this.lista_alunos);
		return "aluno/import_lista/tabela";
	}

	@RequestMapping("/relatorio")
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_alunos != null) {
			String nomeRelatorio = "Relat??rio de Alunos.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/cadastro/relatorio_alunos.jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_alunos);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", retornaUsuarioLogado().getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}

	}

	private FiltroAluno trataParametrosRequest(HttpServletRequest request) {
		this.filtro_aluno = new FiltroAluno();
		this.filtro_aluno.setCurso(request.getParameter("curso"));
		this.filtro_aluno.setTurma(request.getParameter("turma"));
		this.filtro_aluno.setStatus(request.getParameter("status"));
		this.filtro_aluno.setMatricula(request.getParameter("matricula"));
		this.filtro_aluno.setNome(request.getParameter("nome"));
		this.filtro_aluno.setTelefone(request.getParameter("telefone"));
		this.filtro_aluno.setUsuario(request.getParameter("usuario"));
		this.filtro_aluno.setBeneficio(request.getParameter("beneficio"));
		this.filtro_aluno.setSituacao(request.getParameter("situacao"));
		this.filtro_aluno.setAtendimentos(request.getParameter("atendimentos"));

		return this.filtro_aluno;
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}

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
import siga.capau.dao.AlunoAtendimentoMonitoriaDao;
import siga.capau.dao.AlunoDao;
import siga.capau.dao.AtendimentoMonitoriaDao;
import siga.capau.dao.CursoDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.MonitorDao;
import siga.capau.dao.TurmaDao;
import siga.capau.modelo.Aluno;
import siga.capau.modelo.AtendimentoMonitoria;
import siga.capau.modelo.FiltroAtendimentoMonitoria;
import siga.capau.modelo.Monitor;
import siga.capau.modelo.Turma;
import siga.capau.modelo.Usuario;
import siga.capau.relatorio.GeradorRelatorio;

@Transactional
@Controller
@RequestMapping("/atendimento/monitoria")
public class AtendimentoMonitoriaController {

	private AtendimentoMonitoria atendimento_monitoria;
	private List<AtendimentoMonitoria> lista_atendimentos_monitoria;
	private FiltroAtendimentoMonitoria filtra_atendimento_monitoria;
	private Turma turma;
	private Usuario usuario;
	private Monitor monitor;
	private List<Aluno> lista_alunos;
	private List<Monitor> lista_monitor;
	private boolean possui_permissao_editar = false;
	String alunos_id[];

	@Autowired
	AtendimentoMonitoriaDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	MonitorDao dao_monitor;

	@Autowired
	TurmaDao dao_turma;

	@Autowired
	CursoDao dao_curso;

	@Autowired
	AlunoAtendimentoMonitoriaDao dao_aluno_atendimento_monitoria;

	@RequestMapping("/novo")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String novo(Model model) {
		this.usuario = retornaUsuarioLogado();
		// Caso o usuário seja um monitor
		if (this.usuario.getPerfil().getId() == 10) {
			this.lista_monitor = dao_monitor.buscaPorUsuario(this.usuario.getId());
			if (this.lista_monitor.size() == 1) {
				model.addAttribute("monitor", this.lista_monitor.get(0));
				model.addAttribute("cursos", dao_curso.listaCursosPorMonitorId(this.lista_monitor.get(0).getId()));
			} else {
				return "redirect:/monitor/novo";
			}
		} else {
			model.addAttribute("cursos", dao_curso.lista());
		}
		return "atendimento_monitoria/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String adiciona(@Valid AtendimentoMonitoria atendimentoMonitoria, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		this.atendimento_monitoria = dao.adiciona(atendimentoMonitoria);

		// Vincula os alunos ao atendimento de monitoria
		adicionaAlunoAtendimentoMonitoria(atendimentoMonitoria);

		return "redirect:lista";
	}

	@RequestMapping("/lista")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public String lista(Model model) {
		this.usuario = retornaUsuarioLogado();

		// Se o usuario logado for monitor só exibe os atendimentos dele
		if (this.usuario.getPerfil().getId() == 10) {
			this.lista_monitor = dao_monitor.buscaPorUsuario(this.usuario.getId());
			if (this.lista_monitor.size() == 1) { // se há monitor para o usuário cadastrado
				this.lista_atendimentos_monitoria = dao.buscaPeloMonitorId(this.lista_monitor.get(0).getId());
				model.addAttribute("cursos", dao_curso.listaCursosPorMonitorId(this.lista_monitor.get(0).getId()));
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorMonitorId(this.lista_monitor.get(0).getId()));
				model.addAttribute("monitor", this.lista_monitor.get(0));
			} else {
				return "redirect:/monitor/novo";
			}
		} else {
			this.lista_atendimentos_monitoria = dao.lista();
			model.addAttribute("cursos", dao_curso.lista());
			model.addAttribute("disciplinas", dao_disciplina.lista());
			model.addAttribute("monitores", dao_monitor.lista());
		}
		alteraAlunosMonitoria();
		model.addAttribute("atendimento_monitorias", this.lista_atendimentos_monitoria);
		model.addAttribute("alunos", dao_aluno.lista());
		return "atendimento_monitoria/lista";
	}

	@RequestMapping("/remove")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String remove(AtendimentoMonitoria atendimentoMonitoria, HttpServletResponse response) {
		if (possuiPermissao(atendimentoMonitoria.getId())) {
			dao_aluno_atendimento_monitoria.removePeloAtendimentoId(atendimentoMonitoria.getId());
			dao.remove(atendimentoMonitoria.getId());
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
		if (possuiPermissao(id)) {
			this.atendimento_monitoria = dao.buscaPorId(id);
			model.addAttribute("atendimento_monitoria", dao.buscaPorId(id));
			dadosExibir(id, model);
			return "atendimento_monitoria/exibe";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/edita")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String edita(Long id, Model model, HttpServletResponse response) {
		if (possuiPermissao(id)) {
			this.atendimento_monitoria = dao.buscaPorId(id);
			model.addAttribute("atendimento_monitoria", this.atendimento_monitoria);
			this.possui_permissao_editar = true;

			if (this.usuario.getPerfil().getId() == 10) { // Monitor
				model.addAttribute("monitor", this.atendimento_monitoria.getMonitor());
				model.addAttribute("cursos", dao_curso.listaCursosPorMonitorId(this.lista_monitor.get(0).getId()));

				// Se for informado que houve atendimento
				if (this.atendimento_monitoria.isStatus_atendimento() == false) {
					buscaDadosAtendimento(model);
					buscaDadosAtendimentoParaMonitor(model);
				}
			} else {
				model.addAttribute("cursos", dao_curso.lista());

				// Se for informado que houve atendimento
				if (this.atendimento_monitoria.isStatus_atendimento() == false) {
					buscaDadosAtendimento(model);
					buscaDadosAtendimentoParaOutros(model);
				} else {
					model.addAttribute("monitores", dao_monitor.lista());
				}
			}
			return "atendimento_monitoria/edita";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Pedagogia", "ROLE_Monitor" })
	public String altera(@Valid AtendimentoMonitoria atendimentoMonitoria, BindingResult result,
			HttpServletResponse response) {
		if (this.possui_permissao_editar) {
			if (result.hasErrors()) {
				return "redirect:edita?id=" + atendimentoMonitoria.getId();
			}

			// Se na edição marcar atendimento como não realizado
			if (atendimentoMonitoria.isStatus_atendimento()) {
				atendimentoMonitoria.setConteudo("-");
				atendimentoMonitoria.setDificuldades_diagnosticadas("-");
			}

			// Altera no banco
			dao.altera(atendimentoMonitoria);

			// Desvincula os alunos de AlunoAtendimentoMonitoria
			dao_aluno_atendimento_monitoria.removePeloAtendimentoId(atendimentoMonitoria.getId());

			// Vincula os alunos ao atendimento de monitoria
			adicionaAlunoAtendimentoMonitoria(atendimentoMonitoria);

			this.possui_permissao_editar = false;
			return "redirect:lista";
		} else {
			response.setStatus(403);
			return "redirect:/403";
		}
	}

	@RequestMapping("/relatorio")
	@Secured({ "ROLE_Administrador", "ROLE_Coordenador", "ROLE_Diretor", "ROLE_Psicologia", "ROLE_Assistência Social",
			"ROLE_Enfermagem", "ROLE_Pedagogia", "ROLE_Odontologia", "ROLE_Docente", "ROLE_Monitor",
			"ROLE_Coordenação de Disciplina" })
	public void relatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (this.lista_atendimentos_monitoria != null) {
			String nomeRelatorio = "Atendimento Monitoria.pdf";
			String nomeArquivo = request.getServletContext()
					.getRealPath("/resources/relatorio/monitoria/" + retornaCaminhoRelatorio() + ".jasper");
			Map<String, Object> parametros = new HashMap<String, Object>();
			JRBeanCollectionDataSource relatorio = new JRBeanCollectionDataSource(this.lista_atendimentos_monitoria);

			parametros.put("relatorio_logo",
					request.getServletContext().getRealPath("/resources/imagens/relatorio_logo.png"));
			parametros.put("usuario_logado", this.usuario.getEmail());

			GeradorRelatorio gerador = new GeradorRelatorio(nomeRelatorio, nomeArquivo, parametros, relatorio);
			gerador.geraPDFParaOutputStream(response);
		} else {
			response.sendRedirect("lista");
		}
	}

	private String retornaCaminhoRelatorio() {
		switch (this.usuario.getPerfil().getId().toString()) {
		case "10":
			return "atendimento_monitoria_monitor";
		case "7":
			return "atendimento_monitoria_pedagogia";
		case "3":
			return "atendimento_monitoria_diretor";
		default:
			return "atendimento_monitoria";
		}
	}

	@RequestMapping(value = "/filtro_turma", method = RequestMethod.POST)
	public String filtrarTurma(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("curso_id") != null) {
			if (this.usuario.getPerfil().getId() == 10) { // Se Monitor
				model.addAttribute("turmas",
						dao_turma.listaTurmaPorCursoIdMonitorId(Long.parseLong(request.getParameter("curso_id")),
								Long.parseLong(request.getParameter("monitor_id"))));
			} else {
				model.addAttribute("turmas",
						dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso_id"))));
			}
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/turma";
		} else {
			return "atendimento_monitoria/import_novo/turma";
		}
	}

	@RequestMapping(value = "/filtro_turma_lista_atendimento_monitoria", method = RequestMethod.POST)
	public String filtrarTurmaEmListaAtendimentoMonitoria(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		if (request.getParameter("curso") != null) {
			model.addAttribute("turmas", dao_turma.listaTurmaPorCursoId(Long.parseLong(request.getParameter("curso"))));
		}
		return "atendimento_monitoria/import_lista/import_filtro/turma";
	}

	@RequestMapping(value = "/filtro_aluno", method = RequestMethod.POST)
	public String filtrarAluno(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if (request.getParameter("turma_id") != null) {
			model.addAttribute("alunos",
					dao_aluno.listaAlunosPorTurmaId(Long.parseLong(request.getParameter("turma_id"))));
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/aluno";
		} else {
			return "atendimento_monitoria/import_novo/aluno";
		}
	}

	@RequestMapping(value = "/filtro_disciplina", method = RequestMethod.POST)
	public String filtrarDisciplina(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("turma_id") != null) {
			if (this.usuario.getPerfil().getId() == 10) { // Se Monitor
				model.addAttribute("disciplinas",
						dao_disciplina.listaDisciplinasPorTurmaIdMonitorId(
								Long.parseLong(request.getParameter("turma_id")),
								Long.parseLong(request.getParameter("monitor_id"))));
			} else {
				model.addAttribute("disciplinas", dao_disciplina
						.listaDisciplinasPorTurmaIdMonitorNotNull(Long.parseLong(request.getParameter("turma_id"))));
			}
		}
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/disciplina";
		} else {
			return "atendimento_monitoria/import_novo/disciplina";
		}
	}

	@RequestMapping(value = "/filtro_monitor", method = RequestMethod.POST)
	public String filtrarMonitor(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("disciplina_id") != null) {
			model.addAttribute("monitor",
					dao_monitor.buscaPorDisciplinaId(Long.parseLong(request.getParameter("disciplina_id"))).get(0));
		}
		return "atendimento_monitoria/import_novo/monitor";
	}

	@RequestMapping(value = "/lista_monitor", method = RequestMethod.POST)
	public String listaDocente(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("monitores", dao_monitor.listaMonitorVinculadoADisciplina());
		if (request.getParameter("contexto").equals("edita")) {
			return "atendimento_monitoria/import_edita/monitor";
		} else {
			return "atendimento_monitoria/import_novo/monitor";
		}
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtra(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.lista_atendimentos_monitoria = dao.filtraAtendimentoMonitoria(trataParametrosRequest(request));
		alteraAlunosMonitoria();
		model.addAttribute("atendimento_monitorias", this.lista_atendimentos_monitoria);
		return "atendimento_monitoria/import_lista/tabela";
	}

	private FiltroAtendimentoMonitoria trataParametrosRequest(HttpServletRequest request) {
		this.filtra_atendimento_monitoria = new FiltroAtendimentoMonitoria();
		this.filtra_atendimento_monitoria.setData_inicial_atendimento(request.getParameter("data_inicial_atendimento"));
		this.filtra_atendimento_monitoria.setData_final_atendimento(request.getParameter("data_final_atendimento"));
		this.filtra_atendimento_monitoria
				.setHorario_inicial_atendimento(request.getParameter("horario_inicial_atendimento"));
		this.filtra_atendimento_monitoria
				.setHorario_final_atendimento(request.getParameter("horario_final_atendimento"));
		this.filtra_atendimento_monitoria.setCurso(request.getParameter("curso"));
		this.filtra_atendimento_monitoria.setTurma(request.getParameter("turma"));
		this.filtra_atendimento_monitoria.setAluno(request.getParameter("aluno"));
		this.filtra_atendimento_monitoria.setDisciplina(request.getParameter("disciplina"));
		this.filtra_atendimento_monitoria.setMonitor(request.getParameter("monitor"));
		this.filtra_atendimento_monitoria.setLocal(request.getParameter("local"));
		this.filtra_atendimento_monitoria.setConteudo(request.getParameter("conteudo"));
		this.filtra_atendimento_monitoria
				.setDificuldades_diagnosticadas(request.getParameter("dificuldades_diagnosticadas"));
		this.filtra_atendimento_monitoria.setStatus_atendimento(request.getParameter("status_atendimento"));

		trataDatas();

		return this.filtra_atendimento_monitoria;
	}

	private void trataDatas() {
		this.filtra_atendimento_monitoria.setData_inicial_atendimento(
				trataDataInicial(this.filtra_atendimento_monitoria.getData_inicial_atendimento()));
		this.filtra_atendimento_monitoria.setData_final_atendimento(
				trataDataFinal(this.filtra_atendimento_monitoria.getData_final_atendimento()));
	}

	private String trataDataInicial(String data_inicial) {
		// Se a data inicial não estiver sido informada, será atribuido 01/01/2019
		if (data_inicial.equals("")) {
			return "2019-01-01";
		} else {
			return this.filtra_atendimento_monitoria.formataData(data_inicial);
		}
	}

	private String trataDataFinal(String data_final) {
		// Se a data final não estiver sido informada, sera atribuido a data atual do
		// servidor
		if (data_final.equals("")) {
			return this.filtra_atendimento_monitoria.retornaDataFinal();
		} else {
			return this.filtra_atendimento_monitoria.formataData(data_final);
		}
	}

	private boolean possuiPermissao(Long id) {
		this.usuario = retornaUsuarioLogado(); // Pego o usuário logado
		// O monitor só realiza a ação se for dono do atendimento
		if (this.usuario.getPerfil().getId() == 10) {
			this.monitor = dao_monitor.buscaPorUsuario(this.usuario.getId()).get(0);
			if (this.monitor.getId() == dao.buscaMonitorIdPeloAtendimentoMonitoriaId(id)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private Usuario retornaUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private void adicionaAlunoAtendimentoMonitoria(AtendimentoMonitoria atendimentoMonitoria) {
		if (!atendimentoMonitoria.isStatus_atendimento()) {
			// Split lista de alunos
			this.alunos_id = atendimentoMonitoria.getAlunos().split(",");

			// Adiciona Vinculo em AlunoAtendimentoMonitoria
			for (String aluno_id : this.alunos_id) {
				this.dao_aluno_atendimento_monitoria.adiciona(Long.parseLong(aluno_id),
						this.atendimento_monitoria.getId());
			}
		}
	}

	private void dadosExibir(Long id, Model model) {
		if (!this.atendimento_monitoria.isStatus_atendimento()) {
			this.atendimento_monitoria.setAlunos("");
			for (String nome_aluno : dao_aluno
					.buscaNomeAlunoPorAtendimentoMonitoriaId(this.atendimento_monitoria.getId())) {
				this.atendimento_monitoria
						.setAlunos(this.atendimento_monitoria.getAlunos() + "- " + nome_aluno + "<br>");
			}
			this.turma = dao_turma.buscaTurmaPorAtendimentoMonitoriaId(id);
			model.addAttribute("turma", this.turma);
			model.addAttribute("curso", dao_curso.buscaPorTurmaId(this.turma.getId()));
		}
	}

	private void buscaDadosAtendimento(Model model) {
		this.lista_alunos = this.dao_aluno.buscaAlunoPorAtendimentoMonitoriaId(this.atendimento_monitoria.getId());
		model.addAttribute("turma_atendimento", this.lista_alunos.get(0).getTurma().getId());
		model.addAttribute("curso_atendimento", this.lista_alunos.get(0).getTurma().getCurso().getId());

		this.atendimento_monitoria.setAlunos("");
		for (Aluno aluno : this.lista_alunos) {
			this.atendimento_monitoria.setAlunos(this.atendimento_monitoria.getAlunos() + aluno.getId() + " ");
		}
	}

	private void buscaDadosAtendimentoParaMonitor(Model model) {
		model.addAttribute("turmas",
				dao_turma.listaTurmaPorCursoIdMonitorId(this.lista_alunos.get(0).getTurma().getCurso().getId(),
						this.atendimento_monitoria.getMonitor().getId()));
		model.addAttribute("alunos", dao_aluno.listaAlunosPorTurmaId(this.lista_alunos.get(0).getTurma().getId()));
		model.addAttribute("disciplinas", dao_disciplina.listaDisciplinasPorTurmaIdMonitorId(
				this.lista_alunos.get(0).getTurma().getId(), this.atendimento_monitoria.getMonitor().getId()));
	}

	private void buscaDadosAtendimentoParaOutros(Model model) {
		model.addAttribute("turmas",
				dao_turma.listaTurmaPorCursoId(this.lista_alunos.get(0).getTurma().getCurso().getId()));
		model.addAttribute("alunos", dao_aluno.listaAlunosPorTurmaId(this.lista_alunos.get(0).getTurma().getId()));
		model.addAttribute("disciplinas",
				dao_disciplina.listaDisciplinasPorTurmaId(this.lista_alunos.get(0).getTurma().getId()));
		model.addAttribute("monitores",
				dao_monitor.buscaPorDisciplinaId(this.atendimento_monitoria.getDisciplina().getId()));
	}

	private void alteraAlunosMonitoria() {
		for (AtendimentoMonitoria atendimento : this.lista_atendimentos_monitoria) {
			if (!atendimento.isStatus_atendimento()) {
				atendimento.setAlunos("");
				for (String nome_aluno : dao_aluno.buscaNomeAlunoPorAtendimentoMonitoriaId(atendimento.getId())) {
					atendimento.setAlunos(atendimento.getAlunos() + "- " + nome_aluno + "<br>");
				}
			}
		}
	}

}

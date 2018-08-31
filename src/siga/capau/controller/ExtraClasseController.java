package siga.capau.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import siga.capau.dao.AlunoDao;
import siga.capau.dao.DisciplinaDao;
import siga.capau.dao.DocenteDao;
import siga.capau.dao.ExtraClasseDao;
import siga.capau.modelo.ExtraClasse;

@Transactional
@Controller
@RequestMapping("/atendimento/extra-classe")
public class ExtraClasseController {

	private Long turma_id;

	@Autowired
	ExtraClasseDao dao;

	@Autowired
	AlunoDao dao_aluno;

	@Autowired
	DisciplinaDao dao_disciplina;

	@Autowired
	DocenteDao dao_docente;

	@RequestMapping("/novo")
	public String novoExtraClasse(Model model) {
		model.addAttribute("alunos", dao_aluno.lista());
		return "extra_classe/novo";
	}

	@RequestMapping(value = "/adiciona", method = RequestMethod.POST)
	public String adiciona(@Valid ExtraClasse extraClasse, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:novo";
		}

		// Adiciona no banco de dados
		dao.adiciona(extraClasse);
		return "redirect:lista";
	}

	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("extra_classes", dao.lista());
		return "extra_classe/lista";
	}

	@RequestMapping("/remove")
	public String remove(ExtraClasse extraClasse) {
		dao.remove(extraClasse.getId());
		return "redirect:lista";
	}

	@RequestMapping("/exibe")
	public String exibe(Long id, Model model) {
		model.addAttribute("extra_classe", dao.buscaPorId(id));
		return "extra_classe/exibe";
	}

	@RequestMapping("/edita")
	public String edita(Long id, Model model) {
		model.addAttribute("extra_classe", dao.buscaPorId(id));
		return "extra_classe/edita";
	}

	@RequestMapping(value = "/altera", method = RequestMethod.POST)
	public String altera(@Valid ExtraClasse extraClasse, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:edita?id=" + extraClasse.getId();
		}

		// Altera no banco
		dao.altera(extraClasse);
		return "redirect:lista";
	}

	@RequestMapping(value = "/filtro_disciplina", method = RequestMethod.POST)
	public String filtrarDisciplina(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("aluno_id") != null) {
			this.turma_id = dao_aluno.buscaTurmaIdPorAlunoId(Long.parseLong(request.getParameter("aluno_id")));
			model.addAttribute("disciplinas", dao_disciplina.listaDisciplinasPorTurmaId(this.turma_id));
		}
		return "extra_classe/import_novo_edita/disciplina";
	}

	@RequestMapping(value = "/filtro_docente", method = RequestMethod.POST)
	public String filtrarDocente(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		if (request.getParameter("disciplina_id") != null) {
			model.addAttribute("docentes",
					dao_docente.listaDocentesPorDisciplinaId(Long.parseLong(request.getParameter("disciplina_id"))));
		}
		return "extra_classe/import_novo_edita/docente";
	}

}

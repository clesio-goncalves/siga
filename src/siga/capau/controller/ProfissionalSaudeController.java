package siga.capau.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import siga.capau.dao.ProfissionalSaudeDao;
import siga.capau.dao.UsuarioDao;
import siga.capau.modelo.ProfissionalSaude;
import siga.capau.modelo.Usuario;

@Transactional
@Controller
public class ProfissionalSaudeController {

	private List<Usuario> lista_usuarios;
	private ProfissionalSaude profissionalSaude;

	@Autowired
	ProfissionalSaudeDao dao;

	@Autowired
	UsuarioDao dao_usuario;

	@RequestMapping("novoProfissionalSaude")
	public String novoProfissionalSaude(Model model) {

		// Testa se há usuários cadastrados
		if (dao_usuario.lista().size() == 0) {
			return "redirect:novoCurso";
		} else {
			model.addAttribute("usuarios", dao_usuario.lista());
			return "profissional_saude/novo";
		}
	}

	@RequestMapping("adicionaProfissionalSaude")
	public String adiciona(@Valid ProfissionalSaude profissionalSaude, BindingResult result) {

		if (result.hasErrors() && !(profissionalSaude.getTipo_profissional().toString()
				.equals(profissionalSaude.getUsuario().getPerfil().getNome()))) {
			return "redirect:novoProfissionalSaude";
		}

		// Adiciona no banco de dados
		dao.adiciona(profissionalSaude);
		return "redirect:listaProfissionalSaude";
	}

	@RequestMapping("listaProfissionalSaude")
	public String lista(Model model) {
		model.addAttribute("profissionais_saude", dao.lista());
		return "profissional_saude/lista";
	}

	@RequestMapping("removeProfissionalSaude")
	public String remove(ProfissionalSaude profissionalSaude) {
		dao.remove(profissionalSaude);
		return "redirect:listaProfissionalSaude";
	}

	@RequestMapping("exibeProfissionalSaude")
	public String exibe(Long id, Model model) {
		model.addAttribute("profissionalSaude", dao.buscaPorId(id));
		return "profissional_saude/exibe";
	}

	@RequestMapping("editaProfissionalSaude")
	public String edita(Long id, Model model) {

		// Testa se há usuários cadastrados
		if (dao_usuario.lista().size() == 0) {
			return "redirect:novoCurso";
		} else {

			// Alem de inserir as variaveis profissionalSaude, cursos e usuarios, verifica
			// se o profissionalSaude
			// possui usuário e adiciona na lista_usuarios

			profissionalSaude = dao.buscaPorId(id);
			lista_usuarios = dao_usuario.lista();

			model.addAttribute("profissionalSaude", profissionalSaude);

			if (profissionalSaude.getUsuario() != null) {
				lista_usuarios.add(profissionalSaude.getUsuario());
			}

			model.addAttribute("usuarios", lista_usuarios);
			return "profissional_saude/edita";
		}
	}

	@RequestMapping("alteraProfissionalSaude")
	public String altera(@Valid ProfissionalSaude profissionalSaude, BindingResult result) {

		if (result.hasErrors() && !(profissionalSaude.getTipo_profissional().toString()
				.equals(profissionalSaude.getUsuario().getPerfil().getNome()))) {
			return "redirect:editaProfissionalSaude?id=" + profissionalSaude.getId();
		}

		// Altera no banco
		dao.altera(profissionalSaude);
		return "redirect:listaProfissionalSaude";

	}

}

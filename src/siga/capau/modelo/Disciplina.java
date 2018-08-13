package siga.capau.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String nome;

	@Transient
	private List<String> lista_turmas;

	@Transient
	private List<String> lista_docentes;

	@Transient
	private List<Turma> turma;

	@Transient
	private List<Docente> docente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getLista_turmas() {
		return lista_turmas;
	}

	public void setLista_turmas(List<String> lista_turmas) {
		this.lista_turmas = lista_turmas;
	}

	public List<String> getLista_docentes() {
		return lista_docentes;
	}

	public void setLista_docentes(List<String> lista_docentes) {
		this.lista_docentes = lista_docentes;
	}

	public List<Turma> getTurma() {
		return turma;
	}

	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}

	public List<Docente> getDocente() {
		return docente;
	}

	public void setDocente(List<Docente> docente) {
		this.docente = docente;
	}

}

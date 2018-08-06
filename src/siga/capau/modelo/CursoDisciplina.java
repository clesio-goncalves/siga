package siga.capau.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(CursoDisciplinaPK.class)
public class CursoDisciplina {

	@Id
	@NotNull
	@ManyToOne
	private Curso curso;

	@Id
	@NotNull
	@ManyToOne
	private Disciplina disciplina;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}

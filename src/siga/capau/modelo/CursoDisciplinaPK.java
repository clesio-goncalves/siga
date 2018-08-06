package siga.capau.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class CursoDisciplinaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3691452171585806073L;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CursoDisciplinaPK that = (CursoDisciplinaPK) o;

		if (curso != null ? !curso.equals(that.curso) : that.curso != null)
			return false;
		if (disciplina != null ? !disciplina.equals(that.disciplina) : that.disciplina != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = (curso != null ? curso.hashCode() : 0);
		result = 31 * result + (disciplina != null ? disciplina.hashCode() : 0);
		return result;
	}
}

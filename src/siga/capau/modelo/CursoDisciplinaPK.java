package siga.capau.modelo;

import java.io.Serializable;
import java.util.Objects;

public class CursoDisciplinaPK implements Serializable {

	private Long curso;

	private Long disciplina;

	public CursoDisciplinaPK(Long curso, Long disciplina) {
		super();
		this.curso = curso;
		this.disciplina = disciplina;
	}

	public CursoDisciplinaPK() {
	}

	public Long getCurso() {
		return curso;
	}

	public void setCurso(Long curso) {
		this.curso = curso;
	}

	public Long getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Long disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CursoDisciplinaPK))
			return false;
		CursoDisciplinaPK that = (CursoDisciplinaPK) o;
		return Objects.equals(getCurso(), that.getCurso()) && Objects.equals(getDisciplina(), that.getDisciplina());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCurso(), getDisciplina());

	}

}

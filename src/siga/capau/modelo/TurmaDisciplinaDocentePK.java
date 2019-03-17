package siga.capau.modelo;

import java.io.Serializable;
import java.util.Objects;

public class TurmaDisciplinaDocentePK implements Serializable {

	private Long turma;

	private Long disciplina;

	private Long docente;

	public TurmaDisciplinaDocentePK(Long turma, Long disciplina, Long docente) {
		super();
		this.turma = turma;
		this.disciplina = disciplina;
		this.docente = docente;
	}

	public TurmaDisciplinaDocentePK() {
	}

	public Long getTurma() {
		return turma;
	}

	public void setTurma(Long turma) {
		this.turma = turma;
	}

	public Long getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Long disciplina) {
		this.disciplina = disciplina;
	}

	public Long getDocente() {
		return docente;
	}

	public void setDocente(Long docente) {
		this.docente = docente;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TurmaDisciplinaDocentePK))
			return false;
		TurmaDisciplinaDocentePK that = (TurmaDisciplinaDocentePK) o;
		return Objects.equals(getTurma(), that.getTurma()) && Objects.equals(getDisciplina(), that.getDisciplina())
				&& Objects.equals(getDocente(), that.getDocente());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTurma(), getDisciplina(), getDocente());
	}

}

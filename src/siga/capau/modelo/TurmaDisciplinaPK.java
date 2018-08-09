package siga.capau.modelo;

import java.io.Serializable;
import java.util.Objects;

public class TurmaDisciplinaPK implements Serializable {

	private Long turma;

	private Long disciplina;

	public TurmaDisciplinaPK(Long turma, Long disciplina) {
		super();
		this.turma = turma;
		this.disciplina = disciplina;
	}

	public TurmaDisciplinaPK() {
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TurmaDisciplinaPK))
			return false;
		TurmaDisciplinaPK that = (TurmaDisciplinaPK) o;
		return Objects.equals(getTurma(), that.getTurma()) && Objects.equals(getDisciplina(), that.getDisciplina());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTurma(), getDisciplina());

	}

}

package siga.capau.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(TurmaDisciplinaPK.class)
public class TurmaDisciplina {

	@Id
	@ManyToOne
	private Turma turma;

	@Id
	@ManyToOne
	private Disciplina disciplina;

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}

package siga.capau.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(TurmaDisciplinaDocentePK.class)
public class TurmaDisciplinaDocente {

	@Id
	@ManyToOne
	private Turma turma;

	@Id
	@ManyToOne
	private Disciplina disciplina;

	@Id
	@ManyToOne
	private Docente docente;

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

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

}

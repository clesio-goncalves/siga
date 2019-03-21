package siga.capau.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(AlunoExtraClassePK.class)
public class AlunoExtraClasse {

	@Id
	@ManyToOne
	private Aluno aluno;

	@Id
	@ManyToOne
	private ExtraClasse extra_classe;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public ExtraClasse getExtra_classe() {
		return extra_classe;
	}

	public void setExtra_classe(ExtraClasse extra_classe) {
		this.extra_classe = extra_classe;
	}

}

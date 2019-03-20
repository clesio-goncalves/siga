package siga.capau.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(AlunoAtendimentoMonitoriaPK.class)
public class AlunoAtendimentoMonitoria {

	@Id
	@ManyToOne
	private Aluno aluno;

	@Id
	@ManyToOne
	private AtendimentoMonitoria atendimento_monitoria;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public AtendimentoMonitoria getAtendimento_monitoria() {
		return atendimento_monitoria;
	}

	public void setAtendimento_monitoria(AtendimentoMonitoria atendimento_monitoria) {
		this.atendimento_monitoria = atendimento_monitoria;
	}

}

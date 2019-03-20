package siga.capau.modelo;

import java.io.Serializable;
import java.util.Objects;

public class AlunoAtendimentoMonitoriaPK implements Serializable {

	private Long aluno;

	private Long atendimento_monitoria;

	public AlunoAtendimentoMonitoriaPK(Long aluno, Long atendimento_monitoria) {
		super();
		this.aluno = aluno;
		this.atendimento_monitoria = atendimento_monitoria;
	}

	public AlunoAtendimentoMonitoriaPK() {
	}

	public Long getAluno() {
		return aluno;
	}

	public void setAluno(Long aluno) {
		this.aluno = aluno;
	}

	public Long getAtendimento_monitoria() {
		return atendimento_monitoria;
	}

	public void setAtendimento_monitoria(Long atendimento_monitoria) {
		this.atendimento_monitoria = atendimento_monitoria;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AlunoSituacaoPK))
			return false;
		AlunoAtendimentoMonitoriaPK that = (AlunoAtendimentoMonitoriaPK) o;
		return Objects.equals(getAluno(), that.getAluno())
				&& Objects.equals(getAtendimento_monitoria(), that.getAtendimento_monitoria());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAluno(), getAtendimento_monitoria());
	}

}

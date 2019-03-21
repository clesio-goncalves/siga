package siga.capau.modelo;

import java.io.Serializable;
import java.util.Objects;

public class AlunoExtraClassePK implements Serializable {

	private Long aluno;

	private Long extra_classe;

	public AlunoExtraClassePK(Long aluno, Long extra_classe) {
		super();
		this.aluno = aluno;
		this.extra_classe = extra_classe;
	}

	public AlunoExtraClassePK() {
	}

	public Long getAluno() {
		return aluno;
	}

	public void setAluno(Long aluno) {
		this.aluno = aluno;
	}

	public Long getExtra_classe() {
		return extra_classe;
	}

	public void setExtra_classe(Long extra_classe) {
		this.extra_classe = extra_classe;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AlunoSituacaoPK))
			return false;
		AlunoExtraClassePK that = (AlunoExtraClassePK) o;
		return Objects.equals(getAluno(), that.getAluno()) && Objects.equals(getExtra_classe(), that.getExtra_classe());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAluno(), getExtra_classe());
	}

}

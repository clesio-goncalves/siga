package siga.capau.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AlunoSituacaoPK implements Serializable {

	private Long aluno;

	private Long situacao;

	private Date data;

	public AlunoSituacaoPK(Long aluno, Long situacao, Date data) {
		super();
		this.aluno = aluno;
		this.situacao = situacao;
		this.data = data;
	}

	public AlunoSituacaoPK() {
	}

	public Long getAluno() {
		return aluno;
	}

	public void setAluno(Long aluno) {
		this.aluno = aluno;
	}

	public Long getSituacao() {
		return situacao;
	}

	public void setSituacao(Long situacao) {
		this.situacao = situacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AlunoSituacaoPK))
			return false;
		AlunoSituacaoPK that = (AlunoSituacaoPK) o;
		return Objects.equals(getAluno(), that.getAluno()) && Objects.equals(getSituacao(), that.getSituacao())
				&& Objects.equals(getData(), that.getData());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAluno(), getSituacao(), getData());
	}

}

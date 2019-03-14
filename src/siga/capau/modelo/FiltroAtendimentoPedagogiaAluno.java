package siga.capau.modelo;

public class FiltroAtendimentoPedagogiaAluno extends FiltroAtendimento {

	private String assunto;
	private String profissional;
	private String exposicao_motivos;
	private String encaminhamento;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	public String getExposicao_motivos() {
		return exposicao_motivos;
	}

	public void setExposicao_motivos(String exposicao_motivos) {
		this.exposicao_motivos = exposicao_motivos;
	}

	public String getEncaminhamento() {
		return encaminhamento;
	}

	public void setEncaminhamento(String encaminhamento) {
		this.encaminhamento = encaminhamento;
	}

}

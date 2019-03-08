package siga.capau.modelo;

public class FiltroAtendimentoSaude extends FiltroAtendimento {

	private String tipo_atendimento;
	private String profissional;
	private String possui_problema;
	private String esse_problema_dificulta_aprendizado;

	public String getTipo_atendimento() {
		return tipo_atendimento;
	}

	public void setTipo_atendimento(String tipo_atendimento) {
		this.tipo_atendimento = tipo_atendimento;
	}

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	public String getPossui_problema() {
		return possui_problema;
	}

	public void setPossui_problema(String possui_problema) {
		this.possui_problema = possui_problema;
	}

	public String getEsse_problema_dificulta_aprendizado() {
		return esse_problema_dificulta_aprendizado;
	}

	public void setEsse_problema_dificulta_aprendizado(String esse_problema_dificulta_aprendizado) {
		this.esse_problema_dificulta_aprendizado = esse_problema_dificulta_aprendizado;
	}

}

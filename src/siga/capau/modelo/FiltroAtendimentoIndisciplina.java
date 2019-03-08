package siga.capau.modelo;

public class FiltroAtendimentoIndisciplina extends FiltroAtendimento {

	private String advertido;
	private String tipo_advertencia;
	private String profissional;
	private String descricao;

	public String getAdvertido() {
		return advertido;
	}

	public void setAdvertido(String advertido) {
		this.advertido = advertido;
	}

	public String getTipo_advertencia() {
		return tipo_advertencia;
	}

	public void setTipo_advertencia(String tipo_advertencia) {
		this.tipo_advertencia = tipo_advertencia;
	}

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

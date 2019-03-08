package siga.capau.modelo;

public class FiltroAtendimentoMonitoria extends FiltroAtendimento {

	private String disciplina;
	private String monitor;
	private String local;
	private String conteudo;
	private String dificuldades_diagnosticadas;
	private String status_atendimento;

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getDificuldades_diagnosticadas() {
		return dificuldades_diagnosticadas;
	}

	public void setDificuldades_diagnosticadas(String dificuldades_diagnosticadas) {
		this.dificuldades_diagnosticadas = dificuldades_diagnosticadas;
	}

	public String getStatus_atendimento() {
		return status_atendimento;
	}

	public void setStatus_atendimento(String status_atendimento) {
		this.status_atendimento = status_atendimento;
	}

}

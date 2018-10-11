package siga.capau.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FiltroAtendimentoSaude {

	private String data_inicial_atendimento;
	private String data_final_atendimento;
	private String horario_inicial_atendimento;
	private String horario_final_atendimento;
	private String curso;
	private String turma;
	private String tipo_atendimento;
	private String aluno;
	private String profissional;
	private String possui_problema;
	private String esse_problema_dificulta_aprendizado;
	private String[] data_formatada;

	public String getData_inicial_atendimento() {
		return data_inicial_atendimento;
	}

	public void setData_inicial_atendimento(String data_inicial_atendimento) {
		this.data_inicial_atendimento = data_inicial_atendimento;
	}

	public String getData_final_atendimento() {
		return data_final_atendimento;
	}

	public void setData_final_atendimento(String data_final_atendimento) {
		this.data_final_atendimento = data_final_atendimento;
	}

	public String getHorario_inicial_atendimento() {
		return horario_inicial_atendimento;
	}

	public void setHorario_inicial_atendimento(String horario_inicial_atendimento) {
		this.horario_inicial_atendimento = horario_inicial_atendimento;
	}

	public String getHorario_final_atendimento() {
		return horario_final_atendimento;
	}

	public void setHorario_final_atendimento(String horario_final_atendimento) {
		this.horario_final_atendimento = horario_final_atendimento;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getTipo_atendimento() {
		return tipo_atendimento;
	}

	public void setTipo_atendimento(String tipo_atendimento) {
		this.tipo_atendimento = tipo_atendimento;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
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

	public String[] getData_formatada() {
		return data_formatada;
	}

	public void setData_formatada(String[] data_formatada) {
		this.data_formatada = data_formatada;
	}

	public String retornaDataFinal() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(calendar.getTime());
	}

	public String formataData(String data) {
		this.data_formatada = data.split("/");
		return this.data_formatada[2] + "-" + this.data_formatada[1] + "-" + this.data_formatada[0];
	}

}

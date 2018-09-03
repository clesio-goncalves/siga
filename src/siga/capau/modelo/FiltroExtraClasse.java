package siga.capau.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FiltroExtraClasse {

	private String data_inicial_atendimento;
	private String data_final_atendimento;
	private String horario_inicial_atendimento;
	private String horario_final_atendimento;
	private String aluno;
	private String disciplina;
	private String docente;
	private String local;
	private String conteudo;
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

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getDocente() {
		return docente;
	}

	public void setDocente(String docente) {
		this.docente = docente;
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

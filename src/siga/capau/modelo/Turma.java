package siga.capau.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String nome;

	@NotNull
	@ManyToOne
	private Curso curso;

	@Transient
	private int ano_ingresso;

	@Transient
	private int periodo_ingresso;

	@Transient
	private String tipo_turma;

	@Transient
	private Long qnt_alunos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public int getAno_ingresso() {
		return ano_ingresso;
	}

	public void setAno_ingresso(int ano_ingresso) {
		this.ano_ingresso = ano_ingresso;
	}

	public int getPeriodo_ingresso() {
		return periodo_ingresso;
	}

	public void setPeriodo_ingresso(int periodo_ingresso) {
		this.periodo_ingresso = periodo_ingresso;
	}

	public String getTipo_turma() {
		return tipo_turma;
	}

	public void setTipo_turma(String tipo_turma) {
		this.tipo_turma = tipo_turma;
	}

	public Long getQnt_alunos() {
		return qnt_alunos;
	}

	public void setQnt_alunos(Long qnt_alunos) {
		this.qnt_alunos = qnt_alunos;
	}

}

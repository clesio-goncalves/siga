package siga.capau.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class AtendimentoPedagogiaFamilia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;

	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horario_inicial;

	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horario_final;

	@NotNull
	private String responsavel;

	@Size(max = 20)
	private String telefone;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String relato;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String encaminhamento;

	@NotNull
	@ManyToOne
	private Aluno aluno;

	@NotNull
	@ManyToOne
	private Profissional profissional;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHorario_inicial() {
		return horario_inicial;
	}

	public void setHorario_inicial(Date horario_inicial) {
		this.horario_inicial = horario_inicial;
	}

	public Date getHorario_final() {
		return horario_final;
	}

	public void setHorario_final(Date horario_final) {
		this.horario_final = horario_final;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getRelato() {
		return relato;
	}

	public void setRelato(String relato) {
		this.relato = relato;
	}

	public String getEncaminhamento() {
		return encaminhamento;
	}

	public void setEncaminhamento(String encaminhamento) {
		this.encaminhamento = encaminhamento;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

}

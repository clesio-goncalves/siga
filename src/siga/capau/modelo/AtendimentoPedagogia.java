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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class AtendimentoPedagogia {

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

	private boolean dificuldades_ensino_aprendizagem;

	private boolean ausencia_professor;

	private boolean relacao_professor_aluno;

	private boolean indisciplina;

	private String outros;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String exposicao_motivos;

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

	public boolean isDificuldades_ensino_aprendizagem() {
		return dificuldades_ensino_aprendizagem;
	}

	public void setDificuldades_ensino_aprendizagem(boolean dificuldades_ensino_aprendizagem) {
		this.dificuldades_ensino_aprendizagem = dificuldades_ensino_aprendizagem;
	}

	public boolean isAusencia_professor() {
		return ausencia_professor;
	}

	public void setAusencia_professor(boolean ausencia_professor) {
		this.ausencia_professor = ausencia_professor;
	}

	public boolean isRelacao_professor_aluno() {
		return relacao_professor_aluno;
	}

	public void setRelacao_professor_aluno(boolean relacao_professor_aluno) {
		this.relacao_professor_aluno = relacao_professor_aluno;
	}

	public boolean isIndisciplina() {
		return indisciplina;
	}

	public void setIndisciplina(boolean indisciplina) {
		this.indisciplina = indisciplina;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
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

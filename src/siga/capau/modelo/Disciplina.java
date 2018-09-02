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
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String nome;

	@ManyToOne
	private Monitor monitor;

	@Transient
	private String turmas;

	@Transient
	private String docentes;

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

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	public String getTurmas() {
		return turmas;
	}

	public void setTurmas(String turmas) {
		this.turmas = turmas;
	}

	public String getDocentes() {
		return docentes;
	}

	public void setDocentes(String docentes) {
		this.docentes = docentes;
	}

}

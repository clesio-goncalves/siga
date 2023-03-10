package siga.capau.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Profissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	@Column(unique = true)
	private int siape;

	@NotNull
	private String tipo_atendimento;

	@OneToOne
	@NotNull
	private Usuario usuario;

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

	public int getSiape() {
		return siape;
	}

	public void setSiape(int siape) {
		this.siape = siape;
	}

	public String getTipo_atendimento() {
		return tipo_atendimento;
	}

	public void setTipo_atendimento(String tipo_atendimento) {
		this.tipo_atendimento = tipo_atendimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

package siga.capau.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Perfil implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String nome;

	@Transient
	private Long qnt_usuarios_ativos;

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

	@Override
	public String getAuthority() {
		return this.nome;
	}

	public Long getQnt_usuarios_ativos() {
		return qnt_usuarios_ativos;
	}

	public void setQnt_usuarios_ativos(Long qnt_usuarios_ativos) {
		this.qnt_usuarios_ativos = qnt_usuarios_ativos;
	}

}

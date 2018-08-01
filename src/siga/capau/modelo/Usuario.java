package siga.capau.modelo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 5, max = 50)
	@Column(unique = true)
	private String usuario;

	@NotNull
	private String senha;

	@Transient
	private String repetir_senha;
	// O atributo repetir_senha não será persistido no banco

	private boolean ativo;

	@NotNull
	@ManyToOne
	private Permissao permissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRepetir_senha() {
		return repetir_senha;
	}

	public void setRepetir_senha(String repetir_senha) {
		this.repetir_senha = repetir_senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Permissao> permissoes = new HashSet<>();
		
		this.permissao.setNome("ROLE_" + this.permissao.getNome());
		
		permissoes.add(this.permissao);

		return permissoes;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.ativo;
	}

	public boolean comparaSenhas() {
		if (this.senha.equals(this.repetir_senha)) {
			return true;
		} else {
			return false;
		}
	}

}

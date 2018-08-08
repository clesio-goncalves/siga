package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import siga.capau.modelo.Usuario;

@Repository
public class UsuarioDao implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Usuario usuario) {
		manager.persist(usuario);
	}

	public void altera(Usuario usuario) {
		manager.merge(usuario);
	}

	public List<Usuario> lista() {
		return manager.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}

	// Seleciona todos os usuários do tipo aluno que não estão vinculados a nenhum
	// aluno
	public List<Usuario> listaUsuarioAlunoSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.perfil.nome like 'Aluno' and u.id not in (select a.usuario.id from Aluno as a where a.usuario.id is not null)",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioProfissionalSaudeSemVinculo(String tipo_profissional) {
		return manager.createQuery(
				"select u from Usuario u where u.perfil.nome like :tipo_profissional and u.id not in (select ps.usuario.id from ProfissionalSaude as ps)",
				Usuario.class).setParameter("tipo_profissional", tipo_profissional).getResultList();
	}

	// seleciona todos os usuário do tipo Psicologo, Assistente Social, Enfermeiro
	// ou Odontologo que ainda estão sem vinculo com algum desses profissionais da
	// saúde
	public List<Usuario> listaUsuarioProfissionalSaudeSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.perfil.nome in ('Psicólogo', 'Assistente Social', 'Enfermeiro', 'Odontólogo') and u.id not in (select ps.usuario.id from ProfissionalSaude as ps)",
				Usuario.class).getResultList();
	}

	// seleciona todos os usuário do tipo Diretor, Coordenador ou Administrador
	// que ainda estão sem vinculo com algum desses servidores
	public List<Usuario> listaUsuarioServidorSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.perfil.nome in ('Administrador', 'Coordenador', 'Diretor') and u.id not in (select s.usuario.id from Servidor as s)",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioServidorSemVinculo(String funcao) {
		return manager.createQuery(
				"select u from Usuario u where u.perfil.nome like :funcao and u.id not in (select s.usuario.id from Servidor as s)",
				Usuario.class).setParameter("funcao", funcao).getResultList();
	}

	public List<Usuario> buscaPorEmail(String email) {
		return manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", email).getResultList();
	}

	public Usuario buscaPorId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public void remove(Usuario usuario) {
		manager.remove(buscaPorId(usuario.getId()));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = manager.createQuery("select u from Usuario u where u.email = :usuario", Usuario.class)
				.setParameter("usuario", username).getSingleResult();

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}

		return usuario;
	}

}

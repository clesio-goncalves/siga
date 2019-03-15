package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import siga.capau.modelo.FiltroUsuario;
import siga.capau.modelo.Usuario;

@Repository
public class UsuarioDao implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	private String sql;
	private boolean where;

	public void adiciona(Usuario usuario) {
		manager.persist(usuario);
	}

	public void altera(Usuario usuario) {
		manager.merge(usuario);
	}

	public List<Usuario> lista() {
		return manager.createQuery("select u from Usuario u where u.ativo = true order by u.id desc", Usuario.class)
				.getResultList();
	}

	// Seleciona todos os usuários do tipo aluno que não estão vinculados a nenhum
	// aluno
	public List<Usuario> listaUsuarioAlunoSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome like 'Aluno' and u.id not in (select a.usuario.id from Aluno as a where a.usuario.id is not null)",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioMonitorSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome like 'Monitor' and u.id not in (select m.usuario.id from Monitor as m where m.usuario.id is not null)",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioDocenteSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome like 'Docente' and u.id not in (select d.usuario.id from Docente as d where d.usuario.id is not null)",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioProfissionalSemVinculo(String tipo_atendimento) {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome like :tipo_atendimento and u.id not in (select p.usuario.id from Profissional as p)",
				Usuario.class).setParameter("tipo_atendimento", tipo_atendimento).getResultList();
	}

	// seleciona todos os usuário do tipo Psicologo, Assistente Social, Enfermeiro
	// ou Odontologo que ainda estão sem vinculo com algum desses profissionais da
	// saúde
	public List<Usuario> listaUsuarioProfissionalSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome in ('Psicologia', 'Assistência Social', 'Enfermagem', 'Odontologia', 'Pedagogia') and u.id not in (select p.usuario.id from Profissional as p)",
				Usuario.class).getResultList();
	}

	// seleciona todos os usuário do tipo Diretor, Coordenador ou Administrador
	// que ainda estão sem vinculo com algum desses administracaoes
	public List<Usuario> listaUsuarioAdministracaoSemVinculo() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome in ('Administrador', 'Coordenador', 'Diretor') and u.id not in (select s.usuario.id from Administracao as s)",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioAdministracaoSemVinculo(String funcao) {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.nome like :funcao and u.id not in (select s.usuario.id from Administracao as s)",
				Usuario.class).setParameter("funcao", funcao).getResultList();
	}

	public List<Usuario> listaUsuarioManipulavelPorCoordenadorPedagogia() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.id IN (9, 10, 11) order by u.id desc",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioManipulavelPorDiretor() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.id NOT IN (1, 3) order by u.id desc",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioAlunoManipulavel() {
		return manager.createQuery("select u from Usuario u where u.ativo=true and u.perfil.id = 11 order by u.id desc",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioManipulavelPorDocente() {
		return manager.createQuery(
				"select u from Usuario u where u.ativo=true and u.perfil.id IN (10, 11) order by u.id desc",
				Usuario.class).getResultList();
	}

	public List<Usuario> listaUsuarioManipulavelPorCD() {
		return manager
				.createQuery("select u from Usuario u where u.ativo=true and u.perfil.id IN (9, 11) order by u.id desc",
						Usuario.class)
				.getResultList();
	}

	public List<Usuario> buscaPorEmail(String email) {
		return manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", email).getResultList();
	}

	public Usuario buscaPorId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public Long buscarPerfilIdPeloUsuarioId(Long id) {
		return manager.createQuery("select u.perfil.id from Usuario u where u.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public Long buscaQntUsuariosAtivosPorPerfilId(Long perfil_id) {
		return manager.createQuery(
				"select count(u.perfil.id) from Usuario u where u.ativo = true and u.perfil.id = :perfil_id",
				Long.class).setParameter("perfil_id", perfil_id).getSingleResult();
	}

	public void remove(Long id) {
		manager.createQuery("delete from Usuario u where u.id = :id").setParameter("id", id).executeUpdate();
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

	public List<Usuario> filtraUsuario(FiltroUsuario filtro_usuario) {

		sql = "select u from Usuario u";
		this.where = false;

		// Email
		if (!filtro_usuario.getEmail().equals("")) {
			this.where = true;
			sql = sql + " where u.email like '%" + filtro_usuario.getEmail() + "%'";
		}

		// Perfil
		if (!filtro_usuario.getPerfil().equals("")) {
			testeWhere();
			sql = sql + " u.perfil.id = " + filtro_usuario.getPerfil();
		}

		// Situação
		if (!filtro_usuario.getSituacao().equals("")) {
			testeWhere();
			sql = sql + " u.ativo = " + filtro_usuario.getSituacao();
		}

		sql = sql + " order by u.id desc";

		return manager.createQuery(sql, Usuario.class).getResultList();

	}

	private void testeWhere() {
		if (this.where == false) {
			this.where = true;
			sql = sql + " where";
		} else {
			sql = sql + " and";
		}
	}

}

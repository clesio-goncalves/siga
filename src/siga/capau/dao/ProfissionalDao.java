package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Profissional;

@Repository
public class ProfissionalDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Profissional profissional) {
		manager.persist(profissional);
	}

	public void altera(Profissional profissional) {
		manager.merge(profissional);
	}

	public List<Profissional> lista() {
		return manager.createQuery("select p from Profissional p", Profissional.class).getResultList();
	}

	public List<Profissional> buscaPorSiape(int siape) {
		return manager.createQuery("select p from Profissional p where p.siape = :siape", Profissional.class)
				.setParameter("siape", siape).getResultList();
	}

	public List<Profissional> buscaPorUsuario(Long id) {
		return manager.createQuery("select p from Profissional p where p.usuario.id = :id", Profissional.class)
				.setParameter("id", id).getResultList();
	}

	public Long usuarioProfissional(Long id) {
		return manager.createQuery("select count(p) from Profissional p where p.usuario.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public Profissional buscaPorId(Long id) {
		return manager.find(Profissional.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Profissional p where p.id = :id").setParameter("id", id).executeUpdate();
	}

}

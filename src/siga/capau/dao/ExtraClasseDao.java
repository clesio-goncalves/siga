package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.ExtraClasse;

@Repository
public class ExtraClasseDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(ExtraClasse extraClasse) {
		manager.persist(extraClasse);
	}

	public void altera(ExtraClasse extraClasse) {
		manager.merge(extraClasse);
	}

	public List<ExtraClasse> lista() {
		return manager.createQuery("select e from ExtraClasse e", ExtraClasse.class).getResultList();
	}

	public ExtraClasse buscaPorId(Long id) {
		return manager.find(ExtraClasse.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from ExtraClasse a where a.id = :id").setParameter("id", id).executeUpdate();
	}

}

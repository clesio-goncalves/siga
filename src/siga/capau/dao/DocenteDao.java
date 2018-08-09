package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Docente;

@Repository
public class DocenteDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Docente docente) {
		manager.persist(docente);
	}

	public void altera(Docente docente) {
		manager.merge(docente);
	}

	public List<Docente> lista() {
		return manager.createQuery("select d from Docente d", Docente.class).getResultList();
	}

	public List<Docente> buscaPorSiape(int siape) {
		return manager.createQuery("select d from Docente d where d.siape = :siape", Docente.class)
				.setParameter("siape", siape).getResultList();
	}

	public Docente buscaPorId(Long id) {
		return manager.find(Docente.class, id);
	}

	public void remove(Docente docente) {
		manager.remove(buscaPorId(docente.getId()));
	}

}

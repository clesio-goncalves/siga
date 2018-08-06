package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Disciplina;

@Repository
public class DisciplinaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Disciplina disciplina) {
		manager.persist(disciplina);
	}

	public void altera(Disciplina disciplina) {
		manager.merge(disciplina);
	}

	public List<Disciplina> lista() {
		return manager.createQuery("select d from Disciplina d", Disciplina.class).getResultList();
	}

	public Disciplina buscaPorId(Long id) {
		return manager.find(Disciplina.class, id);
	}

	public void remove(Disciplina disciplina) {
		manager.remove(buscaPorId(disciplina.getId()));
	}

}

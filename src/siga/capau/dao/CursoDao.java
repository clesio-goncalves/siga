package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Curso;

@Repository
public class CursoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Curso curso) {
		manager.persist(curso);
	}

	public void altera(Curso curso) {
		manager.merge(curso);
	}

	public List<Curso> lista() {
		return manager.createQuery("select c from Curso c", Curso.class).getResultList();
	}

	public Curso buscaPorId(Long id) {
		return manager.find(Curso.class, id);
	}

	public void remove(Curso curso) {
		manager.remove(buscaPorId(curso.getId()));
	}

}

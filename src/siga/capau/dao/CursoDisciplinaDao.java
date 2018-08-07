package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.CursoDisciplina;

@Repository
public class CursoDisciplinaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(CursoDisciplina curso_disciplina) {
		manager.persist(curso_disciplina);
	}

	public void altera(CursoDisciplina curso_disciplina) {
		manager.merge(curso_disciplina);
	}

	public List<CursoDisciplina> lista() {
		return manager.createQuery("select cd from CursoDisciplina cd", CursoDisciplina.class).getResultList();
	}

	public CursoDisciplina buscaPorId(Long id) {
		return manager.find(CursoDisciplina.class, id);
	}

}

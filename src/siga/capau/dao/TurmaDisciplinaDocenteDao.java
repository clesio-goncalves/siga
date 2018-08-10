package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.TurmaDisciplinaDocente;

@Repository
public class TurmaDisciplinaDocenteDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(TurmaDisciplinaDocente turma_disciplina_docente) {
		manager.persist(turma_disciplina_docente);
	}

	public void altera(TurmaDisciplinaDocente turma_disciplina_docente) {
		manager.merge(turma_disciplina_docente);
	}

	public List<TurmaDisciplinaDocente> lista() {
		return manager.createQuery("select tdd from TurmaDisciplinaDocente tdd", TurmaDisciplinaDocente.class)
				.getResultList();
	}

	public TurmaDisciplinaDocente buscaPorId(Long id) {
		return manager.find(TurmaDisciplinaDocente.class, id);
	}

	public void removeTurmaDisciplinaDocente(Long turma_id, Long disciplina_id, Long docente_id) {
		manager.createQuery(
				"delete from TurmaDisciplinaDocente tdd where tdd.turma.id = :turma_id and tdd.disciplina.id = :disciplina_id and tdd.docente.id = :docente_id")
				.setParameter("turma_id", turma_id).setParameter("disciplina_id", disciplina_id)
				.setParameter("docente_id", docente_id).executeUpdate();
	}

	public void removeTurmaDisciplinaDocentePelaDisciplinaId(Long id) {
		manager.createQuery("delete from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :id")
				.setParameter("id", id).executeUpdate();
	}

}

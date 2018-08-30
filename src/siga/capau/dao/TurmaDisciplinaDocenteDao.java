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

	public List<TurmaDisciplinaDocente> buscaTurmaDisciplinaDocentePorDisciplinaId(Long id) {
		return manager.createQuery("select tdd from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :id",
				TurmaDisciplinaDocente.class).setParameter("id", id).getResultList();
	}

	public List<Long> buscaTurmaPorDisciplinaId(Long id) {
		return manager.createQuery("select tdd.turma.id from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :id",
				Long.class).setParameter("id", id).getResultList();
	}

	public TurmaDisciplinaDocente buscaPorId(Long id) {
		return manager.find(TurmaDisciplinaDocente.class, id);
	}

	public void removeTurmaDisciplinaDocentePelaDisciplinaId(Long id) {
		manager.createQuery("delete from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :id")
				.setParameter("id", id).executeUpdate();
	}

	public void removeTurmaDisciplinaDocentePelaTurmaId(Long id) {
		manager.createQuery("delete from TurmaDisciplinaDocente tdd where tdd.turma.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public void removeTurmaDisciplinaDocentePeloDocenteId(Long id) {
		manager.createQuery("delete from TurmaDisciplinaDocente tdd where tdd.docente.id = :id").setParameter("id", id)
				.executeUpdate();
	}

}
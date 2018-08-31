package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Docente;
import siga.capau.modelo.TurmaDisciplinaDocente;

@Repository
public class TurmaDisciplinaDocenteDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Long disicplina_id, Long turma_id, Long docente_id) {
		manager.createNativeQuery(
				"insert into TurmaDisciplinaDocente (disciplina_id, turma_id, docente_id) values (:disicplina_id, :turma_id, :docente_id)")
				.setParameter("disicplina_id", disicplina_id).setParameter("turma_id", turma_id)
				.setParameter("docente_id", docente_id).executeUpdate();
	}

	public void alteraDocente(Long disicplina_id, Long turma_id, Long docente_id) {
		manager.createQuery(
				"update TurmaDisciplinaDocente tdd set tdd.docente.id = :docente_id where tdd.disciplina.id = :disicplina_id and tdd.turma.id = :turma_id")
				.setParameter("docente_id", docente_id).setParameter("disicplina_id", disicplina_id)
				.setParameter("turma_id", turma_id).executeUpdate();
	}

	public List<Docente> buscaDocentesVinculados() {
		return manager
				.createQuery("select tdd.docente from TurmaDisciplinaDocente tdd group by tdd.docente", Docente.class)
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

	public void removeTurmaDisciplinaDocentePelaDisciplinaIdAndTurmaId(Long disciplina_id, Long turma_id) {
		manager.createQuery(
				"delete from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :disciplina_id and tdd.turma.id = :turma_id")
				.setParameter("disciplina_id", disciplina_id).setParameter("turma_id", turma_id).executeUpdate();
	}

}

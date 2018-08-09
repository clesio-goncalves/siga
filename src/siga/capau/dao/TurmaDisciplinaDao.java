package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.TurmaDisciplina;

@Repository
public class TurmaDisciplinaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(TurmaDisciplina turma_disciplina) {
		manager.persist(turma_disciplina);
	}

	public void altera(TurmaDisciplina turma_disciplina) {
		manager.merge(turma_disciplina);
	}

	public List<TurmaDisciplina> lista() {
		return manager.createQuery("select td from TurmaDisciplina td", TurmaDisciplina.class).getResultList();
	}

	public TurmaDisciplina buscaPorId(Long id) {
		return manager.find(TurmaDisciplina.class, id);
	}

	public void removeTurmaDisciplina(Long turma_id, Long disciplina_id) {
		manager.createQuery(
				"delete from TurmaDisciplina td where td.turma.id = :turma_id and td.disciplina.id = :disciplina_id")
				.setParameter("turma_id", turma_id).setParameter("disciplina_id", disciplina_id).executeUpdate();
	}

	public void removeTurmaDisciplinaPelaDisciplinaId(Long id) {
		manager.createQuery("delete from TurmaDisciplina td where td.disciplina.id = :id").setParameter("id", id)
				.executeUpdate();
	}

}

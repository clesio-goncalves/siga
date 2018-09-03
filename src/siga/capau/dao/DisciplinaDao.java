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

	public Disciplina adiciona(Disciplina disciplina) {
		manager.persist(disciplina);
		return disciplina;
	}

	public void altera(Disciplina disciplina) {
		manager.merge(disciplina);
	}

	public List<Disciplina> lista() {
		return manager.createQuery("select d from Disciplina d", Disciplina.class).getResultList();
	}

	public List<Disciplina> listaDisciplinasPorTurmaId(Long turma_id) {
		return manager.createQuery(
				"select d from Disciplina d inner join TurmaDisciplinaDocente tdd on tdd.disciplina.id = d.id where tdd.turma.id = :turma_id",
				Disciplina.class).setParameter("turma_id", turma_id).getResultList();
	}

	public List<Disciplina> listaDisciplinasPorTurmaIdMonitorNotNull(Long turma_id) {
		return manager.createQuery(
				"select d from Disciplina d inner join TurmaDisciplinaDocente tdd on tdd.disciplina.id = d.id where tdd.turma.id = :turma_id and d.monitor.id is not null",
				Disciplina.class).setParameter("turma_id", turma_id).getResultList();
	}

	public List<Disciplina> listaDisciplinasPorMonitorId(Long monitor_id) {
		return manager.createQuery("select d from Disciplina d where d.monitor.id = :monitor_id", Disciplina.class)
				.setParameter("monitor_id", monitor_id).getResultList();
	}

	public String buscaMonitorPelaDisciplinaId(Long disciplina_id) {
		return manager.createQuery("select d.monitor.nome from Disciplina d where d.id = :disciplina_id", String.class)
				.setParameter("disciplina_id", disciplina_id).getSingleResult();
	}

	public List<Disciplina> buscaPorNome(String nome) {
		return manager.createQuery("select d from Disciplina d where d.nome = :nome", Disciplina.class)
				.setParameter("nome", nome).getResultList();
	}

	public Disciplina buscaPorId(Long id) {
		return manager.find(Disciplina.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Disciplina d where d.id = :id").setParameter("id", id).executeUpdate();
	}

}

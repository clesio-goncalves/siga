package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoMonitoria;

@Repository
public class AtendimentoMonitoriaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(AtendimentoMonitoria AtendimentoMonitoria) {
		manager.persist(AtendimentoMonitoria);
	}

	public void altera(AtendimentoMonitoria AtendimentoMonitoria) {
		manager.merge(AtendimentoMonitoria);
	}

	public List<AtendimentoMonitoria> lista() {
		return manager.createQuery("select am from AtendimentoMonitoria am", AtendimentoMonitoria.class)
				.getResultList();
	}

	public List<AtendimentoMonitoria> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select am from AtendimentoMonitoria am where am.aluno.id = :aluno_id",
				AtendimentoMonitoria.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoMonitoria> buscaPeloMonitorId(Long monitor_id) {
		return manager
				.createQuery("select am from AtendimentoMonitoria am where am.disciplina.monitor.id = :monitor_id",
						AtendimentoMonitoria.class)
				.setParameter("monitor_id", monitor_id).getResultList();
	}

	public List<AtendimentoMonitoria> buscaPelaDisciplinaId(Long disciplina_id) {
		return manager.createQuery("select am from AtendimentoMonitoria am where am.disciplina.id = :disciplina_id",
				AtendimentoMonitoria.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public AtendimentoMonitoria buscaPorId(Long id) {
		return manager.find(AtendimentoMonitoria.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoMonitoria am where am.id = :id").setParameter("id", id)
				.executeUpdate();
	}

}

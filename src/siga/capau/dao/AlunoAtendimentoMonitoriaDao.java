package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AlunoAtendimentoMonitoria;

@Repository
public class AlunoAtendimentoMonitoriaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Long aluno_id, Long atendimento_monitoria_id) {
		manager.createNativeQuery(
				"insert into AlunoAtendimentoMonitoria (aluno_id, atendimento_monitoria_id) values (:aluno_id, :atendimento_monitoria_id)")
				.setParameter("aluno_id", aluno_id).setParameter("atendimento_monitoria_id", atendimento_monitoria_id)
				.executeUpdate();
	}

	public List<AlunoAtendimentoMonitoria> buscaAlunoId(Long aluno_id) {
		return manager.createQuery("select a from AlunoAtendimentoMonitoria a where a.aluno.id = :aluno_id",
				AlunoAtendimentoMonitoria.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AlunoAtendimentoMonitoria> buscaPorAtendimentoId(Long atendimento_monitoria_id) {
		return manager.createQuery(
				"select a from AlunoAtendimentoMonitoria a where a.atendimento_monitoria.id = :atendimento_monitoria_id",
				AlunoAtendimentoMonitoria.class).setParameter("atendimento_monitoria_id", atendimento_monitoria_id)
				.getResultList();
	}

	public void removePeloAlunoId(Long id) {
		manager.createQuery("delete from AlunoAtendimentoMonitoria a where a.aluno.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public void removePeloAtendimentoId(Long id) {
		manager.createQuery("delete from AlunoAtendimentoMonitoria a where a.atendimento_monitoria.id = :id")
				.setParameter("id", id).executeUpdate();
	}

}

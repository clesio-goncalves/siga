package siga.capau.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AlunoSituacao;

@Repository
public class AlunoSituacaoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Long aluno_id, Long situacao_id, Date data) {
		manager.createNativeQuery(
				"insert into AlunoSituacao (aluno_id, situacao_id, data) values (:aluno_id, :situacao_id, :data)")
				.setParameter("aluno_id", aluno_id).setParameter("situacao_id", situacao_id).setParameter("data", data)
				.executeUpdate();
	}

	public List<AlunoSituacao> buscaAlunoSituacaoPorAlunoId(Long aluno_id) {
		return manager.createQuery("select a from AlunoSituacao a where a.aluno.id = :aluno_id order by a.data desc", AlunoSituacao.class)
				.setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AlunoSituacao> buscaAlunoSituacaoPorSituacaoId(Long situacao_id) {
		return manager
				.createQuery("select a from AlunoSituacao a where a.situacao.id = :situacao_id  order by a.data desc", AlunoSituacao.class)
				.setParameter("situacao_id", situacao_id).getResultList();
	}
}

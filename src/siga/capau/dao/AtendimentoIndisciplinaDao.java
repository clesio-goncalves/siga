package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoIndisciplina;

@Repository
public class AtendimentoIndisciplinaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(AtendimentoIndisciplina atendimento_indisciplina) {
		manager.persist(atendimento_indisciplina);
	}

	public void altera(AtendimentoIndisciplina atendimento_indisciplina) {
		manager.merge(atendimento_indisciplina);
	}

	public List<AtendimentoIndisciplina> lista() {
		return manager.createQuery("select ai from AtendimentoIndisciplina ai", AtendimentoIndisciplina.class)
				.getResultList();
	}

	public List<AtendimentoIndisciplina> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select ai from AtendimentoIndisciplina ai where ai.aluno.id = :aluno_id",
				AtendimentoIndisciplina.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoIndisciplina> buscaPeloProfissionalId(Long profissional_id) {
		return manager
				.createQuery("select ai from AtendimentoIndisciplina ai where ai.profissional.id = :profissional_id",
						AtendimentoIndisciplina.class)
				.setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoIndisciplina buscaPorId(Long id) {
		return manager.find(AtendimentoIndisciplina.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoIndisciplina ai where ai.id = :id").setParameter("id", id)
				.executeUpdate();
	}

}

package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoPedagogia;

@Repository
public class AtendimentoPedagogiaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(AtendimentoPedagogia atendimento_saude) {
		manager.persist(atendimento_saude);
	}

	public void altera(AtendimentoPedagogia atendimento_saude) {
		manager.merge(atendimento_saude);
	}

	public List<AtendimentoPedagogia> lista() {
		return manager.createQuery("select a from AtendimentoPedagogia a", AtendimentoPedagogia.class).getResultList();
	}

	public List<AtendimentoPedagogia> buscaPeloAlunoId(Long aluno_id) {
		return manager
				.createQuery("select a from AtendimentoPedagogia a where a.aluno.id = :aluno_id", AtendimentoPedagogia.class)
				.setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoPedagogia> buscaPeloProfissionalId(Long profissional_id) {
		return manager.createQuery("select a from AtendimentoPedagogia a where a.profissional.id = :profissional_id",
				AtendimentoPedagogia.class).setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoPedagogia buscaPorId(Long id) {
		return manager.find(AtendimentoPedagogia.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoPedagogia a where a.id = :id").setParameter("id", id).executeUpdate();
	}

}

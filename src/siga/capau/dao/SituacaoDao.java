package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Situacao;

@Repository
public class SituacaoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Situacao situacao) {
		manager.persist(situacao);
	}

	public void altera(Situacao situacao) {
		manager.merge(situacao);
	}

	public List<Situacao> lista() {
		return manager.createQuery("select s from Situacao s order by s.id", Situacao.class).getResultList();
	}

	public List<Situacao> buscaPorNome(String nome) {
		return manager.createQuery("select s from Situacao s where s.nome = :nome", Situacao.class)
				.setParameter("nome", nome).getResultList();
	}

	public Situacao buscaPorId(Long id) {
		return manager.find(Situacao.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Situacao s where s.id = :id").setParameter("id", id).executeUpdate();
	}

}

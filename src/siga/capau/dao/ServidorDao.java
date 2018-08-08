package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Servidor;

@Repository
public class ServidorDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Servidor servidor) {
		manager.persist(servidor);
	}

	public void altera(Servidor servidor) {
		manager.merge(servidor);
	}

	public List<Servidor> lista() {
		return manager.createQuery("select s from Servidor s", Servidor.class).getResultList();
	}

	public List<Servidor> buscaPorSiape(int siape) {
		return manager.createQuery("select s from Servidor s where s.siape = :siape", Servidor.class)
				.setParameter("siape", siape).getResultList();
	}

	public Servidor buscaPorId(Long id) {
		return manager.find(Servidor.class, id);
	}

	public void remove(Servidor servidor) {
		manager.remove(buscaPorId(servidor.getId()));
	}

}

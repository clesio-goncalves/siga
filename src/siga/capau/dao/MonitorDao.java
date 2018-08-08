package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Monitor;

@Repository
public class MonitorDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Monitor monitor) {
		manager.persist(monitor);
	}

	public void altera(Monitor monitor) {
		manager.merge(monitor);
	}

	public List<Monitor> lista() {
		return manager.createQuery("select m from Monitor m", Monitor.class).getResultList();
	}

	public List<Monitor> buscaPorMatricula(String matricula) {
		return manager.createQuery("select m from Monitor m where m.matricula = :matricula", Monitor.class)
				.setParameter("matricula", matricula).getResultList();
	}

	public Monitor buscaPorId(Long id) {
		return manager.find(Monitor.class, id);
	}

	public void remove(Monitor monitor) {
		manager.remove(buscaPorId(monitor.getId()));
	}

}

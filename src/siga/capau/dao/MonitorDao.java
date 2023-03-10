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
		return manager.createQuery("select m from Monitor m order by m.id desc", Monitor.class).getResultList();
	}
	
	public List<Monitor> listaMonitorVinculadoADisciplina() {
		return manager.createQuery("select m from Monitor m inner join Disciplina d on d.monitor.id = m.id", Monitor.class).getResultList();
	}

	public List<Monitor> buscaPorMatricula(String matricula) {
		return manager.createQuery("select m from Monitor m where m.matricula = :matricula", Monitor.class)
				.setParameter("matricula", matricula).getResultList();
	}

	public List<Monitor> buscaPorUsuario(Long id) {
		return manager.createQuery("select m from Monitor m where m.usuario.id = :id", Monitor.class)
				.setParameter("id", id).getResultList();
	}

	public List<Monitor> buscaPorDisciplinaId(Long id) {
		return manager
				.createQuery("select m from Monitor m inner join Disciplina d on d.monitor.id = m.id where d.id = :id",
						Monitor.class)
				.setParameter("id", id).getResultList();
	}

	public Long usuarioMonitor(Long id) {
		return manager.createQuery("select count(m) from Monitor m where m.usuario.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public Monitor buscaPorId(Long id) {
		return manager.find(Monitor.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Monitor m where m.id = :id").setParameter("id", id).executeUpdate();
	}

}

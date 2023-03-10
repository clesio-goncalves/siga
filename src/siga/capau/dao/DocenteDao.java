package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Docente;

@Repository
public class DocenteDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Docente docente) {
		manager.persist(docente);
	}

	public void altera(Docente docente) {
		manager.merge(docente);
	}

	public List<Docente> lista() {
		return manager.createQuery("select d from Docente d order by d.id desc", Docente.class).getResultList();
	}

	public List<Docente> listaDocentesPorDisciplinaId(Long disciplina_id) {
		return manager.createQuery(
				"select DISTINCT d from Docente d inner join TurmaDisciplinaDocente tdd on tdd.docente.id = d.id where tdd.disciplina.id = :disciplina_id",
				Docente.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public List<Docente> buscaPorSiape(int siape) {
		return manager.createQuery("select d from Docente d where d.siape = :siape", Docente.class)
				.setParameter("siape", siape).getResultList();
	}

	public List<Docente> buscaDocenteSemVinculoEmTurmaDisciplinaDocente(Long disciplina_id) {
		return manager.createQuery(
				"select d from Docente d where d.id not in (select tdd.docente.id from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :disciplina_id)",
				Docente.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public List<Docente> buscaPorUsuario(Long id) {
		return manager.createQuery("select d from Docente d where d.usuario.id = :id", Docente.class)
				.setParameter("id", id).getResultList();
	}

	public Long usuarioDocente(Long id) {
		return manager.createQuery("select count(d) from Docente d where d.usuario.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public Docente buscaPorId(Long id) {
		return manager.find(Docente.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Docente d where d.id = :id").setParameter("id", id).executeUpdate();
	}

}

package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Curso;

@Repository
public class CursoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Curso curso) {
		manager.persist(curso);
	}

	public void altera(Curso curso) {
		manager.merge(curso);
	}

	public List<Curso> lista() {
		return manager.createQuery("select c from Curso c", Curso.class).getResultList();
	}

	public List<Curso> listaCursosPorDisciplinasDoDocenteId(Long docente_id) {
		return manager.createQuery(
				"select DISTINCT c from Curso c inner join Turma t on t.curso.id = c.id inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id where tdd.disciplina.id in (select tddi.disciplina.id from TurmaDisciplinaDocente tddi where tddi.docente.id = :docente_id)",
				Curso.class).setParameter("docente_id", docente_id).getResultList();
	}

	public List<Curso> buscaPorNome(String nome) {
		return manager.createQuery("select c from Curso c where c.nome = :nome", Curso.class).setParameter("nome", nome)
				.getResultList();
	}

	public String buscaNomePorId(Long id) {
		return manager.createQuery("select c.nome from Curso c where c.id = :id", String.class).setParameter("id", id)
				.getSingleResult();
	}

	public Curso buscaPorId(Long id) {
		return manager.find(Curso.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Curso c where c.id = :id").setParameter("id", id).executeUpdate();
	}

}

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

	public List<Curso> buscaPorNome(String nome) {
		return manager.createQuery("select c from Curso c where c.nome = :nome", Curso.class).setParameter("nome", nome)
				.getResultList();
	}

	public List<Curso> buscaCursoPorDisciplinaId(Long id) {
		return manager.createQuery(
				"select c from Curso c inner join CursoDisciplina cd on cd.curso.id = c.id inner join Disciplina d on d.id = cd.disciplina.id where d.id = :id",
				Curso.class).setParameter("id", id).getResultList();
	}

	public List<String> buscaCursoPorDisciplinaIdString(Long id) {
		return manager.createQuery(
				"select c.nome from Curso c inner join CursoDisciplina cd on cd.curso.id = c.id inner join Disciplina d on d.id = cd.disciplina.id where d.id = :id",
				String.class).setParameter("id", id).getResultList();
	}

	public Curso buscaCursoPorNome(String nome) {
		return manager.createQuery("select c from Curso c where c.nome = :nome", Curso.class).setParameter("nome", nome)
				.getSingleResult();
	}

	// Pode ser usada no futuro
	public Long buscaQntDisciplinaPorCurso(Long id) {
		return manager.createQuery(
				"select count(c.id) from Curso c inner join CursoDisciplina cd on cd.curso.id = c.id inner join Disciplina d on d.id = cd.disciplina.id where c.id = :id",
				Long.class).setParameter("id", id).getSingleResult();
	}

	public Curso buscaPorId(Long id) {
		return manager.find(Curso.class, id);
	}

	public void remove(Curso curso) {
		manager.remove(buscaPorId(curso.getId()));
	}

}

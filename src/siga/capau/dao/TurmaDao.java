package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Turma;

@Repository
public class TurmaDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Turma turma) {
		manager.persist(turma);
	}

	public void altera(Turma turma) {
		manager.merge(turma);
	}

	public List<Turma> lista() {
		return manager.createQuery("select t from Turma t", Turma.class).getResultList();
	}

	public List<Turma> buscaPorNome(String nome) {
		return manager.createQuery("select t from Turma t where t.nome = :nome", Turma.class).setParameter("nome", nome)
				.getResultList();
	}

	public List<Turma> buscaTurmaPorCurso(Long id) {
		return manager.createQuery("select t from Turma as t where t.curso.id = :id", Turma.class)
				.setParameter("id", id).getResultList();
	}

	public List<Turma> buscaTurmaSemVinculoEmTurmaDisciplinaDocente(Long disciplina_id) {
		return manager.createQuery(
				"select t from Turma t where t.id not in (select tdd.turma.id from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :disciplina_id)",
				Turma.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	// Pode ser usada no futuro
	public Long buscaQntDisciplinaPorTurma(Long id) {
		return manager.createQuery(
				"select count(t.id) from Turma t inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id inner join Disciplina d on d.id = tdd.disciplina.id where t.id = :id",
				Long.class).setParameter("id", id).getSingleResult();
	}

	public Turma buscaPorId(Long id) {
		return manager.find(Turma.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Turma t where t.id = :id").setParameter("id", id).executeUpdate();
	}

}

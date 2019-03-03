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

	public List<Turma> listaTurmas() {
		return manager.createQuery("select t from Turma t", Turma.class).getResultList();
	}

	public List<Turma> listaTurmasAtivas() {
		return manager.createQuery("select t from Turma t where t.ativo=true", Turma.class).getResultList();
	}

	public List<Turma> buscaPorNome(String nome) {
		return manager.createQuery("select t from Turma t where t.nome = :nome", Turma.class).setParameter("nome", nome)
				.getResultList();
	}

	public List<Turma> listaTurmaPorCursoId(Long id) {
		return manager.createQuery("select t from Turma as t where t.ativo=true and t.curso.id = :id", Turma.class)
				.setParameter("id", id).getResultList();
	}

	public List<Turma> listaTurmaPorCursoIdDisciplinasDoDocenteId(Long curso_id, Long docente_id) {
		return manager.createQuery(
				"select t from Turma t inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id where t.ativo=true and t.curso.id = :curso_id and tdd.disciplina.id in (select tddi.disciplina.id from TurmaDisciplinaDocente tddi where tddi.docente.id = :docente_id)",
				Turma.class).setParameter("curso_id", curso_id).setParameter("docente_id", docente_id).getResultList();
	}

	public List<Turma> buscaTurmaSemVinculoEmTurmaDisciplinaDocente(Long disciplina_id) {
		return manager.createQuery(
				"select t from Turma t where t.ativo=true and t.id not in (select tdd.turma.id from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :disciplina_id)",
				Turma.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public Long buscaQntTurmasPorCursoId(Long curso_id) {
		return manager
				.createQuery("select count(t.curso.id) from Turma t where t.ativo=true and t.curso.id = :curso_id",
						Long.class)
				.setParameter("curso_id", curso_id).getSingleResult();
	}

	public Turma buscaPorId(Long id) {
		return manager.find(Turma.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Turma t where t.id = :id").setParameter("id", id).executeUpdate();
	}

}

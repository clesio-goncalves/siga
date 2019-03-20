package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.FiltroTurma;
import siga.capau.modelo.Turma;

@Repository
public class TurmaDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;
	private boolean where;

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
		return manager.createQuery("select t from Turma t where t.ativo=true order by t.id desc", Turma.class)
				.getResultList();
	}

	public List<Turma> buscaPorNome(String nome) {
		return manager.createQuery("select t from Turma t where t.nome = :nome", Turma.class).setParameter("nome", nome)
				.getResultList();
	}

	public List<Turma> listaTurmaPorCursoId(Long id) {
		return manager.createQuery("select t from Turma as t where t.ativo=true and t.curso.id = :id", Turma.class)
				.setParameter("id", id).getResultList();
	}

	public List<Turma> listaTurmaPorCursoIdDocenteId(Long curso_id, Long docente_id) {
		return manager.createQuery(
				"select t from Turma t inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id where t.ativo=true and t.curso.id = :curso_id and tdd.docente.id = :docente_id",
				Turma.class).setParameter("curso_id", curso_id).setParameter("docente_id", docente_id).getResultList();
	}

	public List<Turma> listaTurmaPorCursoIdMonitorId(Long curso_id, Long monitor_id) {
		return manager.createQuery(
				"select t from Turma t inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id where t.ativo=true and t.curso.id = :curso_id and tdd.disciplina.monitor.id = :monitor_id",
				Turma.class).setParameter("curso_id", curso_id).setParameter("monitor_id", monitor_id).getResultList();
	}

	public List<Turma> listaTurmasPorDocenteId(Long docente_id) {
		return manager.createQuery(
				"select t from Turma t inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id where t.ativo=true and tdd.docente.id = :docente_id",
				Turma.class).setParameter("docente_id", docente_id).getResultList();
	}

	public List<Turma> listaTurmasPorMonitorId(Long monitor_id) {
		return manager.createQuery(
				"select t from Turma t inner join TurmaDisciplinaDocente tdd on tdd.turma.id = t.id where t.ativo=true and tdd.disciplina.monitor.id = :monitor_id",
				Turma.class).setParameter("monitor_id", monitor_id).getResultList();
	}

	public List<Turma> buscaTurmaSemVinculoEmTurmaDisciplinaDocente(Long disciplina_id) {
		return manager.createQuery(
				"select t from Turma t where t.ativo=true and t.id not in (select tdd.turma.id from TurmaDisciplinaDocente tdd where tdd.disciplina.id = :disciplina_id)",
				Turma.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public Long buscaQntTurmasPorCursoId(Long curso_id) {
		return manager
				.createQuery("select count(t) from Turma t where t.ativo=true and t.curso.id = :curso_id", Long.class)
				.setParameter("curso_id", curso_id).getSingleResult();
	}

	public Turma buscaPorId(Long id) {
		return manager.find(Turma.class, id);
	}

	public Turma buscaTurmaPorAtendimentoMonitoriaId(Long atendimento_id) {
		return manager.createQuery(
				"select t from Turma t inner join Aluno a on a.turma.id = t.id inner join AlunoAtendimentoMonitoria aat on aat.aluno.id = a.id where aat.atendimento_monitoria.id = :atendimento_id",
				Turma.class).setParameter("atendimento_id", atendimento_id).getSingleResult();
	}

	public void remove(Long id) {
		manager.createQuery("delete from Turma t where t.id = :id").setParameter("id", id).executeUpdate();
	}

	public List<Turma> filtraTurma(FiltroTurma filtro_turma) {

		sql = "select t from Turma t";
		this.where = false;

		// Curso
		if (filtro_turma.getCurso() != null) {
			this.where = true;
			sql = sql + " where t.curso.id = " + filtro_turma.getCurso();
		}

		// Ano
		if (!filtro_turma.getAno_ingresso().equals("")) {
			testeWhere();
			sql = sql + " t.nome like '%" + filtro_turma.getAno_ingresso() + "%'";
		}

		// Periodo
		if (!filtro_turma.getPeriodo_ingresso().equals("")) {
			testeWhere();
			sql = sql + " t.nome like '%." + filtro_turma.getPeriodo_ingresso() + "%'";
		}

		// Tipo
		if (!filtro_turma.getTipo_turma().equals("")) {
			testeWhere();
			sql = sql + " t.nome like '% - " + filtro_turma.getTipo_turma() + "%'";
		}

		// Situação
		if (!filtro_turma.getSituacao().equals("")) {
			testeWhere();
			sql = sql + " t.ativo = " + filtro_turma.getSituacao();
		}

		sql = sql + " order by t.id desc";

		return manager.createQuery(sql, Turma.class).getResultList();

	}

	private void testeWhere() {
		if (this.where == false) {
			this.where = true;
			sql = sql + " where";
		} else {
			sql = sql + " and";
		}
	}

}

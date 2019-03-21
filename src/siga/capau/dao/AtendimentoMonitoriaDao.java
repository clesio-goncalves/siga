package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoMonitoria;
import siga.capau.modelo.FiltroAtendimentoMonitoria;

@Repository
public class AtendimentoMonitoriaDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;
	private boolean where;
	private boolean join;

	public AtendimentoMonitoria adiciona(AtendimentoMonitoria AtendimentoMonitoria) {
		manager.persist(AtendimentoMonitoria);
		return AtendimentoMonitoria;
	}

	public void altera(AtendimentoMonitoria AtendimentoMonitoria) {
		manager.merge(AtendimentoMonitoria);
	}

	public List<AtendimentoMonitoria> lista() {
		return manager
				.createQuery("select am from AtendimentoMonitoria am order by am.data desc", AtendimentoMonitoria.class)
				.getResultList();
	}

	public Long buscaMonitorIdPeloAtendimentoMonitoriaId(Long id) {
		return manager.createQuery("select am.monitor.id from AtendimentoMonitoria am where am.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public List<AtendimentoMonitoria> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery(
				"select am from AtendimentoMonitoria am inner join AlunoAtendimentoMonitoria aam on aam.atendimento_monitoria.id = am.id where aam.aluno.id = :aluno_id",
				AtendimentoMonitoria.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoMonitoria> buscaPeloMonitorId(Long monitor_id) {
		return manager.createQuery(
				"select am from AtendimentoMonitoria am where am.monitor.id = :monitor_id order by am.data desc",
				AtendimentoMonitoria.class).setParameter("monitor_id", monitor_id).getResultList();
	}

	public List<AtendimentoMonitoria> buscaPelaDisciplinaId(Long disciplina_id) {
		return manager.createQuery("select am from AtendimentoMonitoria am where am.disciplina.id = :disciplina_id",
				AtendimentoMonitoria.class).setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public AtendimentoMonitoria buscaPorId(Long id) {
		return manager.find(AtendimentoMonitoria.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoMonitoria am where am.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public void removePeloAlunoId(Long id) {
		manager.createQuery("delete from AtendimentoMonitoria a where a.aluno.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public List<AtendimentoMonitoria> filtraAtendimentoMonitoria(
			FiltroAtendimentoMonitoria filtro_atendimento_monitoria) {

		sql = "select am from AtendimentoMonitoria as am";
		this.where = false;
		this.join = false;

		// Curso
		if (filtro_atendimento_monitoria.getCurso() != null) {
			sql = sql
					+ " inner join AlunoAtendimentoMonitoria aam on aam.atendimento_monitoria.id = am.id where aam.aluno.turma.curso.id = "
					+ filtro_atendimento_monitoria.getCurso();
			this.join = true;
			this.where = true;
		}

		// Turma
		if (filtro_atendimento_monitoria.getTurma() != null) {
			testeWhere();
			sql = sql + " aam.aluno.turma.id = " + filtro_atendimento_monitoria.getTurma();
		}

		// Aluno
		if (filtro_atendimento_monitoria.getAluno() != null) {
			testeJoin();
			testeWhere();
			sql = sql + " aam.aluno.id = " + filtro_atendimento_monitoria.getAluno();
			this.join = true;
			this.where = true;
		}

		// Data
		testeWhere();
		sql = sql + " DATE(am.data) between '" + filtro_atendimento_monitoria.getData_inicial_atendimento() + "' and '"
				+ filtro_atendimento_monitoria.getData_final_atendimento() + "'";

		// Horario inicial atendimento
		if (!filtro_atendimento_monitoria.getHorario_inicial_atendimento().equals("")) {
			sql = sql + " and TIME(am.horario_inicial) >= '"
					+ filtro_atendimento_monitoria.getHorario_inicial_atendimento() + ":00'";
		}

		// Horario final atendimento
		if (!filtro_atendimento_monitoria.getHorario_final_atendimento().equals("")) {
			sql = sql + " and TIME(am.horario_final) <= '" + filtro_atendimento_monitoria.getHorario_final_atendimento()
					+ ":00'";
		}

		// disciplina
		if (filtro_atendimento_monitoria.getDisciplina() != null) {
			sql = sql + " and am.disciplina.id = " + filtro_atendimento_monitoria.getDisciplina();
		}

		// monitor
		if (filtro_atendimento_monitoria.getMonitor() != null) {
			sql = sql + " and am.monitor.id = " + filtro_atendimento_monitoria.getMonitor();
		}

		// local
		if (!filtro_atendimento_monitoria.getLocal().equals("")) {
			sql = sql + " and am.local like '%" + filtro_atendimento_monitoria.getLocal() + "%'";
		}

		// conteudo
		if (!filtro_atendimento_monitoria.getConteudo().equals("")) {
			sql = sql + " and am.conteudo like '%" + filtro_atendimento_monitoria.getConteudo() + "%'";
		}

		// dificuldades_diagnosticadas
		if (!filtro_atendimento_monitoria.getDificuldades_diagnosticadas().equals("")) {
			sql = sql + " and am.dificuldades_diagnosticadas like '%"
					+ filtro_atendimento_monitoria.getDificuldades_diagnosticadas() + "%'";
		}

		// Status atendimento
		if (!filtro_atendimento_monitoria.getStatus_atendimento().equals("")) {
			if (filtro_atendimento_monitoria.getStatus_atendimento().equals("sim")) {
				sql = sql + " and am.status_atendimento = false";
			} else {
				sql = sql + " and am.status_atendimento = true";
			}
		}

		// Group by
		sql = sql + " group by am.id";

		// Oder by
		sql = sql + " order by am.data desc";

		return manager.createQuery(sql, AtendimentoMonitoria.class).getResultList();

	}

	private void testeJoin() {
		if (this.join == false) {
			this.join = true;
			sql = sql + " inner join AlunoAtendimentoMonitoria aam on aam.atendimento_monitoria.id = e.id";
		}
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

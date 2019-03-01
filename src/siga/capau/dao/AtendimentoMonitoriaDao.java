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

	public void adiciona(AtendimentoMonitoria AtendimentoMonitoria) {
		manager.persist(AtendimentoMonitoria);
	}

	public void altera(AtendimentoMonitoria AtendimentoMonitoria) {
		manager.merge(AtendimentoMonitoria);
	}

	public List<AtendimentoMonitoria> lista() {
		return manager.createQuery("select am from AtendimentoMonitoria am", AtendimentoMonitoria.class)
				.getResultList();
	}

	public List<AtendimentoMonitoria> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select am from AtendimentoMonitoria am where am.aluno.id = :aluno_id",
				AtendimentoMonitoria.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoMonitoria> buscaPeloMonitorId(Long monitor_id) {
		return manager
				.createQuery("select am from AtendimentoMonitoria am where am.disciplina.monitor.id = :monitor_id",
						AtendimentoMonitoria.class)
				.setParameter("monitor_id", monitor_id).getResultList();
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

	public List<AtendimentoMonitoria> filtraAtendimentoMonitoria(
			FiltroAtendimentoMonitoria filtro_atendimento_monitoria) {

		sql = "select am from AtendimentoMonitoria as am";

		sql = sql + " where DATE(am.data) between '" + filtro_atendimento_monitoria.getData_inicial_atendimento()
				+ "' and '" + filtro_atendimento_monitoria.getData_final_atendimento() + "'";

		// Horario inicial atendimento
		if (!filtro_atendimento_monitoria.getHorario_inicial_atendimento().equals("")) {
			sql = sql + " and TIME(am.horario_inicial) >= '" + filtro_atendimento_monitoria.getHorario_inicial_atendimento()
					+ ":00'";
		}

		// Horario final atendimento
		if (!filtro_atendimento_monitoria.getHorario_final_atendimento().equals("")) {
			sql = sql + " and TIME(am.horario_final) <= '" + filtro_atendimento_monitoria.getHorario_final_atendimento()
					+ ":00'";
		}

		// Curso
		if (filtro_atendimento_monitoria.getCurso() != null) {
			sql = sql + " and am.aluno.turma.curso.id = " + filtro_atendimento_monitoria.getCurso();
		}

		// Turma
		if (filtro_atendimento_monitoria.getTurma() != null) {
			sql = sql + " and am.aluno.turma.id = " + filtro_atendimento_monitoria.getTurma();
		}

		// disciplina
		if (filtro_atendimento_monitoria.getDisciplina() != null) {
			sql = sql + " and am.disciplina.id = " + filtro_atendimento_monitoria.getDisciplina();
		}

		// Aluno
		if (filtro_atendimento_monitoria.getAluno() != null) {
			sql = sql + " and am.aluno.id = " + filtro_atendimento_monitoria.getAluno();
		}

		// monitor
		if (filtro_atendimento_monitoria.getMonitor() != null) {
			sql = sql + " and am.disciplina.monitor.id = " + filtro_atendimento_monitoria.getMonitor();
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

		return manager.createQuery(sql, AtendimentoMonitoria.class).getResultList();

	}

}

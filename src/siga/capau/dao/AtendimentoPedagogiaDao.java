package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoPedagogia;
import siga.capau.modelo.FiltroAtendimentoPedagogia;

@Repository
public class AtendimentoPedagogiaDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;

	public void adiciona(AtendimentoPedagogia atendimento_pedagogia) {
		manager.persist(atendimento_pedagogia);
	}

	public void altera(AtendimentoPedagogia atendimento_pedagogia) {
		manager.merge(atendimento_pedagogia);
	}

	public List<AtendimentoPedagogia> lista() {
		return manager.createQuery("select a from AtendimentoPedagogia a order by a.data desc", AtendimentoPedagogia.class).getResultList();
	}

	public List<AtendimentoPedagogia> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select a from AtendimentoPedagogia a where a.aluno.id = :aluno_id",
				AtendimentoPedagogia.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoPedagogia> buscaPeloProfissionalId(Long profissional_id) {
		return manager.createQuery("select a from AtendimentoPedagogia a where a.profissional.id = :profissional_id",
				AtendimentoPedagogia.class).setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoPedagogia buscaPorId(Long id) {
		return manager.find(AtendimentoPedagogia.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoPedagogia a where a.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public List<AtendimentoPedagogia> filtraAtendimentoPedagogia(
			FiltroAtendimentoPedagogia filtro_atendimento_pedagogia) {

		sql = "select a from AtendimentoPedagogia as a";

		sql = sql + " where DATE(a.data) between '" + filtro_atendimento_pedagogia.getData_inicial_atendimento()
				+ "' and '" + filtro_atendimento_pedagogia.getData_final_atendimento() + "'";

		// Horario inicial atendimento
		if (!filtro_atendimento_pedagogia.getHorario_inicial_atendimento().equals("")) {
			sql = sql + " and TIME(a.horario_inicial) >= '"
					+ filtro_atendimento_pedagogia.getHorario_inicial_atendimento() + ":00'";
		}

		// Horario final atendimento
		if (!filtro_atendimento_pedagogia.getHorario_final_atendimento().equals("")) {
			sql = sql + " and TIME(a.horario_final) <= '" + filtro_atendimento_pedagogia.getHorario_final_atendimento()
					+ ":00'";
		}

		// Curso
		if (filtro_atendimento_pedagogia.getCurso() != null) {
			sql = sql + " and a.aluno.turma.curso.id = " + filtro_atendimento_pedagogia.getCurso();
		}

		// Turma
		if (filtro_atendimento_pedagogia.getTurma() != null) {
			sql = sql + " and a.aluno.turma.id = " + filtro_atendimento_pedagogia.getTurma();
		}

		// Assunto
		if (!filtro_atendimento_pedagogia.getAssunto().equals("")) {
			if (filtro_atendimento_pedagogia.getAssunto().equals("outros")) {
				sql = sql + " and a.outros is not null";
			} else {
				sql = sql + " and a." + filtro_atendimento_pedagogia.getAssunto() + " = true";
			}
		}

		// Aluno
		if (filtro_atendimento_pedagogia.getAluno() != null) {
			sql = sql + " and a.aluno.id = " + filtro_atendimento_pedagogia.getAluno();
		}

		// Profissional
		if (filtro_atendimento_pedagogia.getProfissional() != null) {
			sql = sql + " and a.profissional.id = " + filtro_atendimento_pedagogia.getProfissional();
		}

		// Exposição motivos
		if (!filtro_atendimento_pedagogia.getExposicao_motivos().equals("")) {
			sql = sql + " and a.exposicao_motivos like '%" + filtro_atendimento_pedagogia.getExposicao_motivos() + "%'";
		}

		// Encaminhamento
		if (!filtro_atendimento_pedagogia.getEncaminhamento().equals("")) {
			sql = sql + " and a.encaminhamento like '%" + filtro_atendimento_pedagogia.getEncaminhamento() + "%'";
		}
		
		// Order by
		sql = sql + " order by a.data desc";

		return manager.createQuery(sql, AtendimentoPedagogia.class).getResultList();

	}

}

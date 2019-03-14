package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoPedagogiaFamilia;
import siga.capau.modelo.FiltroAtendimentoPedagogiaFamilia;

@Repository
public class AtendimentoPedagogiaFamiliaDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;

	public void adiciona(AtendimentoPedagogiaFamilia atendimento_pedagogia) {
		manager.persist(atendimento_pedagogia);
	}

	public void altera(AtendimentoPedagogiaFamilia atendimento_pedagogia) {
		manager.merge(atendimento_pedagogia);
	}

	public List<AtendimentoPedagogiaFamilia> lista() {
		return manager.createQuery("select a from AtendimentoPedagogiaFamilia a order by a.data desc",
				AtendimentoPedagogiaFamilia.class).getResultList();
	}

	public List<AtendimentoPedagogiaFamilia> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select a from AtendimentoPedagogiaFamilia a where a.aluno.id = :aluno_id",
				AtendimentoPedagogiaFamilia.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoPedagogiaFamilia> buscaPeloProfissionalId(Long profissional_id) {
		return manager
				.createQuery("select a from AtendimentoPedagogiaFamilia a where a.profissional.id = :profissional_id",
						AtendimentoPedagogiaFamilia.class)
				.setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoPedagogiaFamilia buscaPorId(Long id) {
		return manager.find(AtendimentoPedagogiaFamilia.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoPedagogiaFamilia a where a.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public List<AtendimentoPedagogiaFamilia> filtraAtendimentoPedagogiaFamilia(
			FiltroAtendimentoPedagogiaFamilia filtro_atendimento_pedagogia) {

		sql = "select a from AtendimentoPedagogiaFamilia as a";

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

		// Responsavel
		if (!filtro_atendimento_pedagogia.getResponsavel().equals("")) {
			sql = sql + " and a.responsavel like '%" + filtro_atendimento_pedagogia.getResponsavel() + "%'";
		}

		// Aluno
		if (filtro_atendimento_pedagogia.getAluno() != null) {
			sql = sql + " and a.aluno.id = " + filtro_atendimento_pedagogia.getAluno();
		}

		// Profissional
		if (filtro_atendimento_pedagogia.getProfissional() != null) {
			sql = sql + " and a.profissional.id = " + filtro_atendimento_pedagogia.getProfissional();
		}

		// Relato do atendimento
		if (!filtro_atendimento_pedagogia.getRelato().equals("")) {
			sql = sql + " and a.relato like '%" + filtro_atendimento_pedagogia.getRelato() + "%'";
		}

		// Encaminhamento
		if (!filtro_atendimento_pedagogia.getEncaminhamento().equals("")) {
			sql = sql + " and a.encaminhamento like '%" + filtro_atendimento_pedagogia.getEncaminhamento() + "%'";
		}

		// Order by
		sql = sql + " order by a.data desc";

		return manager.createQuery(sql, AtendimentoPedagogiaFamilia.class).getResultList();

	}

}

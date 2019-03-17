package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoPedagogiaAluno;
import siga.capau.modelo.FiltroAtendimentoPedagogiaAluno;

@Repository
public class AtendimentoPedagogiaAlunoDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;

	public void adiciona(AtendimentoPedagogiaAluno atendimento_pedagogia) {
		manager.persist(atendimento_pedagogia);
	}

	public void altera(AtendimentoPedagogiaAluno atendimento_pedagogia) {
		manager.merge(atendimento_pedagogia);
	}

	public List<AtendimentoPedagogiaAluno> lista() {
		return manager.createQuery("select a from AtendimentoPedagogiaAluno a order by a.data desc", AtendimentoPedagogiaAluno.class).getResultList();
	}

	public List<AtendimentoPedagogiaAluno> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select a from AtendimentoPedagogiaAluno a where a.aluno.id = :aluno_id",
				AtendimentoPedagogiaAluno.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoPedagogiaAluno> buscaPeloProfissionalId(Long profissional_id) {
		return manager.createQuery("select a from AtendimentoPedagogiaAluno a where a.profissional.id = :profissional_id",
				AtendimentoPedagogiaAluno.class).setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoPedagogiaAluno buscaPorId(Long id) {
		return manager.find(AtendimentoPedagogiaAluno.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoPedagogiaAluno a where a.id = :id").setParameter("id", id)
				.executeUpdate();
	}
	
	public void removePeloAlunoId(Long id) {
		manager.createQuery("delete from AtendimentoPedagogiaAluno a where a.aluno.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public List<AtendimentoPedagogiaAluno> filtraAtendimentoPedagogiaAluno(
			FiltroAtendimentoPedagogiaAluno filtro_atendimento_pedagogia) {

		sql = "select a from AtendimentoPedagogiaAluno as a";

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

		return manager.createQuery(sql, AtendimentoPedagogiaAluno.class).getResultList();

	}

}

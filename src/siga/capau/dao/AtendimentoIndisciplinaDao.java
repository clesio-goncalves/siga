package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoIndisciplina;
import siga.capau.modelo.FiltroAtendimentoIndisciplina;

@Repository
public class AtendimentoIndisciplinaDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;

	public void adiciona(AtendimentoIndisciplina atendimento_indisciplina) {
		manager.persist(atendimento_indisciplina);
	}

	public void altera(AtendimentoIndisciplina atendimento_indisciplina) {
		manager.merge(atendimento_indisciplina);
	}

	public List<AtendimentoIndisciplina> lista() {
		return manager.createQuery("select ai from AtendimentoIndisciplina ai", AtendimentoIndisciplina.class)
				.getResultList();
	}

	public List<AtendimentoIndisciplina> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select ai from AtendimentoIndisciplina ai where ai.aluno.id = :aluno_id",
				AtendimentoIndisciplina.class).setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoIndisciplina> buscaPeloProfissionalId(Long profissional_id) {
		return manager
				.createQuery("select ai from AtendimentoIndisciplina ai where ai.profissional.id = :profissional_id",
						AtendimentoIndisciplina.class)
				.setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoIndisciplina buscaPorId(Long id) {
		return manager.find(AtendimentoIndisciplina.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoIndisciplina ai where ai.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public List<AtendimentoIndisciplina> filtraAtendimentoIndisciplina(
			FiltroAtendimentoIndisciplina filtro_atendimento_indisciplina) {

		sql = "select a from AtendimentoIndisciplina as a";

		sql = sql + " where DATE(a.data) between '" + filtro_atendimento_indisciplina.getData_inicial_atendimento()
				+ "' and '" + filtro_atendimento_indisciplina.getData_final_atendimento() + "'";

		// Horario inicial atendimento
		if (!filtro_atendimento_indisciplina.getHorario_inicial_atendimento().equals("")) {
			sql = sql + " and TIME(a.horario) >= '" + filtro_atendimento_indisciplina.getHorario_inicial_atendimento()
					+ ":00'";
		}

		// Horario final atendimento
		if (!filtro_atendimento_indisciplina.getHorario_final_atendimento().equals("")) {
			sql = sql + " and TIME(a.horario) <= '" + filtro_atendimento_indisciplina.getHorario_final_atendimento()
					+ ":00'";
		}

		// Curso
		if (filtro_atendimento_indisciplina.getCurso() != null) {
			sql = sql + " and a.aluno.turma.curso.id = " + filtro_atendimento_indisciplina.getCurso();
		}

		// Turma
		if (filtro_atendimento_indisciplina.getTurma() != null) {
			sql = sql + " and a.aluno.turma.id = " + filtro_atendimento_indisciplina.getTurma();
		}

		// Advertido
		if (!filtro_atendimento_indisciplina.getAdvertido().equals("")) {
			sql = sql + " and a.advertido like '" + filtro_atendimento_indisciplina.getAdvertido() + "'";
		}

		// Tipo Advertencia
		if (!filtro_atendimento_indisciplina.getTipo_advertencia().equals("")) {
			sql = sql + " and a.tipo_advertencia like '" + filtro_atendimento_indisciplina.getTipo_advertencia() + "'";
		}

		// Aluno
		if (filtro_atendimento_indisciplina.getAluno() != null) {
			sql = sql + " and a.aluno.id = " + filtro_atendimento_indisciplina.getAluno();
		}

		// Profissional
		if (filtro_atendimento_indisciplina.getProfissional() != null) {
			sql = sql + " and a.profissional.id = " + filtro_atendimento_indisciplina.getProfissional();
		}

		// Descrição
		if (!filtro_atendimento_indisciplina.getDescricao().equals("")) {
			sql = sql + " and a.descricao like '%" + filtro_atendimento_indisciplina.getDescricao() + "%'";
		}

		System.out.println("---------------------------------------------------------------------");
		System.out.println(sql);
		System.out.println("---------------------------------------------------------------------");

		return manager.createQuery(sql, AtendimentoIndisciplina.class).getResultList();

	}

}

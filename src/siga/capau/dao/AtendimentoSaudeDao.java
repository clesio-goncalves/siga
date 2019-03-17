package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AtendimentoSaude;
import siga.capau.modelo.FiltroAtendimentoSaude;

@Repository
public class AtendimentoSaudeDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;

	public void adiciona(AtendimentoSaude atendimento_saude) {
		manager.persist(atendimento_saude);
	}

	public void altera(AtendimentoSaude atendimento_saude) {
		manager.merge(atendimento_saude);
	}

	public List<AtendimentoSaude> lista() {
		return manager.createQuery("select a from AtendimentoSaude a order by a.data desc", AtendimentoSaude.class)
				.getResultList();
	}

	public List<AtendimentoSaude> buscaPeloAlunoId(Long aluno_id) {
		return manager
				.createQuery("select a from AtendimentoSaude a where a.aluno.id = :aluno_id", AtendimentoSaude.class)
				.setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AtendimentoSaude> buscaPeloProfissionalId(Long profissional_id) {
		return manager.createQuery("select a from AtendimentoSaude a where a.profissional.id = :profissional_id",
				AtendimentoSaude.class).setParameter("profissional_id", profissional_id).getResultList();
	}

	public AtendimentoSaude buscaPorId(Long id) {
		return manager.find(AtendimentoSaude.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from AtendimentoSaude a where a.id = :id").setParameter("id", id).executeUpdate();
	}

	public void removePeloAlunoId(Long id) {
		manager.createQuery("delete from AtendimentoSaude a where a.aluno.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public List<AtendimentoSaude> filtraAtendimentoSaude(FiltroAtendimentoSaude filtro_atendimento_saude) {

		sql = "select a from AtendimentoSaude as a";

		sql = sql + " where DATE(a.data) between '" + filtro_atendimento_saude.getData_inicial_atendimento() + "' and '"
				+ filtro_atendimento_saude.getData_final_atendimento() + "'";

		// Horario inicial atendimento
		if (!filtro_atendimento_saude.getHorario_inicial_atendimento().equals("")) {
			sql = sql + " and TIME(a.horario_inicial) >= '" + filtro_atendimento_saude.getHorario_inicial_atendimento()
					+ ":00'";
		}

		// Horario final atendimento
		if (!filtro_atendimento_saude.getHorario_final_atendimento().equals("")) {
			sql = sql + " and TIME(a.horario_final) <= '" + filtro_atendimento_saude.getHorario_final_atendimento()
					+ ":00'";
		}

		// Curso
		if (filtro_atendimento_saude.getCurso() != null) {
			sql = sql + " and a.aluno.turma.curso.id = " + filtro_atendimento_saude.getCurso();
		}

		// Turma
		if (filtro_atendimento_saude.getTurma() != null) {
			sql = sql + " and a.aluno.turma.id = " + filtro_atendimento_saude.getTurma();
		}

		// Tipo de Atendimento
		if (!filtro_atendimento_saude.getTipo_atendimento().equals("")) {
			sql = sql + " and a.profissional.tipo_atendimento like '" + filtro_atendimento_saude.getTipo_atendimento()
					+ "'";
		}

		// Aluno
		if (filtro_atendimento_saude.getAluno() != null) {
			sql = sql + " and a.aluno.id = " + filtro_atendimento_saude.getAluno();
		}

		// Profissional
		if (filtro_atendimento_saude.getProfissional() != null) {
			sql = sql + " and a.profissional.id = " + filtro_atendimento_saude.getProfissional();
		}

		// Possui Problema
		if (!filtro_atendimento_saude.getPossui_problema().equals("")) {
			sql = sql + " and a.possui_problema like '" + filtro_atendimento_saude.getPossui_problema() + "'";
		}

		// esse_problema_dificulta_aprendizado
		if (!filtro_atendimento_saude.getEsse_problema_dificulta_aprendizado().equals("")) {
			sql = sql + " and a.esse_problema_dificulta_aprendizado like '"
					+ filtro_atendimento_saude.getEsse_problema_dificulta_aprendizado() + "'";
		}

		// Order by
		sql = sql + " order by a.data desc";

		return manager.createQuery(sql, AtendimentoSaude.class).getResultList();

	}

}

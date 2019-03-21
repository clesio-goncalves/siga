package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.ExtraClasse;
import siga.capau.modelo.FiltroExtraClasse;

@Repository
public class ExtraClasseDao {

	@PersistenceContext
	private EntityManager manager;

	private String sql;

	public ExtraClasse adiciona(ExtraClasse extraClasse) {
		manager.persist(extraClasse);
		return extraClasse;
	}

	public void altera(ExtraClasse extraClasse) {
		manager.merge(extraClasse);
	}

	public List<ExtraClasse> lista() {
		return manager.createQuery("select e from ExtraClasse e order by e.data desc", ExtraClasse.class)
				.getResultList();
	}

	public Long buscaDocenteIdPeloExtraClasseId(Long id) {
		return manager.createQuery("select e.docente.id from ExtraClasse e where e.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public List<ExtraClasse> buscaPeloAlunoId(Long aluno_id) {
		return manager.createQuery("select e from ExtraClasse e where e.aluno.id = :aluno_id", ExtraClasse.class)
				.setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<ExtraClasse> buscaPeloDocenteId(Long docente_id) {
		return manager.createQuery("select e from ExtraClasse e where e.docente.id = :docente_id order by e.data desc",
				ExtraClasse.class).setParameter("docente_id", docente_id).getResultList();
	}

	public List<ExtraClasse> buscaPelaDisciplinaId(Long disciplina_id) {
		return manager
				.createQuery("select e from ExtraClasse e where e.disciplina.id = :disciplina_id", ExtraClasse.class)
				.setParameter("disciplina_id", disciplina_id).getResultList();
	}

	public ExtraClasse buscaPorId(Long id) {
		return manager.find(ExtraClasse.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from ExtraClasse a where a.id = :id").setParameter("id", id).executeUpdate();
	}

	public void removePeloAlunoId(Long id) {
		manager.createQuery("delete from ExtraClasse a where a.aluno.id = :id").setParameter("id", id).executeUpdate();
	}

	public List<ExtraClasse> filtraExtraClasse(FiltroExtraClasse filtro_extra_classe) {

		sql = "select e from ExtraClasse as e";

		sql = sql + " where DATE(e.data) between '" + filtro_extra_classe.getData_inicial_atendimento() + "' and '"
				+ filtro_extra_classe.getData_final_atendimento() + "'";

		// Horario inicial atendimento
		if (!filtro_extra_classe.getHorario_inicial_atendimento().equals("")) {
			sql = sql + " and TIME(e.horario_inicial) >= '" + filtro_extra_classe.getHorario_inicial_atendimento()
					+ ":00'";
		}

		// Horario final atendimento
		if (!filtro_extra_classe.getHorario_final_atendimento().equals("")) {
			sql = sql + " and TIME(e.horario_final) <= '" + filtro_extra_classe.getHorario_final_atendimento() + ":00'";
		}

		// Curso
		if (filtro_extra_classe.getCurso() != null) {
			sql = sql + " and e.aluno.turma.curso.id = " + filtro_extra_classe.getCurso();
		}

		// Turma
		if (filtro_extra_classe.getTurma() != null) {
			sql = sql + " and e.aluno.turma.id = " + filtro_extra_classe.getTurma();
		}

		// disciplina
		if (filtro_extra_classe.getDisciplina() != null) {
			sql = sql + " and e.disciplina.id = " + filtro_extra_classe.getDisciplina();
		}

		// Aluno
		if (filtro_extra_classe.getAluno() != null) {
			sql = sql + " and e.aluno.id = " + filtro_extra_classe.getAluno();
		}

		// docente
		if (filtro_extra_classe.getDocente() != null) {
			sql = sql + " and e.docente.id = " + filtro_extra_classe.getDocente();
		}

		// local
		if (!filtro_extra_classe.getLocal().equals("")) {
			sql = sql + " and e.local like '%" + filtro_extra_classe.getLocal() + "%'";
		}

		// conteudo
		if (!filtro_extra_classe.getConteudo().equals("")) {
			sql = sql + " and e.conteudo like '%" + filtro_extra_classe.getConteudo() + "%'";
		}

		// dificuldades_diagnosticadas
		if (!filtro_extra_classe.getDificuldades_diagnosticadas().equals("")) {
			sql = sql + " and e.dificuldades_diagnosticadas like '%"
					+ filtro_extra_classe.getDificuldades_diagnosticadas() + "%'";
		}

		// Status atendimento
		if (!filtro_extra_classe.getStatus_atendimento().equals("")) {
			if (filtro_extra_classe.getStatus_atendimento().equals("sim")) {
				sql = sql + " and e.status_atendimento = false";
			} else {
				sql = sql + " and e.status_atendimento = true";
			}
		}

		// Oder by
		sql = sql + " order by e.data desc";

		return manager.createQuery(sql, ExtraClasse.class).getResultList();

	}

}

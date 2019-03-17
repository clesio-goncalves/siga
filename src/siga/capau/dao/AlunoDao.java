package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Aluno;
import siga.capau.modelo.FiltroAluno;

@Repository
public class AlunoDao {

	@PersistenceContext
	private EntityManager manager;
	private String sql;
	private boolean where;

	public Aluno adiciona(Aluno aluno) {
		manager.persist(aluno);
		return aluno;
	}

	public void altera(Aluno aluno) {
		manager.merge(aluno);
	}

	public List<Aluno> lista() {
		return manager.createQuery("select a from Aluno a where a.turma.ativo = true order by a.id desc", Aluno.class)
				.getResultList();
	}

	public Long buscaTurmaIdPorAlunoId(Long aluno_id) {
		return manager.createQuery("select a.turma.id from Aluno a where a.id = :aluno_id", Long.class)
				.setParameter("aluno_id", aluno_id).getSingleResult();
	}

	public List<Aluno> listaAlunosPorTurmaId(Long turma_id) {
		return manager.createQuery("select a from Aluno a where a.turma.id = :turma_id", Aluno.class)
				.setParameter("turma_id", turma_id).getResultList();
	}

	public List<Aluno> listaAlunosPorSituacaoId(Long situacao_id) {
		return manager.createQuery("select a from Aluno a where a.turma.ativo = true and a.situacao.id = :situacao_id",
				Aluno.class).setParameter("situacao_id", situacao_id).getResultList();
	}

	public List<Aluno> alunoPossuiUsuario(Long id) {
		return manager.createQuery("select a from Aluno a where a.id = :id and a.usuario.id is not null", Aluno.class)
				.setParameter("id", id).getResultList();
	}

	public Long buscaQntAlunosPorTurmaId(Long turma_id) {
		return manager.createQuery("select count(a) from Aluno a where a.turma.id = :turma_id", Long.class)
				.setParameter("turma_id", turma_id).getSingleResult();
	}

	public Long buscaQntAlunosPorSituacaoId(Long situacao_id) {
		return manager
				.createQuery("select count(a) from Aluno a where a.turma.ativo = true and a.situacao.id = :situacao_id",
						Long.class)
				.setParameter("situacao_id", situacao_id).getSingleResult();
	}

	public Long usuarioAluno(Long id) {
		return manager.createQuery("select count(a) from Aluno a where a.usuario.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public Long buscaSituacaoAtualPorAluno(Long id) {
		return manager.createQuery("select a.situacao.id from Aluno a where a.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public Aluno buscaPorId(Long id) {
		return manager.find(Aluno.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Aluno a where a.id = :id").setParameter("id", id).executeUpdate();
	}

	public List<Aluno> buscaAlunosPorTurma(Long id) {
		return manager.createQuery("select a from Aluno as a where a.turma.id = :id", Aluno.class)
				.setParameter("id", id).getResultList();
	}

	public List<Aluno> buscaPorUsuario(Long id) {
		return manager.createQuery("select a from Aluno a where a.usuario.id = :id", Aluno.class).setParameter("id", id)
				.getResultList();
	}

	public List<Aluno> filtraAluno(FiltroAluno filtro_aluno) {

		sql = "select DISTINCT a from Aluno a";
		this.where = false;

		// Atendimentos
		if (!filtro_aluno.getAtendimentos().equals("")) {
			switch (filtro_aluno.getAtendimentos()) {
			case "Extraclasse":
				sql = sql + " inner join ExtraClasse e on e.aluno.id = a.id";
				break;
			case "Monitoria":
				sql = sql + " inner join AtendimentoMonitoria am on am.aluno.id = a.id";
				break;
			case "Saude":
				sql = sql + " inner join AtendimentoSaude saude on saude.aluno.id = a.id";
				break;
			case "Indisciplina":
				sql = sql + " inner join AtendimentoIndisciplina ai on ai.aluno.id = a.id";
				break;
			case "Pedagogia":
				sql = sql + " inner join AtendimentoPedagogia ap on ap.aluno.id = a.id";
				break;
			case "Todos":
				sql = sql + " inner join ExtraClasse e on e.aluno.id = a.id"
						+ " inner join AtendimentoMonitoria am on am.aluno.id = a.id"
						+ " inner join AtendimentoSaude saude on saude.aluno.id = a.id"
						+ " inner join AtendimentoIndisciplina ai on ai.aluno.id = a.id"
						+ " inner join AtendimentoPedagogia ap on ap.aluno.id = a.id";
				break;
			default:
				break;
			}
		}

		// Curso
		if (filtro_aluno.getCurso() != null) {
			this.where = true;
			sql = sql + " where a.turma.curso.id = " + filtro_aluno.getCurso();
		}

		// Turma
		if (filtro_aluno.getTurma() != null) {
			sql = sql + " and a.turma.id = " + filtro_aluno.getTurma();
		}

		// Situação
		if (!filtro_aluno.getSituacao().equals("")) {
			testeWhere();
			sql = sql + " a.turma.ativo = " + filtro_aluno.getSituacao();
		}

		// Nome
		if (!filtro_aluno.getNome().equals("")) {
			testeWhere();
			sql = sql + " a.nome like '%" + filtro_aluno.getNome() + "%'";
		}

		// Matricula
		if (!filtro_aluno.getMatricula().equals("")) {
			testeWhere();
			sql = sql + " a.matricula like '%" + filtro_aluno.getMatricula() + "%'";
		}

		// Telefone
		if (!filtro_aluno.getTelefone().equals("")) {
			testeWhere();
			sql = sql + " a.telefone like '%" + filtro_aluno.getTelefone() + "%'";
		}

		// Usuario
		if (!filtro_aluno.getUsuario().equals("")) {
			testeWhere();
			sql = sql + " a.usuario.email like '%" + filtro_aluno.getUsuario() + "%'";
		}

		sql = sql + "order by a.id desc";

		return manager.createQuery(sql, Aluno.class).getResultList();

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

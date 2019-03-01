package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Aluno;

@Repository
public class AlunoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Aluno aluno) {
		manager.persist(aluno);
	}

	public void altera(Aluno aluno) {
		manager.merge(aluno);
	}

	public List<Aluno> lista() {
		return manager.createQuery("select a from Aluno a", Aluno.class).getResultList();
	}

	public Long buscaTurmaIdPorAlunoId(Long aluno_id) {
		return manager.createQuery("select a.turma.id from Aluno a where a.id = :aluno_id", Long.class)
				.setParameter("aluno_id", aluno_id).getSingleResult();
	}

	public List<Aluno> listaAlunosPorTurmaId(Long turma_id) {
		return manager.createQuery("select a from Aluno a where a.turma.id = :turma_id", Aluno.class)
				.setParameter("turma_id", turma_id).getResultList();
	}

	public List<Aluno> alunoPossuiUsuario(Long id) {
		return manager.createQuery("select a from Aluno a where a.id = :id and a.usuario.id is not null", Aluno.class)
				.setParameter("id", id).getResultList();
	}

	public Long buscaQntAlunosPorTurmaId(Long turma_id) {
		return manager.createQuery("select count(a.turma.id) from Aluno a where a.turma.id = :turma_id", Long.class)
				.setParameter("turma_id", turma_id).getSingleResult();
	}

	public Long usuarioAluno(Long id) {
		return manager.createQuery("select count(a) from Aluno a where a.usuario.id = :id", Long.class)
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

}

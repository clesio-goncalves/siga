package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Aluno;
import siga.capau.modelo.Usuario;

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

	public Aluno buscaPorId(Long id) {
		return manager.find(Aluno.class, id);
	}

	public void remove(Aluno aluno) {
		Aluno alunoARemover = buscaPorId(aluno.getId());
		manager.remove(alunoARemover);
	}

	public List<Aluno> buscaAlunosPorCurso(Long id) {
		return manager.createQuery("select a from Aluno as a where a.curso.id = :idCurso", Aluno.class)
				.setParameter("idCurso", id).getResultList();
	}

}

package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.AlunoExtraClasse;

@Repository
public class AlunoExtraClasseDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Long aluno_id, Long extra_classe_id) {
		manager.createNativeQuery(
				"insert into AlunoExtraClasse (aluno_id, extra_classe_id) values (:aluno_id, :extra_classe_id)")
				.setParameter("aluno_id", aluno_id).setParameter("extra_classe_id", extra_classe_id).executeUpdate();
	}

	public List<AlunoExtraClasse> buscaAlunoId(Long aluno_id) {
		return manager
				.createQuery("select a from AlunoExtraClasse a where a.aluno.id = :aluno_id", AlunoExtraClasse.class)
				.setParameter("aluno_id", aluno_id).getResultList();
	}

	public List<AlunoExtraClasse> buscaPorAtendimentoId(Long extra_classe_id) {
		return manager.createQuery("select a from AlunoExtraClasse a where a.extra_classe.id = :extra_classe_id",
				AlunoExtraClasse.class).setParameter("extra_classe_id", extra_classe_id).getResultList();
	}

	public void removePeloAlunoId(Long id) {
		manager.createQuery("delete from AlunoExtraClasse a where a.aluno.id = :id").setParameter("id", id)
				.executeUpdate();
	}

	public void removePeloAtendimentoId(Long id) {
		manager.createQuery("delete from AlunoExtraClasse a where a.extra_classe.id = :id").setParameter("id", id)
				.executeUpdate();
	}

}

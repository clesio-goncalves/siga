package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.ProfissionalSaude;

@Repository
public class ProfissionalSaudeDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(ProfissionalSaude profissional_saude) {
		manager.persist(profissional_saude);
	}

	public void altera(ProfissionalSaude profissional_saude) {
		manager.merge(profissional_saude);
	}

	public List<ProfissionalSaude> lista() {
		return manager.createQuery("select p from ProfissionalSaude p", ProfissionalSaude.class).getResultList();
	}

	public List<ProfissionalSaude> buscaPorSiape(int siape) {
		return manager.createQuery("select p from ProfissionalSaude p where p.siape = :siape", ProfissionalSaude.class)
				.setParameter("siape", siape).getResultList();
	}

	public ProfissionalSaude buscaPorId(Long id) {
		return manager.find(ProfissionalSaude.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from ProfissionalSaude p where p.id = :id").setParameter("id", id).executeUpdate();
	}

}

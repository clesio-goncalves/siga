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

	public ProfissionalSaude buscaPorId(Long id) {
		return manager.find(ProfissionalSaude.class, id);
	}

	public void remove(ProfissionalSaude profissional_saude) {
		manager.remove(buscaPorId(profissional_saude.getId()));
	}

}

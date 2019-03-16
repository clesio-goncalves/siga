package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Perfil;

@Repository
public class PerfilDao {

	@PersistenceContext
	private EntityManager manager;

	public List<Perfil> lista() {
		return manager.createQuery("select p from Perfil p order by p.id", Perfil.class).getResultList();
	}

	public Perfil buscaPorId(Long id) {
		return manager.find(Perfil.class, id);
	}
}

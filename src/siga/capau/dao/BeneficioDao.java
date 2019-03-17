package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Beneficio;

@Repository
public class BeneficioDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Beneficio seneficio) {
		manager.persist(seneficio);
	}

	public void altera(Beneficio seneficio) {
		manager.merge(seneficio);
	}

	public List<Beneficio> lista() {
		return manager.createQuery("select b from Beneficio b order by b.id", Beneficio.class).getResultList();
	}

	public List<Beneficio> buscaPorNome(String nome) {
		return manager.createQuery("select b from Beneficio b where b.nome = :nome", Beneficio.class)
				.setParameter("nome", nome).getResultList();
	}

	public Beneficio buscaPorId(Long id) {
		return manager.find(Beneficio.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Beneficio b where b.id = :id").setParameter("id", id).executeUpdate();
	}

}

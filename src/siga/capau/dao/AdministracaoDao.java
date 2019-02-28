package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Administracao;

@Repository
public class AdministracaoDao {

	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Administracao administracao) {
		manager.persist(administracao);
	}

	public void altera(Administracao administracao) {
		manager.merge(administracao);
	}

	public List<Administracao> lista() {
		return manager.createQuery("select a from Administracao a", Administracao.class).getResultList();
	}

	public Long administradorVinculadoUsuario(Long id) {
		return manager.createQuery("select count(*) from Administracao a where a.usuario.id = :id", Long.class)
				.setParameter("id", id).getSingleResult();
	}

	public List<Administracao> buscaPorSiape(int siape) {
		return manager.createQuery("select a from Administracao a where a.siape = :siape", Administracao.class)
				.setParameter("siape", siape).getResultList();
	}

	public Administracao buscaPorId(Long id) {
		return manager.find(Administracao.class, id);
	}

	public void remove(Long id) {
		manager.createQuery("delete from Administracao a where a.id = :id").setParameter("id", id).executeUpdate();
	}

}

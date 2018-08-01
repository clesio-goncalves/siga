package siga.capau.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import siga.capau.modelo.Permissao;

@Repository
public class PermissaoDao {

	@PersistenceContext
	private EntityManager manager;

	public List<Permissao> lista() {
		return manager.createQuery("select p from Permissao p", Permissao.class).getResultList();
	}

}

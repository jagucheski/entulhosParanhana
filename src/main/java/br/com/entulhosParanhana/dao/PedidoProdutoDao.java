package br.com.entulhosParanhana.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

//import br.com.entulhosParanhana.model.Pedido;
import br.com.entulhosParanhana.model.PedidoProduto;



public class PedidoProdutoDao {

	private static PedidoProdutoDao instance;
	protected EntityManager entityManager;

	public static PedidoProdutoDao getInstance() {
		if (instance == null) {
			instance = new PedidoProdutoDao();
		}
		return instance;
	}

	private PedidoProdutoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("entulhosParanhana");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public PedidoProduto getById(int id) {
		return (PedidoProduto) entityManager.find(PedidoProduto.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<PedidoProduto> findAll() {
		try {
			return (List<PedidoProduto>) entityManager.createQuery("from PedidoProduto pp order by pp.dataEntrega").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void persist(PedidoProduto pedidoProduto) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(pedidoProduto);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(PedidoProduto pedidoProduto) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(pedidoProduto);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(PedidoProduto pedidoProduto) {
		try {
			entityManager.getTransaction().begin();
			pedidoProduto = (PedidoProduto) entityManager.find(PedidoProduto.class, Integer.valueOf(pedidoProduto.getId()));
			entityManager.remove(pedidoProduto);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			PedidoProduto pedidoProduto = getById(id);
			remove(pedidoProduto);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}
}

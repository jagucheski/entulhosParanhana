package br.com.entulhosParanhana.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.entulhosParanhana.model.Pedido;

public class PedidoDao {

	private static PedidoDao instance;
	protected EntityManager entityManager;

	public static PedidoDao getInstance() {
		if (instance == null) {
			instance = new PedidoDao();
		}
		return instance;
	}

	private PedidoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("entulhosParanhana");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Pedido getById(int id) {
		return (Pedido) entityManager.find(Pedido.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> findAll() {
		try {
			return (List<Pedido>) entityManager.createQuery("from Pedido p order by p.cliente.nome").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> findAllByNomeCliente(String clienteFind) {
		try {
			Query query = entityManager.createQuery("from Pedido p where upper(p.cliente.nome) like upper(:nome) order by p.cliente.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(clienteFind).append("%"));
			return (ArrayList<Pedido>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
		public void persist(Pedido pedido) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(pedido);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(Pedido pedido) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(pedido);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(Pedido pedido) {
		try {
			entityManager.getTransaction().begin();
			pedido = (Pedido) entityManager.find(Pedido.class, Integer.valueOf(pedido.getId()));
			entityManager.remove(pedido);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Pedido pedido = getById(id);
			remove(pedido);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}
}

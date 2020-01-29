package br.com.entulhosParanhana.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.entulhosParanhana.model.Produto;

public class ProdutoDao {

	private static ProdutoDao instance;
	protected EntityManager entityManager;

	public static ProdutoDao getInstance() {
		if (instance == null) {
			instance = new ProdutoDao();
		}
		return instance;
	}

	private ProdutoDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("entulhosParanhana");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Produto getById(int id) {
		return (Produto) entityManager.find(Produto.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Produto> findAll() {
		try {
			return (List<Produto>) entityManager.createQuery("from Produto p order by p.nome").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> findAllByNome(Produto produtoFind) {
		try {
			Query query = entityManager.createQuery("from Produto p where upper(p.nome) like upper(:nome) order by p.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(produtoFind.getNome()).append("%").toString());
			return (ArrayList<Produto>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Produto> findAllByNome(String produtoFind) {
		try {
			Query query = entityManager.createQuery("from Produto p where upper(p.nome) like upper(:nome) order by p.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(produtoFind).append("%").toString());
			return (ArrayList<Produto>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Produto> findAllByDescricao(String produtoFind) {
		try {
			Query query = entityManager.createQuery("from Produto p where upper(p.descricao) like upper(:descricao) order by p.descricao");
			query.setParameter("descricao", (new StringBuilder("%")).append(produtoFind).append("%").toString());
			return (ArrayList<Produto>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findAllByDescricao(Produto produtoFind) {
		try {
			Query query = entityManager.createQuery("from Produto p where upper(p.descricao) like upper(:descricao) order by p.descricao");
			query.setParameter("descricao", (new StringBuilder("%")).append(produtoFind.getDescricao()).append("%").toString());
			return (ArrayList<Produto>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Produto> findAllByNomeDescricao(Produto produtoFind) {
		try {
			Query query = entityManager.createQuery("from Produto p where upper(p.nome) like upper(:nome) and p.descricao =:descricao order by p.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(produtoFind.getNome()).append("%").toString());
			query.setParameter("descricao", (new StringBuilder("%")).append(produtoFind.getDescricao()).append("%").toString());
			return (ArrayList<Produto>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}	
	
	public void persist(Produto produto) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(produto);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(Produto produto) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(produto);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(Produto produto) {
		try {
			entityManager.getTransaction().begin();
			produto = (Produto) entityManager.find(Produto.class, Integer.valueOf(produto.getId()));
			entityManager.remove(produto);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Produto produto = getById(id);
			remove(produto);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}
}

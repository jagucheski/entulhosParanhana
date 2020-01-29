package br.com.entulhosParanhana.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.entulhosParanhana.model.Cliente;

public class ClienteDao {

	private static ClienteDao instance;
	protected EntityManager entityManager;

	public static ClienteDao getInstance() {
		if (instance == null) {
			instance = new ClienteDao();
		}
		return instance;
	}

	private ClienteDao() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("entulhosParanhana");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public Cliente getById(int id) {
		return (Cliente) entityManager.find(Cliente.class, Integer.valueOf(id));
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findAll() {
		try {
			return (List<Cliente>) entityManager.createQuery("from Cliente c order by c.nome").getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findAllByNome(Cliente clienteFind) {
		try {
			Query query = entityManager.createQuery("from Cliente c where upper(c.nome) like upper(:nome) order by c.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(clienteFind.getNome()).append("%").toString());
			return (ArrayList<Cliente>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> findAllByNome(String clienteFind) {
		try {
			Query query = entityManager.createQuery("from Cliente c where upper(c.nome) like upper(:nome) order by c.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(clienteFind).append("%").toString());
			return (ArrayList<Cliente>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> findAllByCpf(Cliente ClienteFind) {
		try {
			Query query = entityManager.createQuery("from Cliente c where c.cpf = :cpf order by c.nome");
			query.setParameter("cpf", ClienteFind.getCpf());
			return (ArrayList<Cliente>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> findAllByNomeCpf(Cliente clienteFind) {
		try {
			Query query = entityManager.createQuery("from Cliente c where upper(c.nome) like upper(:nome) and c.cpf =:cpf order by c.nome");
			query.setParameter("nome", (new StringBuilder("%")).append(clienteFind.getNome()).append("%").toString());
			query.setParameter("cpf", clienteFind.getCpf());
			return (ArrayList<Cliente>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}	

	public Cliente getByCpf(String cpf) {
		try {
			Query query = entityManager.createQuery("from Cliente c where c.cpf = :cpf");
			query.setParameter("cpf", cpf);						
			return (Cliente) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Cliente getByNomeCpf(Cliente clienteFind) {
		try {
			Query query = entityManager.createQuery("from Cliente c where upper(c.nome) like upper(:nome) and c.cpf = :cpf");
			query.setParameter("nome", (new StringBuilder("%")).append(clienteFind.getNome()).append("%").toString());
			query.setParameter("cpf", clienteFind.getCpf());
			return (Cliente) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Cliente getByNomeCnpj(Cliente clienteFind) {
		try {
			Query query = entityManager.createQuery("from Cliente c where upper(c.nome) like upper(:nome) and c.cnpj = :cnpj");
			query.setParameter("nome", (new StringBuilder("%")).append(clienteFind.getNome()).append("%").toString());
			query.setParameter("cnpj", clienteFind.getCnpj());
			return (Cliente) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Cliente getByCnpj(String cnpj) {
		try {
			Query query = entityManager.createQuery("from Cliente c where c.cnpj = :cnpj");
			query.setParameter("cnpj", cnpj);						
			return (Cliente) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void persist(Cliente cliente) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(cliente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void merge(Cliente cliente) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(cliente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void remove(Cliente cliente) {
		try {
			entityManager.getTransaction().begin();
			cliente = (Cliente) entityManager.find(Cliente.class, Integer.valueOf(cliente.getId()));
			entityManager.remove(cliente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}

	public void removeById(int id) {
		try {
			Cliente cliente = getById(id);
			remove(cliente);
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new  PersistenceException();
		}
	}
}

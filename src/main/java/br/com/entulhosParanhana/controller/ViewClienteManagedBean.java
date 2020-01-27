package br.com.entulhosParanhana.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.ClienteDao;
import br.com.entulhosParanhana.model.Cliente;
import br.com.entulhosParanhana.uteis.Uteis;

@Named(value = "viewClienteController")
@ViewScoped
public class ViewClienteManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.controller.ViewClienteManagedBean");

	private Cliente clienteFind;
	private Cliente clienteView;
	private int clienteIdSelecionado;
	private int tabIndexViewCliente;
	private ArrayList<Cliente> clienteList;
	

	public ViewClienteManagedBean() {
		this.tabIndexViewCliente = 0;
		this.clienteFind = new Cliente();
		this.clienteView = new Cliente();
		this.clienteList = new ArrayList<Cliente>();
		findAllCliente();
	}

	public void findAllCliente() {
		try {
			clienteList = (ArrayList<Cliente>) ClienteDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findCliente() {
		/**TODO COLOCAR CNPJ E TELEFONE CELULAR**/
		try {
			if (StringUtils.isEmpty(clienteFind.getNome()) && StringUtils.isBlank(clienteFind.getCpf())) {
				findAllCliente();
			} else if (StringUtils.isEmpty(clienteFind.getNome())) {
				clienteList = (ArrayList<Cliente>) ClienteDao.getInstance().findAllByCpf(clienteFind);
			} else if (StringUtils.isEmpty(clienteFind.getCpf())) {
				clienteList = (ArrayList<Cliente>) ClienteDao.getInstance().findAllByNome(clienteFind);
			} else {
				clienteList = (ArrayList<Cliente>) ClienteDao.getInstance().findAllByNomeCpf(clienteFind);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void detalharCliente() {
		try {
			this.clienteView = ClienteDao.getInstance().getById(clienteIdSelecionado);
			setTabIndexViewCliente(1);			
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o Cliente! ");
		}
	}

	public Cliente getClienteFind() {
		return clienteFind;
	}

	public void setClienteFind(Cliente clienteFind) {
		this.clienteFind = clienteFind;
	}

	public Cliente getClienteView() {
		return clienteView;
	}

	public void setClienteView(Cliente clienteView) {
		this.clienteView = clienteView;
	}

	public int getClienteIdSelecionado() {
		return clienteIdSelecionado;
	}

	public void setClienteIdSelecionado(int clienteIdSelecionado) {
		this.clienteIdSelecionado = clienteIdSelecionado;
	}

	public int getTabIndexViewCliente() {
		return tabIndexViewCliente;
	}

	public void setTabIndexViewCliente(int tabIndexViewCliente) {
		this.tabIndexViewCliente = tabIndexViewCliente;
	}

	public ArrayList<Cliente> getClienteList() {
		return clienteList;
	}

	public void setClienteList(ArrayList<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

	
	
}

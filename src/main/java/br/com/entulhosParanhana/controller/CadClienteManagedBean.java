package br.com.entulhosParanhana.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.CidadeDao;
import br.com.entulhosParanhana.dao.ClienteDao;
import br.com.entulhosParanhana.model.Cidade;
import br.com.entulhosParanhana.model.Cliente;
import br.com.entulhosParanhana.uteis.Uteis;
import br.com.entulhosParanhana.uteis.ValidaCPF;
import br.com.entulhosParanhana.uteis.ValidaCNPJ;

@Named(value = "cadClienteController")
@ViewScoped
public class CadClienteManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.controller.CadClienteManagedBean");

	private Cliente clienteFind;
	private Cliente clienteCadastro;
	private int clienteIdSelecionado;
	private int tabIndexCadCliente;
	private ArrayList<Cliente> clienteList;
	

	public CadClienteManagedBean() {
		this.tabIndexCadCliente = 0;
		this.clienteFind = new Cliente();
		this.clienteCadastro = new Cliente();
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

	public void salvarCliente() {
		if (validaCampos()) {
			if (clienteCadastro.getTipo().equals("FISICA")) {
				if (validaCpf()) {
					saveCliente();
					findCliente();
				}
			}else if (clienteCadastro.getTipo().equals("JURIDICA")) {
				if (validaCnpj()) {
					saveCliente();
					findCliente();
				}
			}
		}
		setTabIndexCadCliente(1);
	}

	public void saveCliente() {
		try {
			this.clienteCadastro.setNome(this.clienteCadastro.getNome().toUpperCase());
			ClienteDao.getInstance().merge(this.clienteCadastro);
			Uteis.MensagemInfo("Cliente salvo com sucesso!");
			this.clienteCadastro = new Cliente();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar o Cliente!");			
		}
	}

	public void deletarCliente() {
		if (validaCliente()) {
			deleteCliente();
			findCliente();
			setTabIndexCadCliente(1);
		}
	}

	public void deleteCliente() {
		try {
			ClienteDao.getInstance().remove(clienteCadastro);
			Uteis.MensagemInfo("Cliente excluido com sucesso!");
			novoCliente();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir o Cliente!");
		}
	}

	public boolean validaCliente() {
		boolean clienteSelecionada = true;
		if (clienteCadastro.getId() == 0 || clienteCadastro.getId() < 1) {
			Uteis.MensagemAtencao("Selecione um Cliente para excluir");
			clienteSelecionada = false;
		}
		return clienteSelecionada;
	}

	
	/**Verifica se os campos obrigatórios estão preenchidos e CPF ou CNPJ são válidos*/
	public boolean validaCampos() {
		boolean camposValidos = true;

		if (clienteCadastro.getTipo().equals("null")) {
			Uteis.MensagemAtencao("Campo Tipo Cliente é obrigatório");
			camposValidos = false;
		}
		
 
		if (StringUtils.isEmpty(clienteCadastro.getNome()) && StringUtils.isBlank(clienteCadastro.getNome())) {
			Uteis.MensagemAtencao("Campo Nome/Razão Social é obrigatório");
			camposValidos = false;
		}
		
		
		if (clienteCadastro.getTipo().equals("FISICA")) {
			if (!ValidaCPF.isCPF(clienteCadastro.getCpf())) {
				Uteis.MensagemAtencao("CPF inválido");
				camposValidos = false;
			}	
		}

		if (clienteCadastro.getTipo().equals("JURIDICA")) {
			if (!ValidaCNPJ.isCNPJ(clienteCadastro.getCnpj())) {
				Uteis.MensagemAtencao("CNPJ inválido");
				camposValidos = false;
			}	
		}			
		return camposValidos;
	}

	/**Verifica se ja existe cliente cadastrado com o mesmo CPF**/
	public boolean validaCpf() {
		boolean confirm = false;
		Cliente clienteTemp = ClienteDao.getInstance().getByCpf(this.clienteCadastro.getCpf());

		if (clienteTemp == null) {
			return true;
		} else {
			if (clienteCadastro.getId() == clienteTemp.getId()) {
				confirm = true;
			} else if (clienteCadastro.getId() != clienteTemp.getId()) {
				Uteis.MensagemAtencao("Existe um Cliente cadastrado com o CPF informado");
			}
		}
		return confirm;
	}

	/**Verifica se ja existe cliente cadastrado com o mesmo CPF**/
	public boolean validaCnpj() {
		boolean confirm = false;
		Cliente clienteTemp = ClienteDao.getInstance().getByCnpj(this.clienteCadastro.getCnpj());
		
		if (clienteTemp == null) {
			return true;
		} else {
			if (clienteCadastro.getId() == clienteTemp.getId()) {
				confirm = true;
			} else if (clienteCadastro.getId() != clienteTemp.getId()) {
				Uteis.MensagemAtencao("Existe um Cliente cadastrado com o CNPJ informado");
			}
		}
		return confirm;
	}

	public void novoCliente() {
		try {
			clienteCadastro = new Cliente();
			setTabIndexCadCliente(1);			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharCliente() {
		try {
			this.clienteCadastro = ClienteDao.getInstance().getById(clienteIdSelecionado);
			setTabIndexCadCliente(1);			
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o Cliente! ");
		}
	}
	
	public ArrayList<Cidade> findCidadeAutoComplete(String query) {
		ArrayList<Cidade> cidadeListTemp = new ArrayList<Cidade>();
		try {
			cidadeListTemp = CidadeDao.getInstance().findAllByNome(query);
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
		return cidadeListTemp;
	}

	public Cliente getClienteFind() {
		return clienteFind;
	}

	public void setClienteFind(Cliente clienteFind) {
		this.clienteFind = clienteFind;
	}

	public Cliente getClienteCadastro() {
		return clienteCadastro;
	}

	public void setClienteCadastro(Cliente clienteCadastro) {
		this.clienteCadastro = clienteCadastro;
	}

	public int getClienteIdSelecionado() {
		return clienteIdSelecionado;
	}

	public void setClienteIdSelecionado(int clienteIdSelecionado) {
		this.clienteIdSelecionado = clienteIdSelecionado;
	}

	public int getTabIndexCadCliente() {
		return tabIndexCadCliente;
	}

	public void setTabIndexCadCliente(int tabIndexCadCliente) {
		this.tabIndexCadCliente = tabIndexCadCliente;
	}

	public ArrayList<Cliente> getClienteList() {
		return clienteList;
	}

	public void setClienteList(ArrayList<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

	
	
	
}

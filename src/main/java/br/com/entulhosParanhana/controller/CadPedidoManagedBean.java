package br.com.entulhosParanhana.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.CidadeDao;
import br.com.entulhosParanhana.dao.ClienteDao;
import br.com.entulhosParanhana.dao.PedidoDao;
import br.com.entulhosParanhana.dao.ProdutoDao;
import br.com.entulhosParanhana.model.Cidade;
import br.com.entulhosParanhana.model.Cliente;
import br.com.entulhosParanhana.model.Pedido;
import br.com.entulhosParanhana.model.PedidoProduto;
import br.com.entulhosParanhana.model.Produto;
import br.com.entulhosParanhana.uteis.DateUtils;
import br.com.entulhosParanhana.uteis.Uteis;

@Named(value = "cadPedidoController")
@ViewScoped
public class CadPedidoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.controller.CadPedidoManagedBean");

	private Pedido pedidoFind;
	private Pedido pedidoCadastro;
	private int pedidoIdSelecionado;
	private int tabIndexCadPedido;

	private ArrayList<Pedido> pedidoFindList;

	private PedidoProduto pedidoProdutoCad;
	private ArrayList<PedidoProduto> pedidoProdutoList;
	private int pedidoProdutoIdSelecionado;
	private Produto produtoCad;

	public CadPedidoManagedBean() {
		this.tabIndexCadPedido = 0;
		this.pedidoFind = new Pedido();
		this.pedidoCadastro = new Pedido();
		this.pedidoFindList = new ArrayList<Pedido>();
		this.pedidoProdutoCad = new PedidoProduto();
		this.pedidoProdutoList = new ArrayList<PedidoProduto>();
		findAllPedido();
	}

	public void findAllPedido() {
		try {
			pedidoFindList = (ArrayList<Pedido>) PedidoDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findPedido() {
		try {
			if (StringUtils.isEmpty(pedidoFind.getCliente().getNome()) && pedidoFind.getDataPedido() == null) {
				pedidoFindList = (ArrayList<Pedido>) PedidoDao.getInstance().findAllByStatusPagamento(pedidoFind);				
			}else if (StringUtils.isEmpty(pedidoFind.getCliente().getNome()) && pedidoFind.getDataPedido() != null) {
				pedidoFindList = (ArrayList<Pedido>) PedidoDao.getInstance().findAllByDataPedidoStatusPagamento(pedidoFind);
			} else if (StringUtils.isNotEmpty(pedidoFind.getCliente().getNome()) && pedidoFind.getDataPedido() == null) {
				pedidoFindList = (ArrayList<Pedido>) PedidoDao.getInstance().findAllByClienteNomeStatusPagamento(pedidoFind);
			} else {
				pedidoFindList = (ArrayList<Pedido>) PedidoDao.getInstance().findAllByClienteNomeDataPedidoStatusPagamento(pedidoFind);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void salvarPedido() {
		if (validaCampos()) {
				savePedido();
				findPedido();
		}
		setTabIndexCadPedido(1);
	}

	public void savePedido() {
		try {
//			this.pedidoCadastro.setNome(this.pedidoCadastro.getNome().toUpperCase());
//			ProdutoDao.getInstance().merge(this.pedidoCadastro);
//			Uteis.MensagemInfo("Produto salvo com sucesso!");
//			this.pedidoCadastro = new Produto();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar o Produto!");			
		}
	}

	public void deletarPedido() {
		if (validaPedido()) {
			deletePedido();
			findPedido();
			setTabIndexCadPedido(1);
		}
	}

	public void deletePedido() {
		try {
			PedidoDao.getInstance().remove(pedidoCadastro);
			Uteis.MensagemInfo("Pedido excluido com sucesso!");
			novoPedido();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir o Pedido!");
		}
	}

	public boolean validaPedido() {
		boolean pedidoSelecionado = true;
		if (pedidoCadastro.getId() == 0 || pedidoCadastro.getId() < 1) {
			Uteis.MensagemAtencao("Selecione um pedido para excluir");
			pedidoSelecionado = false;
		}
		return pedidoSelecionado;
	}

	
	/**Verifica se os campos obrigatórios estão preenchidos**/
	public boolean validaCampos() {
		boolean camposValidos = true;

//		if (StringUtils.isEmpty(pedidoCadastro.getNome()) && StringUtils.isBlank(pedidoCadastro.getNome())) {
//			Uteis.MensagemAtencao("Campo Nome é obrigatório");
//			camposValidos = false;
//		}
//		
//		if (StringUtils.isEmpty(pedidoCadastro.getDescricao()) && StringUtils.isBlank(pedidoCadastro.getDescricao())) {
//			Uteis.MensagemAtencao("Campo Descrição é obrigatório");
//			camposValidos = false;
//		}
//		
//		if (StringUtils.isEmpty(pedidoCadastro.getUnidadeMedida()) && StringUtils.isBlank(pedidoCadastro.getUnidadeMedida())) {
//			Uteis.MensagemAtencao("Campo Unidade Medida é obrigatório");
//			camposValidos = false;
//		}
//
//		if(pedidoCadastro.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0){
//			Uteis.MensagemAtencao("Campo R$ Unitário  é obrigatório");
//			camposValidos = false;
//		}
//					
		return camposValidos;
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

	public ArrayList<Cliente> findClienteAutoComplete(String query) {
		ArrayList<Cliente> clienteListTemp = new ArrayList<Cliente>(); 
		try {
			clienteListTemp = ClienteDao.getInstance().findAllByNome(query.toUpperCase());
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
		return clienteListTemp;
	}	

	public ArrayList<Produto> findProdutoAutoComplete(String query) {
		ArrayList<Produto> produtoListTemp = new ArrayList<Produto>(); 
		try {
			produtoListTemp = ProdutoDao.getInstance().findAllByNome(query.toUpperCase());
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
		return produtoListTemp;
	}	

	public void novoPedido() {
		try {
			pedidoCadastro = new Pedido();
			setTabIndexCadPedido(1);			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharPedido() {
		try {
			this.pedidoCadastro = PedidoDao.getInstance().getById(pedidoIdSelecionado);
			setTabIndexCadPedido(1);			
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o Pedido! ");
		}
	}
	
	public void carregaDadosClienteSelecionado() {
		this.pedidoCadastro.setEnderecoEntrega(this.pedidoCadastro.getCliente().getEndereco());
		this.pedidoCadastro.setBairroEntrega(this.pedidoCadastro.getCliente().getBairro());
		this.pedidoCadastro.setCidade(this.pedidoCadastro.getCliente().getCidade());
	}

	public void carregaDadosProdutoSelecionado() {
		this.produtoCad = ProdutoDao.getInstance().getById(this.pedidoProdutoCad.getProduto().getId());
		this.pedidoProdutoCad.setDataPrevistaRetirada(DateUtils.getDataPrevistaRetirada(this.produtoCad.getPrazoDias()));
	}
	
	public void novoItemPedido() {		
		
	}
	
	public void detalharProdutoPedido() {		
		
	}
	
	public Pedido getPedidoFind() {
		return pedidoFind;
	}

	public void setPedidoFind(Pedido pedidoFind) {
		this.pedidoFind = pedidoFind;
	}

	public Pedido getPedidoCadastro() {
		return pedidoCadastro;
	}

	public void setPedidoCadastro(Pedido pedidoCadastro) {
		this.pedidoCadastro = pedidoCadastro;
	}

	public int getPedidoIdSelecionado() {
		return pedidoIdSelecionado;
	}

	public void setPedidoIdSelecionado(int pedidoIdSelecionado) {
		this.pedidoIdSelecionado = pedidoIdSelecionado;
	}

	public int getTabIndexCadPedido() {
		return tabIndexCadPedido;
	}

	public void setTabIndexCadPedido(int tabIndexCadPedido) {
		this.tabIndexCadPedido = tabIndexCadPedido;
	}

	public ArrayList<Pedido> getPedidoFindList() {
		return pedidoFindList;
	}

	public void setPedidoFindList(ArrayList<Pedido> pedidoFindList) {
		this.pedidoFindList = pedidoFindList;
	}

	public PedidoProduto getPedidoProdutoCad() {
		return pedidoProdutoCad;
	}

	public void setPedidoProdutoCad(PedidoProduto pedidoProdutoCad) {
		this.pedidoProdutoCad = pedidoProdutoCad;
	}

	public ArrayList<PedidoProduto> getPedidoProdutoList() {
		return pedidoProdutoList;
	}

	public void setPedidoProdutoList(ArrayList<PedidoProduto> pedidoProdutoList) {
		this.pedidoProdutoList = pedidoProdutoList;
	}

	public int getPedidoProdutoIdSelecionado() {
		return pedidoProdutoIdSelecionado;
	}

	public void setPedidoProdutoIdSelecionado(int pedidoProdutoIdSelecionado) {
		this.pedidoProdutoIdSelecionado = pedidoProdutoIdSelecionado;
	}

	public Produto getProdutoCad() {
		return produtoCad;
	}

	public void setProdutoCad(Produto produtoCad) {
		this.produtoCad = produtoCad;
	}
	
}

package br.com.entulhosParanhana.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.CidadeDao;
import br.com.entulhosParanhana.dao.ProdutoDao;
import br.com.entulhosParanhana.model.Cidade;
import br.com.entulhosParanhana.model.Produto;
import br.com.entulhosParanhana.uteis.Uteis;

@Named(value = "cadProdutoController")
@ViewScoped
public class CadProdutoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.controller.CadProdutoManagedBean");

	private Produto produtoFind;
	private Produto produtoCadastro;
	private int produtoIdSelecionado;
	private int tabIndexCadProduto;
	private ArrayList<Produto> produtoList;
	
	public CadProdutoManagedBean() {
		this.tabIndexCadProduto = 0;
		this.produtoFind = new Produto();
		this.produtoCadastro = new Produto();
		this.produtoList = new ArrayList<Produto>();
		findAllProduto();
	}

	public void findAllProduto() {
		try {
			produtoList = (ArrayList<Produto>) ProdutoDao.getInstance().findAll();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void findProduto() {
		try {
			if (StringUtils.isEmpty(produtoFind.getNome()) && StringUtils.isBlank(produtoFind.getDescricao())) {
				findAllProduto();
			} else if (StringUtils.isEmpty(produtoFind.getNome())) {
				produtoList = (ArrayList<Produto>) ProdutoDao.getInstance().findAllByDescricao(produtoFind);
			} else if (StringUtils.isEmpty(produtoFind.getDescricao())) {
				produtoList = (ArrayList<Produto>) ProdutoDao.getInstance().findAllByNome(produtoFind);
			} else {
				produtoList = (ArrayList<Produto>) ProdutoDao.getInstance().findAllByNomeDescricao(produtoFind);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops. Ocorreu um erro, tente novamente.");
		}
	}

	public void salvarProduto() {
		if (validaCampos()) {
				saveProduto();
				findProduto();
		}
		setTabIndexCadProduto(1);
	}

	public void saveProduto() {
		try {
			this.produtoCadastro.setNome(this.produtoCadastro.getNome().toUpperCase());
			ProdutoDao.getInstance().merge(this.produtoCadastro);
			Uteis.MensagemInfo("Produto salvo com sucesso!");
			this.produtoCadastro = new Produto();
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao salvar o Produto!");			
		}
	}

	public void deletarProduto() {
		if (validaProduto()) {
			deleteProduto();
			findProduto();
			setTabIndexCadProduto(1);
		}
	}

	public void deleteProduto() {
		try {
			ProdutoDao.getInstance().remove(produtoCadastro);
			Uteis.MensagemInfo("Produto excluido com sucesso!");
			novoProduto();
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao excluir o Produto!");
		}
	}

	public boolean validaProduto() {
		boolean produtoSelecionado = true;
		if (produtoCadastro.getId() == 0 || produtoCadastro.getId() < 1) {
			Uteis.MensagemAtencao("Selecione um produto para excluir");
			produtoSelecionado = false;
		}
		return produtoSelecionado;
	}

	
	/**Verifica se os campos obrigatórios estão preenchidos**/
	public boolean validaCampos() {
		boolean camposValidos = true;

		if (StringUtils.isEmpty(produtoCadastro.getNome()) && StringUtils.isBlank(produtoCadastro.getNome())) {
			Uteis.MensagemAtencao("Campo Nome é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(produtoCadastro.getDescricao()) && StringUtils.isBlank(produtoCadastro.getDescricao())) {
			Uteis.MensagemAtencao("Campo Descrição é obrigatório");
			camposValidos = false;
		}
		
		if (StringUtils.isEmpty(produtoCadastro.getUnidadeMedida()) && StringUtils.isBlank(produtoCadastro.getUnidadeMedida())) {
			Uteis.MensagemAtencao("Campo Unidade Medida é obrigatório");
			camposValidos = false;
		}

		if(produtoCadastro.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0){
			Uteis.MensagemAtencao("Campo R$ Unitário  é obrigatório");
			camposValidos = false;
		}
					
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

	public void novoProduto() {
		try {
			produtoCadastro = new Produto();
			setTabIndexCadProduto(1);			
		} catch (Exception e) {
			System.out.println(e.toString());
			logger.error(e.toString(), e);
		}
	}

	public void detalharProduto() {
		try {
			this.produtoCadastro = ProdutoDao.getInstance().getById(produtoIdSelecionado);
			setTabIndexCadProduto(1);			
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops, Ocorreu um erro ao selecionar o Produto! ");
		}
	}

	public Produto getProdutoFind() {
		return produtoFind;
	}

	public void setProdutoFind(Produto produtoFind) {
		this.produtoFind = produtoFind;
	}

	public Produto getProdutoCadastro() {
		return produtoCadastro;
	}

	public void setProdutoCadastro(Produto produtoCadastro) {
		this.produtoCadastro = produtoCadastro;
	}

	public int getProdutoIdSelecionado() {
		return produtoIdSelecionado;
	}

	public void setProdutoIdSelecionado(int produtoIdSelecionado) {
		this.produtoIdSelecionado = produtoIdSelecionado;
	}

	public int getTabIndexCadProduto() {
		return tabIndexCadProduto;
	}

	public void setTabIndexCadProduto(int tabIndexCadProduto) {
		this.tabIndexCadProduto = tabIndexCadProduto;
	}

	public ArrayList<Produto> getProdutoList() {
		return produtoList;
	}

	public void setProdutoList(ArrayList<Produto> produtoList) {
		this.produtoList = produtoList;
	}
	
}

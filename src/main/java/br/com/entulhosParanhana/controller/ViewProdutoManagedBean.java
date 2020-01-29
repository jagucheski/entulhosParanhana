package br.com.entulhosParanhana.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.ProdutoDao;
import br.com.entulhosParanhana.model.Produto;
import br.com.entulhosParanhana.uteis.Uteis;

@Named(value = "viewProdutoController")
@ViewScoped
public class ViewProdutoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.controller.ViewProdutoManagedBean");

	private Produto produtoFind;
	private Produto produtoView;
	private int produtoIdSelecionado;
	private int tabIndexViewProduto;
	private ArrayList<Produto> produtoList;
	

	public ViewProdutoManagedBean() {
		this.tabIndexViewProduto = 0;
		this.produtoFind = new Produto();
		this.produtoView = new Produto();
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

	public void detalharProduto() {
		try {
			this.produtoView = ProdutoDao.getInstance().getById(produtoIdSelecionado);
			setTabIndexViewProduto(1);			
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

	public Produto getProdutoView() {
		return produtoView;
	}

	public void setProdutoView(Produto produtoView) {
		this.produtoView = produtoView;
	}

	public int getProdutoIdSelecionado() {
		return produtoIdSelecionado;
	}

	public void setProdutoIdSelecionado(int produtoIdSelecionado) {
		this.produtoIdSelecionado = produtoIdSelecionado;
	}

	public int getTabIndexViewProduto() {
		return tabIndexViewProduto;
	}

	public void setTabIndexViewProduto(int tabIndexViewProduto) {
		this.tabIndexViewProduto = tabIndexViewProduto;
	}

	public ArrayList<Produto> getProdutoList() {
		return produtoList;
	}

	public void setProdutoList(ArrayList<Produto> produtoList) {
		this.produtoList = produtoList;
	}	
	
}

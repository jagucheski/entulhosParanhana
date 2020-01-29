package br.com.entulhosParanhana.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;

@Entity
@Table(name = "pedido_produto")
public class PedidoProduto {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega")
	private Date dataEntrega;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_retirada")
	private Date dataRetirada;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_prevista_retirada")
	private Date dataPrevistaRetirada;

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column(name = "preco_produto", precision = 7, scale = 2)
	@DecimalMax("9999999.99")
	private BigDecimal precoProduto;

	public PedidoProduto() {
		super();
		this.produto = new Produto();
		this.produto = new Produto();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Date getDataPrevistaRetirada() {
		return dataPrevistaRetirada;
	}

	public void setDataPrevistaRetirada(Date dataPrevistaRetirada) {
		this.dataPrevistaRetirada = dataPrevistaRetirada;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoProduto() {
		return precoProduto;
	}

	public void setPrecoProduto(BigDecimal precoProduto) {
		this.precoProduto = precoProduto;
	}

}

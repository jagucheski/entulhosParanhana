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
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "pedido")
public class Pedido {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_pedido")
	private Date dataPedido;

	@Length(max = 150)
	@Column(name = "endereco_entrega")
	private String enderecoEntrega;

	@Length(max = 150)
	@Column(name = "bairro_entrega")
	private String bairroEntrega;

	@Length(max = 100)
	@Column(name = "ponto_referencia")
	private String pontoReferencia;

	@Length(max = 150)
	@Column(name = "forma_pagamento")
	private String formaPagamento;

	@Length(max = 15)
	@Column(name = "status_pagamento")
	private String statusPagamento;

	@Column(name = "valor_total", precision = 7, scale = 2)
	@DecimalMax("9999999.99")
	private BigDecimal valorTotal;

	@Length(max = 20)
	@Column(name = "numero_ordem")
	private String numeroOrdem;

	@Length(max = 100)
	private String observacao;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "pedido_cidade", joinColumns = {
			@JoinColumn(name = "pedido_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cidade_id", referencedColumnName = "id") })
	private Cidade cidade;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "pedido_cliente", joinColumns = {
			@JoinColumn(name = "pedido_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cliente_id", referencedColumnName = "id") })
	private Cliente cliente;

	public Pedido() {
		super();
		this.cidade = new Cidade();
		this.cliente = new Cliente();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public String getBairroEntrega() {
		return bairroEntrega;
	}

	public void setBairroEntrega(String bairroEntrega) {
		this.bairroEntrega = bairroEntrega;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(String statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getNumeroOrdem() {
		return numeroOrdem;
	}

	public void setNumeroOrdem(String numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getToDetail() {
		String detail = "";
		return detail;
	}

}

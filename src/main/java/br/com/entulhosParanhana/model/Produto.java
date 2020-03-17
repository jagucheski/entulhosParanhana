package br.com.entulhosParanhana.model;

import java.math.BigDecimal;

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
import javax.validation.constraints.DecimalMax;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "produto")
public class Produto {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(max = 50)
	private String nome;

	@Length(max = 150)
	private String descricao;

	@Column(name = "valor_unitario", precision = 7, scale = 2)
	@DecimalMax("9999999.99")
	private BigDecimal valorUnitario;

	@Length(max = 15)
	@Column(name = "unidade_medida")
	private String unidadeMedida;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "produto_cidade", joinColumns = {
			@JoinColumn(name = "produto_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cidade_id", referencedColumnName = "id") })
	private Cidade cidade;
	
	@Column(name = "prazo_dias")
	private int prazoDias;

	public Produto() {
		super();
		this.valorUnitario = new BigDecimal(0.0);
		this.cidade = new Cidade();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public int getPrazoDias() {
		return prazoDias;
	}

	public void setPrazoDias(int prazoDias) {
		this.prazoDias = prazoDias;
	}
	
}

package br.com.entulhosParanhana.model;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "cliente")
public class Cliente {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(max = 150)
	private String nome;

	@Length(max = 14)
	private String cpf;

	@Length(max = 14)
	private String rg;

	@Length(max = 20)
	private String cnpj;

	@Length(max = 20)
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;

	@Length(max = 50)
	@Column(name = "dados_bancarios")
	private String dadosBancarios;

	@Length(max = 100)
	private String observacao;

	@Length(max = 150)
	private String endereco;

	@Length(max = 150)
	private String bairro;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinTable(name = "cliente_cidade", joinColumns = {
			@JoinColumn(name = "cliente_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "cidade_id", referencedColumnName = "id") })
	private Cidade cidade;

	@Length(max = 15)
	@Column(name = "tel_residencial")
	private String telefoneResidencial;

	@Length(max = 15)
	@Column(name = "tel_comercial")
	private String telefoneComercial;

	@Length(max = 15)
	@Column(name = "tel_celular")
	private String telefoneCelular;

	public Cliente() {
		super();
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getDadosBancarios() {
		return dadosBancarios;
	}

	public void setDadosBancarios(String dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getToDetail() {
		String detail = "";
		if (getId() != 0 && getId() >= 1) {
			detail = (new StringBuilder(String.valueOf(detail))).append(getNome()).append(" - ").toString();
			if(StringUtils.isNotEmpty(getCpf()) || StringUtils.isNotBlank(getCpf())){
				detail = (new StringBuilder(String.valueOf(detail))).append(" CPF: ").append(getCpf()).append(" - ").toString();
			}
			if(StringUtils.isNotEmpty(getCnpj()) || StringUtils.isNotBlank(getCnpj())){			
				detail = (new StringBuilder(String.valueOf(detail))).append(" CNPJ: ").append(getCnpj()).toString();
			}
		}
		return detail;
	}

}

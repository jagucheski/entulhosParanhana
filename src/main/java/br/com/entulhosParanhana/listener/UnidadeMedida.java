package br.com.entulhosParanhana.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "unidadeMedidaListener")
@SessionScoped
public class UnidadeMedida implements Serializable {

	private static final long serialVersionUID = 1L;
	private String unidadeMedida;
	private Map<String, String> unidadesMedida;

	public UnidadeMedida() {
		unidadesMedida = new TreeMap<String, String>();
		unidadesMedida.put("M", "M");
		unidadesMedida.put("M2", "M2");
		unidadesMedida.put("M3", "M3");
		unidadesMedida.put("KG", "KG");
		unidadesMedida.put("Diaria", "Diaria");
		unidadesMedida.put("Mês", "Mês");
		unidadesMedida.put("UN", "UN");
		unidadesMedida.put("L", "L");		
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Map<String, String> getUnidadesMedida() {
		return unidadesMedida;
	}

	public void setUnidadesMedida(Map<String, String> unidadesMedida) {
		this.unidadesMedida = unidadesMedida;
	}

	
}

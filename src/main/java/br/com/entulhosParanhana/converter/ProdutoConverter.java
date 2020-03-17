package br.com.entulhosParanhana.converter;

import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.ProdutoDao;
import br.com.entulhosParanhana.model.Produto;
import br.com.entulhosParanhana.uteis.Uteis;

@FacesConverter(value="produtoConverter")
public class ProdutoConverter implements Converter {

	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.ProdutoConverter");
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		try {
			if (value == null || value.trim().length() <= 0) {
				return null;
			}
			String id;
			StringTokenizer st = new StringTokenizer(value, "-");
			id = st.nextToken();
			return ProdutoDao.getInstance().getById(Integer.valueOf(id).intValue());
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops! Produto inválido.");
			logger.error(e.toString(), e);
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		String retorno = null;

		try {
			if (object != null) {
				Produto produto = (Produto) object;
				retorno = (new StringBuilder(String.valueOf(produto.getId()))).append("-").append(produto.getNome()).toString();
			}
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops! Produto inválido.");
			logger.error(e.toString(), e);
			return null;
		}
		return retorno;
	}
}
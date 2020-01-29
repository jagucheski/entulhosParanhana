package br.com.entulhosParanhana.converter;

import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import br.com.entulhosParanhana.dao.CidadeDao;
import br.com.entulhosParanhana.model.Cidade;
import br.com.entulhosParanhana.uteis.Uteis;

@FacesConverter(value="cidadeConverter")
public class CidadeConverter implements Converter {

	static final Logger logger = Logger.getLogger("br.com.entulhosParanhana.CidadeConverter");
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		try {
			if (value == null || value.trim().length() <= 0) {
				return null;
			}
			String id;
			StringTokenizer st = new StringTokenizer(value, "-");
			id = st.nextToken();
			return CidadeDao.getInstance().getById(Integer.valueOf(id).intValue());
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops! Cidade inválida.");
			logger.error(e.toString(), e);
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		String retorno = null;

		try {
			if (object != null) {
				Cidade cidade = (Cidade) object;
				retorno = (new StringBuilder(String.valueOf(cidade.getId()))).append("-").append(cidade.getNome()).append("-").append(cidade.getUf()).toString();
			}
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops! Cidade inválida.");
			logger.error(e.toString(), e);
			return null;
		}
		return retorno;
	}
}
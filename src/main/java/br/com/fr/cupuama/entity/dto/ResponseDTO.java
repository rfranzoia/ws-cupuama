package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

/**
 * @author Romeu Franzoia Jr
 *
 */
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer statusCode;
	private String mensagem;
	private Object entity;

	public ResponseDTO(Integer statusCode) {
		this(statusCode, "");
	}
	
	public ResponseDTO(Integer statusCode, String mensagem) {
		this(statusCode, mensagem, null);
	}

	public ResponseDTO(Integer statusCode, String mensagem, Object entity) {
		this.statusCode = statusCode;
		this.mensagem = mensagem;
		this.entity = entity;
	}
	
	public ResponseDTO(Integer statusCode, Object entity) {
		this(statusCode, "", entity);
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseDTO other = (ResponseDTO) obj;
		if (entity == null) {
			if (other.getEntity() != null)
				return false;
		} else if (!entity.equals(other.getEntity()))
			return false;
		if (mensagem == null) {
			if (other.getMensagem() != null)
				return false;
		} else if (!mensagem.equals(other.getMensagem()))
			return false;
		if (statusCode == null) {
			if (other.getStatusCode() != null)
				return false;
		} else if (!statusCode.equals(other.getStatusCode()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{\"statusCode\":" + statusCode + ", \"mensagem\":\"" + mensagem + "\", \"entity\":" + entity.toString() + "}";
	}

}
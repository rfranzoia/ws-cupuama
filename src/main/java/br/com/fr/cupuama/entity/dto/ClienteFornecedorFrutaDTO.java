package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class ClienteFornecedorFrutaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer keyClienteFornecedorId;
	private String keyClienteFornecedorNome;
	private String keyClienteFornecedorCpfCnpj;

	private Integer keyFrutaId;
	private String keyFrutaNome;
	private String keyFrutaSigla;
	private String keyFrutaSafra;

	public Integer getKeyClienteFornecedorId() {
		return keyClienteFornecedorId;
	}

	public void setKeyClienteFornecedorId(Integer keyClienteFornecedorId) {
		this.keyClienteFornecedorId = keyClienteFornecedorId;
	}

	public Integer getKeyFrutaId() {
		return keyFrutaId;
	}

	public void setKeyFrutaId(Integer keyFrutaId) {
		this.keyFrutaId = keyFrutaId;
	}

	public String getKeyClienteFornecedorNome() {
		return keyClienteFornecedorNome;
	}

	public void setKeyClienteFornecedorNome(String keyClienteFornecedorNome) {
		this.keyClienteFornecedorNome = keyClienteFornecedorNome;
	}

	public String getKeyClienteFornecedorCpfCnpj() {
		return keyClienteFornecedorCpfCnpj;
	}

	public void setKeyClienteFornecedorCpfCnpj(String keyClienteFornecedorCpfCnpj) {
		this.keyClienteFornecedorCpfCnpj = keyClienteFornecedorCpfCnpj;
	}

	public String getKeyFrutaNome() {
		return keyFrutaNome;
	}

	public void setKeyFrutaNome(String keyFrutaNome) {
		this.keyFrutaNome = keyFrutaNome;
	}

	public String getKeyFrutaSigla() {
		return keyFrutaSigla;
	}

	public void setKeyFrutaSigla(String keyFrutaSigla) {
		this.keyFrutaSigla = keyFrutaSigla;
	}

	public String getKeyFrutaSafra() {
		return keyFrutaSafra;
	}

	public void setKeyFrutaSafra(String keyFrutaSafra) {
		this.keyFrutaSafra = keyFrutaSafra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyFrutaId == null) ? 0 : keyFrutaId.hashCode());
		result = prime * result + ((keyClienteFornecedorId == null) ? 0 : keyClienteFornecedorId.hashCode());
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
		ClienteFornecedorFrutaDTO other = (ClienteFornecedorFrutaDTO) obj;
		if (keyFrutaId == null) {
			if (other.getKeyFrutaId() != null)
				return false;
		} else if (!keyFrutaId.equals(other.getKeyFrutaId()))
			return false;
		if (keyClienteFornecedorId == null) {
			if (other.getKeyClienteFornecedorId() != null)
				return false;
		} else if (!keyClienteFornecedorId.equals(other.getKeyClienteFornecedorId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteFornecedorFrutaDTO {" 
				+ "\"clienteFornecedor\": {\"id\":\"" + keyClienteFornecedorId 
					+ "\", \"nome\":\"" + keyClienteFornecedorNome 
					+ "\", \"cpfCnpj\":\"" + keyClienteFornecedorCpfCnpj + "\"}, " 
				+ "\"fruta\": {\"id\":\"" + keyFrutaId 
					+ "\", \"nome\":\"" + keyFrutaNome 
					+ "\", \"sigla\":\"" + keyFrutaSigla 
					+ "\", \"safra\":\"" + keyFrutaSafra + "\"}" 
				+ "}";
	}
}

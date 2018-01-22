package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class ProdutoFrutaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer keyProdutoId;
	private String keyProdutoNome;
	private String keyProdutoUnidade;
	
	private Integer keyFrutaId;
	private String keyFrutaNome;
	private String keyFrutaSigla;
	private String keyFrutaSafra;
	
	public Integer getKeyProdutoId() {
		return keyProdutoId;
	}
	public void setKeyProdutoId(Integer keyProdutoId) {
		this.keyProdutoId = keyProdutoId;
	}
	public Integer getKeyFrutaId() {
		return keyFrutaId;
	}
	public void setKeyFrutaId(Integer keyFrutaId) {
		this.keyFrutaId = keyFrutaId;
	}
	
	public String getKeyProdutoNome() {
		return keyProdutoNome;
	}
	public void setKeyProdutoNome(String keyProdutoNome) {
		this.keyProdutoNome = keyProdutoNome;
	}
	public String getKeyProdutoUnidade() {
		return keyProdutoUnidade;
	}
	public void setKeyProdutoUnidade(String keyProdutoUnidade) {
		this.keyProdutoUnidade = keyProdutoUnidade;
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
		result = prime * result + ((keyProdutoId == null) ? 0 : keyProdutoId.hashCode());
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
		ProdutoFrutaDTO other = (ProdutoFrutaDTO) obj;
		if (keyFrutaId == null) {
			if (other.getKeyFrutaId() != null)
				return false;
		} else if (!keyFrutaId.equals(other.getKeyFrutaId()))
			return false;
		if (keyProdutoId == null) {
			if (other.getKeyProdutoId() != null)
				return false;
		} else if (!keyProdutoId.equals(other.getKeyProdutoId()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ProdutoFrutaDTO {" + "\"produto\": {\"id\":\"" + keyProdutoId 
							       + "\", \"nome\":\"" + keyProdutoNome 
							       + "\", \"unidade\":\"" + keyProdutoUnidade + "\"}, "
							    + "\"fruta\": {\"id\":\"" + keyFrutaId 
							       + "\", \"nome\":\"" + keyFrutaNome 
							       + "\", \"sigla\":\"" + keyFrutaSigla 
							       + "\", \"safra\":\"" + keyFrutaSafra + "\"}" 
							    + "}";
	}
}

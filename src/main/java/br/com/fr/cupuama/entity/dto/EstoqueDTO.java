package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class EstoqueDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String keyAnoMes;
	private Integer keyProdutoId;
	private Integer keyFrutaId;
	private Integer keyLocalEstoqueId;
	
	private Float qtSaldoAnterior;
	private Float qtEntradas;
	private Float qtSaidas;
	private Float qtSaldoAtual;

	public String getKeyAnoMes() {
		return keyAnoMes;
	}

	public void setKeyAnoMes(String keyAnoMes) {
		this.keyAnoMes = keyAnoMes;
	}

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

	public Integer getKeyLocalEstoqueId() {
		return keyLocalEstoqueId;
	}

	public void setKeyLocalEstoqueId(Integer keyLocalEstoqueId) {
		this.keyLocalEstoqueId = keyLocalEstoqueId;
	}

	public Float getQtSaldoAnterior() {
		return qtSaldoAnterior;
	}

	public void setQtSaldoAnterior(Float qtSaldoAnterior) {
		this.qtSaldoAnterior = qtSaldoAnterior;
	}

	public Float getQtEntradas() {
		return qtEntradas;
	}

	public void setQtEntradas(Float qtEntradas) {
		this.qtEntradas = qtEntradas;
	}

	public Float getQtSaidas() {
		return qtSaidas;
	}

	public void setQtSaidas(Float qtSaidas) {
		this.qtSaidas = qtSaidas;
	}

	public Float getQtSaldoAtual() {
		return qtSaldoAtual;
	}

	public void setQtSaldoAtual(Float qtSaldoAtual) {
		this.qtSaldoAtual = qtSaldoAtual;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyAnoMes == null) ? 0 : keyAnoMes.hashCode());
		result = prime * result + ((keyFrutaId == null) ? 0 : keyFrutaId.hashCode());
		result = prime * result + ((keyLocalEstoqueId == null) ? 0 : keyLocalEstoqueId.hashCode());
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
		EstoqueDTO other = (EstoqueDTO) obj;
		if (keyAnoMes == null) {
			if (other.getKeyAnoMes() != null)
				return false;
		} else if (!keyAnoMes.equals(other.getKeyAnoMes()))
			return false;
		if (keyFrutaId == null) {
			if (other.getKeyFrutaId() != null)
				return false;
		} else if (!keyFrutaId.equals(other.getKeyFrutaId()))
			return false;
		if (keyLocalEstoqueId == null) {
			if (other.getKeyLocalEstoqueId() != null)
				return false;
		} else if (!keyLocalEstoqueId.equals(other.getKeyLocalEstoqueId()))
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
		return "EstoqueDTO {"
				+ "\"keyAnoMes\":\"" + keyAnoMes + "\", "
				+ "\"keyProdutoId\":" + keyProdutoId + ", "
				+ "\"keyFrutaId\":" + keyFrutaId + ", "
				+ "\"keyLocalEstoqueId\":" + keyLocalEstoqueId  + ", "
				+ "\"qtSaldoAnterior\":" + qtSaldoAnterior + ", "
				+ "\"qtEntradas\":" + qtEntradas + ", "
				+ "\"qtSaidas\":" + qtSaidas + ", "
				+ "\"qtSaldoAtual\":" + qtSaldoAtual 
				+ "}";
	}

	
}

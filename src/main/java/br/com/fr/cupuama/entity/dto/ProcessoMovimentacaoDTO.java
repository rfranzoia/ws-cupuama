package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class ProcessoMovimentacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer keyTipoMovimentacaoId;
	private String keyTipoMovimentacaoNome;

	private Integer keyProdutoId;
	private String keyProdutoNome;
	private String keyProdutoUnidade;

	private Character keyTipoEntradaSaida;

	private Integer localEstoqueId;
	private String localEstoqueNome;
	private Character localEstoqueGuardaEstoque;

	public Integer getKeyTipoMovimentacaoId() {
		return keyTipoMovimentacaoId;
	}

	public void setKeyTipoMovimentacaoId(Integer keyTipoMovimentacaoId) {
		this.keyTipoMovimentacaoId = keyTipoMovimentacaoId;
	}

	public String getKeyTipoMovimentacaoNome() {
		return keyTipoMovimentacaoNome;
	}

	public void setKeyTipoMovimentacaoNome(String keyTipoMovimentacaoNome) {
		this.keyTipoMovimentacaoNome = keyTipoMovimentacaoNome;
	}

	public Integer getKeyProdutoId() {
		return keyProdutoId;
	}

	public void setKeyProdutoId(Integer keyProdutoId) {
		this.keyProdutoId = keyProdutoId;
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

	public Character getKeyTipoEntradaSaida() {
		return keyTipoEntradaSaida;
	}

	public void setKeyTipoEntradaSaida(Character keyTipoEntradaSaida) {
		this.keyTipoEntradaSaida = keyTipoEntradaSaida;
	}

	public Integer getLocalEstoqueId() {
		return localEstoqueId;
	}

	public void setLocalEstoqueId(Integer localEstoqueId) {
		this.localEstoqueId = localEstoqueId;
	}

	public String getLocalEstoqueNome() {
		return localEstoqueNome;
	}

	public void setLocalEstoqueNome(String localEstoqueNome) {
		this.localEstoqueNome = localEstoqueNome;
	}

	public Character getLocalEstoqueGuardaEstoque() {
		return localEstoqueGuardaEstoque;
	}

	public void setLocalEstoqueGuardaEstoque(Character localEstoqueGuardaEstoque) {
		this.localEstoqueGuardaEstoque = localEstoqueGuardaEstoque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyProdutoId == null) ? 0 : keyProdutoId.hashCode());
		result = prime * result + ((keyTipoEntradaSaida == null) ? 0 : keyTipoEntradaSaida.hashCode());
		result = prime * result + ((keyTipoMovimentacaoId == null) ? 0 : keyTipoMovimentacaoId.hashCode());
		result = prime * result + ((localEstoqueId == null) ? 0 : localEstoqueId.hashCode());
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
		ProcessoMovimentacaoDTO other = (ProcessoMovimentacaoDTO) obj;
		if (keyProdutoId == null) {
			if (other.getKeyProdutoId() != null)
				return false;
		} else if (!keyProdutoId.equals(other.getKeyProdutoId()))
			return false;
		if (keyTipoEntradaSaida == null) {
			if (other.getKeyTipoEntradaSaida() != null)
				return false;
		} else if (!keyTipoEntradaSaida.equals(other.getKeyTipoEntradaSaida()))
			return false;
		if (keyTipoMovimentacaoId == null) {
			if (other.getKeyTipoMovimentacaoId() != null)
				return false;
		} else if (!keyTipoMovimentacaoId.equals(other.getKeyTipoMovimentacaoId()))
			return false;
		if (localEstoqueId == null) {
			if (other.getLocalEstoqueId() != null)
				return false;
		} else if (!localEstoqueId.equals(other.getLocalEstoqueId()))
			return false;
		return true;
	}

	
}

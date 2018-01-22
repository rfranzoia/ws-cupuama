package br.com.fr.cupuama.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.fr.cupuama.entity.key.ProcessoMovimentacaoKey;

@Entity
@Table(name = "processo_movimentacao")
public class ProcessoMovimentacao  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private ProcessoMovimentacaoKey key;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "local_estoque_id", nullable = false)
	private LocalEstoque localEstoque;

	public ProcessoMovimentacaoKey getKey() {
		return key;
	}

	public void setKey(ProcessoMovimentacaoKey key) {
		this.key = key;
	}

	public LocalEstoque getLocalEstoque() {
		return localEstoque;
	}

	public void setLocalEstoque(LocalEstoque localEstoque) {
		this.localEstoque = localEstoque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((localEstoque == null) ? 0 : localEstoque.hashCode());
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
		ProcessoMovimentacao other = (ProcessoMovimentacao) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		if (localEstoque == null) {
			if (other.getLocalEstoque() != null)
				return false;
		} else if (!localEstoque.equals(other.getLocalEstoque()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessoMovimentacao [key=" + key + ", localEstoque=" + localEstoque + "]";
	}
	
}

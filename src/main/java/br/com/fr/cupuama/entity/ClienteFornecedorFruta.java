package br.com.fr.cupuama.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.fr.cupuama.entity.key.ClienteFornecedorFrutaKey;

@Entity
@Table(name="cliente_fornecedor_fruta")
public class ClienteFornecedorFruta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private ClienteFornecedorFrutaKey key;

	public ClienteFornecedorFrutaKey getKey() {
		return key;
	}

	public void setKey(ClienteFornecedorFrutaKey key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		ClienteFornecedorFruta other = (ClienteFornecedorFruta) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteFornecedorFruta [key=" + key.toString() + "]";
	}
	
	 
	
}

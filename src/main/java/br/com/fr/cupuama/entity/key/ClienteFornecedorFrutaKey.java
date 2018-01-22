package br.com.fr.cupuama.entity.key;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.ClienteFornecedor;

@Embeddable
public class ClienteFornecedorFrutaKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_fornecedor_id", nullable = false)
	private ClienteFornecedor clienteFornecedor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruta_id", nullable = false)
	private Fruta fruta;

	public ClienteFornecedor getClienteFornecedor() {
		return clienteFornecedor;
	}

	public void setClienteFornecedor(ClienteFornecedor clienteFornecedor) {
		this.clienteFornecedor = clienteFornecedor;
	}

	public Fruta getFruta() {
		return fruta;
	}

	public void setFruta(Fruta fruta) {
		this.fruta = fruta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fruta == null) ? 0 : fruta.hashCode());
		result = prime * result + ((clienteFornecedor == null) ? 0 : clienteFornecedor.hashCode());
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
		ClienteFornecedorFrutaKey other = (ClienteFornecedorFrutaKey) obj;
		if (fruta == null) {
			if (other.getFruta() != null)
				return false;
		} else if (!fruta.equals(other.getFruta()))
			return false;
		if (clienteFornecedor == null) {
			if (other.getClienteFornecedor() != null)
				return false;
		} else if (!clienteFornecedor.equals(other.getClienteFornecedor()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteFornecedorFrutaKey [clienteFornecedor=" + clienteFornecedor + ", fruta=" + fruta + "]";
	}
	
	

}

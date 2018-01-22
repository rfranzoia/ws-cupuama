package br.com.fr.cupuama.entity.key;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.Produto;

@Embeddable
public class ProdutoFrutaKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruta_id", nullable = false)
	private Fruta fruta;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ProdutoFrutaKey other = (ProdutoFrutaKey) obj;
		if (fruta == null) {
			if (other.getFruta() != null)
				return false;
		} else if (!fruta.equals(other.getFruta()))
			return false;
		if (produto == null) {
			if (other.getProduto() != null)
				return false;
		} else if (!produto.equals(other.getProduto()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoFrutaKey [produto=" + produto + ", fruta=" + fruta + "]";
	}
	
	

}

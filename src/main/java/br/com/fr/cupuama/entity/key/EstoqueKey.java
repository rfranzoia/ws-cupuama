package br.com.fr.cupuama.entity.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.LocalEstoque;
import br.com.fr.cupuama.entity.Produto;

@Embeddable
public class EstoqueKey implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "ano_mes")
	private String anoMes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruta_id", nullable = false)
	private Fruta fruta;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "local_estoque_id", nullable = false)
	private LocalEstoque localEstoque;

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

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
		result = prime * result + ((anoMes == null) ? 0 : anoMes.hashCode());
		result = prime * result + ((fruta == null) ? 0 : fruta.hashCode());
		result = prime * result + ((localEstoque == null) ? 0 : localEstoque.hashCode());
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
		EstoqueKey other = (EstoqueKey) obj;
		if (anoMes == null) {
			if (other.getAnoMes() != null)
				return false;
		} else if (!anoMes.equals(other.getAnoMes()))
			return false;
		if (fruta == null) {
			if (other.getFruta() != null)
				return false;
		} else if (!fruta.equals(other.getFruta()))
			return false;
		if (localEstoque == null) {
			if (other.getLocalEstoque() != null)
				return false;
		} else if (!localEstoque.equals(other.getLocalEstoque()))
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
		return "EstoqueKey [anoMes=" + anoMes + ", produto=" + produto + ", fruta=" + fruta + ", localEstoque="
				+ localEstoque + "]";
	}

}

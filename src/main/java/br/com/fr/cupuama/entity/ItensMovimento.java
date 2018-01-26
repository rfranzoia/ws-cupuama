package br.com.fr.cupuama.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItensMovimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "movimento_id")
	private Movimento movimento;

	@Column(name = "tipo_entrada_saida")
	private Character tipoEntradaSaida;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruta_id")
	private Fruta fruta;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "local_estoque_id")
	private LocalEstoque localEstoque;

	@Column(name = "qt_movimento", precision = 9, scale = 3)
	private Float qtMovimento;

	@Column(name = "vl_movimento", precision = 12, scale = 2)
	private Double vlMovimento;

	@Column(name = "vl_desconto", precision = 12, scale = 2)
	private Double vlDesconto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	public Character getTipoEntradaSaida() {
		return tipoEntradaSaida;
	}

	public void setTipoEntradaSaida(Character tipoEntradaSaida) {
		this.tipoEntradaSaida = tipoEntradaSaida;
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

	public Float getQtMovimento() {
		return qtMovimento;
	}

	public void setQtMovimento(Float qtMovimento) {
		this.qtMovimento = qtMovimento;
	}

	public Double getVlMovimento() {
		return vlMovimento;
	}

	public void setVlMovimento(Double vlMovimento) {
		this.vlMovimento = vlMovimento;
	}

	public Double getVlDesconto() {
		return vlDesconto;
	}

	public void setVlDesconto(Double vlDesconto) {
		this.vlDesconto = vlDesconto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItensMovimento other = (ItensMovimento) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItensMovimento [id=" + id + ", movimento=" + movimento + ", tipoEntradaSaida=" + tipoEntradaSaida
				+ ", produto=" + produto + ", fruta=" + fruta + ", localEstoque=" + localEstoque + ", qtMovimento="
				+ qtMovimento + ", vlMovimento=" + vlMovimento + ", vlDesconto=" + vlDesconto + "]";
	}

}

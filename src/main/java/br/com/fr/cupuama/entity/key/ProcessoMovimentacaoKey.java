package br.com.fr.cupuama.entity.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.TipoMovimentacao;

@Embeddable
public class ProcessoMovimentacaoKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_movimentacao_id", nullable = false)
	private TipoMovimentacao tipoMovimentacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;
	
	@Column(name = "tipo_entrada_saida")
	private Character tipoEntradaSaida;

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Character getTipoEntradaSaida() {
		return tipoEntradaSaida;
	}

	public void setTipoEntradaSaida(Character tipoEntradaSaida) {
		this.tipoEntradaSaida = tipoEntradaSaida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tipoEntradaSaida == null) ? 0 : tipoEntradaSaida.hashCode());
		result = prime * result + ((tipoMovimentacao == null) ? 0 : tipoMovimentacao.hashCode());
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
		ProcessoMovimentacaoKey other = (ProcessoMovimentacaoKey) obj;
		if (produto == null) {
			if (other.getProduto() != null)
				return false;
		} else if (!produto.equals(other.getProduto()))
			return false;
		if (tipoEntradaSaida == null) {
			if (other.tipoEntradaSaida != null)
				return false;
		} else if (!tipoEntradaSaida.equals(other.tipoEntradaSaida))
			return false;
		if (tipoMovimentacao == null) {
			if (other.getTipoMovimentacao() != null)
				return false;
		} else if (!tipoMovimentacao.equals(other.getTipoMovimentacao()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessoMovimentacaoKey [tipoMovimentacao=" + tipoMovimentacao + ", produto=" + produto
				+ ", tipoEntradaSaida=" + tipoEntradaSaida + "]";
	}

}

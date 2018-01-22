package br.com.fr.cupuama.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.fr.cupuama.entity.key.EstoqueKey;

@Entity
public class Estoque implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private EstoqueKey key;

	@Column(name = "qt_saldo_anterior", precision = 9, scale = 3)
	private Float qtSaldoAnterior;
	
	@Column(name = "qt_entradas", precision = 9, scale = 3)
	private Float qtEntradas;
	
	@Column(name = "qt_saidas", precision = 9, scale = 3)
	private Float qtSaidas;
	
	@Column(name = "qt_saldo_atual", precision = 9, scale = 3)
	private Float qtSaldoAtual;

	public EstoqueKey getKey() {
		return key;
	}

	public void setKey(EstoqueKey key) {
		this.key = key;
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
		Estoque other = (Estoque) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estoque [key=" + key + ", qtSaldoAnterior=" + qtSaldoAnterior + ", qtEntradas=" + qtEntradas
				+ ", qtSaidas=" + qtSaidas + ", qtSaldoAtual=" + qtSaldoAtual + "]";
	}
	
}

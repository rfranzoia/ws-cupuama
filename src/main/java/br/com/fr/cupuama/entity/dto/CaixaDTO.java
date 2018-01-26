package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class CaixaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String anoMes;

	private Double vlSaldoAnterior;
	private Double vlEntradas;
	private Double vlSaidas;
	private Double vlSaldoAtual;

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public Double getVlSaldoAnterior() {
		return vlSaldoAnterior;
	}

	public void setVlSaldoAnterior(Double vlSaldoAnterior) {
		this.vlSaldoAnterior = vlSaldoAnterior;
	}

	public Double getVlEntradas() {
		return vlEntradas;
	}

	public void setVlEntradas(Double vlEntradas) {
		this.vlEntradas = vlEntradas;
	}

	public Double getVlSaidas() {
		return vlSaidas;
	}

	public void setVlSaidas(Double vlSaidas) {
		this.vlSaidas = vlSaidas;
	}

	public Double getVlSaldoAtual() {
		return vlSaldoAtual;
	}

	public void setVlSaldoAtual(Double vlSaldoAtual) {
		this.vlSaldoAtual = vlSaldoAtual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anoMes == null) ? 0 : anoMes.hashCode());
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
		CaixaDTO other = (CaixaDTO) obj;
		if (anoMes == null) {
			if (other.getAnoMes() != null)
				return false;
		} else if (!anoMes.equals(other.getAnoMes()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CaixaDTO [keyAnoMes=" + anoMes + ", vlSaldoAnterior=" + vlSaldoAnterior + ", vlEntradas="
				+ vlEntradas + ", vlSaidas=" + vlSaidas + ", vlSaldoAtual=" + vlSaldoAtual + "]";
	}

}

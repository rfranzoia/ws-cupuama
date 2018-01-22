package br.com.fr.cupuama.util;

import java.io.Serializable;

public class OrderBy implements Serializable {

	private static final long serialVersionUID = 1L;

	private String campo;
	private String direcao;

	public OrderBy() {
	}

	public OrderBy(String campo, String direcao) {
		this.campo = campo;
		this.direcao = direcao;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getDirecao() {
		return direcao;
	}

	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}

}
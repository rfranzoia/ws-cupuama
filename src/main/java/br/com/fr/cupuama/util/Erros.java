package br.com.fr.cupuama.util;

public enum Erros {
	REGISTRO_NAO_CADASTRADO("O registro solicitado não foi encontrado!", 1);

	private String mensagem;
	private Integer codErro;

	private Erros(String mensagem, Integer codErro) {
		this.mensagem = mensagem;
		this.codErro = codErro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Integer getCodErro() {
		return codErro;
	}

}
package br.com.fr.cupuama.exception;

public class ProdutoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProdutoException() {
	}

	public ProdutoException(String message) {
		super(message);
	}

	public ProdutoException(Throwable cause) {
		super(cause);
	}

	public ProdutoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProdutoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

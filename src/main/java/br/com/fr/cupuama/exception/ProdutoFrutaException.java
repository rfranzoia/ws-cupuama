package br.com.fr.cupuama.exception;

public class ProdutoFrutaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProdutoFrutaException() {
	}

	public ProdutoFrutaException(String message) {
		super(message);
	}

	public ProdutoFrutaException(Throwable cause) {
		super(cause);
	}

	public ProdutoFrutaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProdutoFrutaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

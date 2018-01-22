package br.com.fr.cupuama.exception;

public class LocalEstoqueException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocalEstoqueException() {
	}

	public LocalEstoqueException(String message) {
		super(message);
	}

	public LocalEstoqueException(Throwable cause) {
		super(cause);
	}

	public LocalEstoqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocalEstoqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

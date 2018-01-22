package br.com.fr.cupuama.exception;

public class EstoqueException extends Exception {

	private static final long serialVersionUID = 1L;

	public EstoqueException() {
	}

	public EstoqueException(String message) {
		super(message);
	}

	public EstoqueException(Throwable cause) {
		super(cause);
	}

	public EstoqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public EstoqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

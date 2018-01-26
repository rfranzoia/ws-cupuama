package br.com.fr.cupuama.exception;

public class CaixaException extends Exception {

	private static final long serialVersionUID = 1L;

	public CaixaException() {
	}

	public CaixaException(String message) {
		super(message);
	}

	public CaixaException(Throwable cause) {
		super(cause);
	}

	public CaixaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaixaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

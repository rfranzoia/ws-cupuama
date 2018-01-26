package br.com.fr.cupuama.exception;

public class MovimentoCaixaException extends Exception {

	private static final long serialVersionUID = 1L;

	public MovimentoCaixaException() {
	}

	public MovimentoCaixaException(String message) {
		super(message);
	}

	public MovimentoCaixaException(Throwable cause) {
		super(cause);
	}

	public MovimentoCaixaException(String message, Throwable cause) {
		super(message, cause);
	}

	public MovimentoCaixaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

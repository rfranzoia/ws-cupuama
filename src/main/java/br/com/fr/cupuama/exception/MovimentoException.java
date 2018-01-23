package br.com.fr.cupuama.exception;

public class MovimentoException extends Exception {

	private static final long serialVersionUID = 1L;

	public MovimentoException() {
	}

	public MovimentoException(String message) {
		super(message);
	}

	public MovimentoException(Throwable cause) {
		super(cause);
	}

	public MovimentoException(String message, Throwable cause) {
		super(message, cause);
	}

	public MovimentoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

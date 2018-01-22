package br.com.fr.cupuama.exception;

public class FrutaException extends Exception {

	private static final long serialVersionUID = 1L;

	public FrutaException() {
	}

	public FrutaException(String message) {
		super(message);
	}

	public FrutaException(Throwable cause) {
		super(cause);
	}

	public FrutaException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrutaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

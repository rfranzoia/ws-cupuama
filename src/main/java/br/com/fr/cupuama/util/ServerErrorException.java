package br.com.fr.cupuama.util;

public class ServerErrorException extends Exception {

	private static final long serialVersionUID = 933687309657303040L;

	public ServerErrorException() {
		super();
	}

	public ServerErrorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerErrorException(String message) {
		super(message);
	}

	public ServerErrorException(Throwable cause) {
		super(cause);
	}

}

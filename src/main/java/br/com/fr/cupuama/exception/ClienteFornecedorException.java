package br.com.fr.cupuama.exception;

public class ClienteFornecedorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteFornecedorException() {
	}

	public ClienteFornecedorException(String message) {
		super(message);
	}

	public ClienteFornecedorException(Throwable cause) {
		super(cause);
	}

	public ClienteFornecedorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClienteFornecedorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package br.com.fr.cupuama.exception;

public class ClienteFornecedorFrutaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteFornecedorFrutaException() {
	}

	public ClienteFornecedorFrutaException(String message) {
		super(message);
	}

	public ClienteFornecedorFrutaException(Throwable cause) {
		super(cause);
	}

	public ClienteFornecedorFrutaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClienteFornecedorFrutaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

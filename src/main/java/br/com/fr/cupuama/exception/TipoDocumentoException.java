package br.com.fr.cupuama.exception;

public class TipoDocumentoException extends Exception {

	private static final long serialVersionUID = 1L;

	public TipoDocumentoException() {
	}

	public TipoDocumentoException(String message) {
		super(message);
	}

	public TipoDocumentoException(Throwable cause) {
		super(cause);
	}

	public TipoDocumentoException(String message, Throwable cause) {
		super(message, cause);
	}

	public TipoDocumentoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

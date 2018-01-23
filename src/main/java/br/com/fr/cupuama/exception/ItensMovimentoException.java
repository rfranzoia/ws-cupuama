package br.com.fr.cupuama.exception;

public class ItensMovimentoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ItensMovimentoException() {
	}

	public ItensMovimentoException(String message) {
		super(message);
	}

	public ItensMovimentoException(Throwable cause) {
		super(cause);
	}

	public ItensMovimentoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItensMovimentoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package br.com.fr.cupuama.exception;

public class TipoMovimentacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public TipoMovimentacaoException() {
	}

	public TipoMovimentacaoException(String message) {
		super(message);
	}

	public TipoMovimentacaoException(Throwable cause) {
		super(cause);
	}

	public TipoMovimentacaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public TipoMovimentacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

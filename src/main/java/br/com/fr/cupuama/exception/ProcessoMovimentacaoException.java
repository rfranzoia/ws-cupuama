package br.com.fr.cupuama.exception;

public class ProcessoMovimentacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcessoMovimentacaoException() {
	}

	public ProcessoMovimentacaoException(String message) {
		super(message);
	}

	public ProcessoMovimentacaoException(Throwable cause) {
		super(cause);
	}

	public ProcessoMovimentacaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessoMovimentacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

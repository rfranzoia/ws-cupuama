package br.com.cupuama.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ConstraintsViolationException extends Exception {

	static final long serialVersionUID = -3387516993224229948L;

	public ConstraintsViolationException(String message) {
		super(message);
	}

	public ConstraintsViolationException() {
		super();
	}

	public ConstraintsViolationException(String s, Throwable t) {
		super(s, t);
	}

	public ConstraintsViolationException(Throwable t) {
		super(t);
	}
	
	

}

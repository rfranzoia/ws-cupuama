package br.com.fr.cupuama.util;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtil {

	private static final ValidatorFactory factory;

	static {
		factory = Validation.buildDefaultValidatorFactory();

	}

	public static void validate(Object... entities) {
		Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
		Validator validator = factory.getValidator();
		for (Object entity : entities) {

			try {
				violations.addAll(validator.validate(entity));
			} catch (UnexpectedTypeException cause) {
				// TODO Colocar a mensagem no log
			}
		}

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

	}

}
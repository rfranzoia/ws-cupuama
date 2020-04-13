package br.com.cupuama.util;

@FunctionalInterface
public interface ThrowingConsumer<I, T extends Throwable> {
	/**
	 * Performs this operation on the given argument.
	 *
	 * @param in the input argument
	 */
	void accept(I in) throws T;
}
package br.com.cupuama.enums;

import br.com.cupuama.domain.processing.Processing;
import br.com.cupuama.exception.InvalidRequestException;

import java.util.function.Consumer;

public enum ProcessStatus {
	Created, Approved, Rejected, Processing, Paid, Completed, Canceled;

	public void changeStatus(Processing processing, Consumer<Processing> action) throws InvalidRequestException{
		// Processing cancellation is only allowed if current processing is in one of Created, Approved or Rejected statuses
		if (this == Canceled) {
			switch (processing.getProcessStatus()) {
				case Paid:
				case Completed:
					throw new InvalidRequestException(String.format("Process cancellation is not permitted anymore!"));
			}
		}
		processing.setProcessStatus(this);
		action.accept(processing);
	}
}

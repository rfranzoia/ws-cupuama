package br.com.cupuama.domain.cashflow.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashFlowDTO {

	@NotNull(message = "Period cannot be null!")
	private String period;

	@NotNull(message = "Previous Balance cannot be null!")
	private Double previousBalance;

	private Double credits;
	private Double debits;
	
	@JsonIgnore
	private Double currentBalance;

	private CashFlowDTO() {
	}

	private CashFlowDTO(String period, Double previousBalance, Double credits, Double debits) {
		this.period = period;
		this.previousBalance = previousBalance;
		this.credits = credits;
		this.debits = debits;
		this.currentBalance = previousBalance + credits - debits;
	}

	public static CashFlowDTOBuilder newBuilder() {
		return new CashFlowDTOBuilder();
	}

	@JsonProperty
	public String getPeriod() {
		return period;
	}

	@JsonProperty
	public Double getPreviousBalance() {
		return previousBalance;
	}

	@JsonProperty
	public Double getCredits() {
		return credits;
	}

	@JsonProperty
	public Double getDebits() {
		return debits;
	}
	
	@JsonProperty
	public Double getCurrentBalance() {
		return currentBalance;
	}

	public static class CashFlowDTOBuilder {
		private String period;
		private Double previousBalance = 0.0;
		private Double credits = 0.0;
		private Double debits = 0.0;

		public CashFlowDTOBuilder setPeriod(String period) {
			this.period = period;
			return this;
		}

		public CashFlowDTOBuilder setPreviousBalance(Double previousBalance) {
			this.previousBalance = previousBalance;
			return this;
		}

		public CashFlowDTOBuilder setCredits(Double credits) {
			this.credits = credits;
			return this;
		}

		public CashFlowDTOBuilder setDebits(Double debits) {
			this.debits = debits;
			return this;
		}
		
		public CashFlowDTO createDTO() {
			return new CashFlowDTO(period, previousBalance, credits, debits);
		}
	}
}

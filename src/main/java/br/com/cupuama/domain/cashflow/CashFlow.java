package br.com.cupuama.domain.cashflow;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "cashflow")
public class CashFlow {

	@Id
	private String period;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double previousBalance;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double credits;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double debits;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	private Boolean deleted = false;

	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateUpdated = ZonedDateTime.now();

	public CashFlow() {
	}

	public CashFlow(String period, Double previousBalance, Double credits, Double debits) {
		this.period = period;
		this.previousBalance = previousBalance;
		this.credits = credits;
		this.debits = debits;
		this.deleted = false;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Double getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(Double previousBalance) {
		this.previousBalance = previousBalance;
	}

	public Double getCredits() {
		return credits;
	}

	public void setCredits(Double credits) {
		this.credits = credits;
	}

	public Double getDebits() {
		return debits;
	}

	public void setDebits(Double debits) {
		this.debits = debits;
	}

	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public ZonedDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(ZonedDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Override
	public String toString() {
		return "CashFlow [period=" + period + ", dateCreated=" + dateCreated + ", deleted=" + deleted + ", dateUpdated="
				+ dateUpdated + "]";
	}
	
	

}

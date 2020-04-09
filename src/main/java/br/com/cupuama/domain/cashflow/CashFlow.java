package br.com.cupuama.domain.cashflow;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cupuama.domain.Audit;
import br.com.cupuama.domain.AuditableEntity;

@Entity
@Table(name = "cashflow")
public class CashFlow implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private String period;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double previousBalance;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double credits;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double debits;

	@Embedded
	private Audit audit;

	public CashFlow() {
	}

	public CashFlow(String period, Double previousBalance, Double credits, Double debits) {
		this.period = period;
		this.previousBalance = previousBalance;
		this.credits = credits;
		this.debits = debits;
		this.audit = new Audit();
		this.audit.setDeleted(false);
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

	@Override
	public Audit getAudit() {
		return audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	@Override
	public String toString() {
		return "CashFlow [period=" + period + ", audit=" + audit + "]";
	}

}

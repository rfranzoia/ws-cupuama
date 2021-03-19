package br.com.cupuama.domain.processing;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.cupuama.domain.products.Products;
import br.com.cupuama.enums.StocktakeInOut;

@Embeddable
public class ProcessFlowTypeId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "process_type_id", nullable = false)
	private ProcessType processType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Products products;

	@Column(name = "stocktake_in_out")
	@Enumerated(EnumType.STRING)
	private StocktakeInOut stocktakeInOut;

	public ProcessFlowTypeId() {
	}

	public ProcessFlowTypeId(ProcessType processType, Products products, StocktakeInOut stocktakeInOut) {
		this.processType = processType;
		this.products = products;
		this.stocktakeInOut = stocktakeInOut;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType stocktakeType) {
		this.processType = stocktakeType;
	}

	public Products getProduct() {
		return products;
	}

	public void setProduct(Products products) {
		this.products = products;
	}

	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	public void setStocktakeInOut(StocktakeInOut stocktakeInOut) {
		this.stocktakeInOut = stocktakeInOut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((stocktakeInOut == null) ? 0 : stocktakeInOut.hashCode());
		result = prime * result + ((processType == null) ? 0 : processType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessFlowTypeId other = (ProcessFlowTypeId) obj;
		if (products == null) {
			if (other.getProduct() != null)
				return false;
		} else if (!products.equals(other.getProduct()))
			return false;
		if (stocktakeInOut != other.getStocktakeInOut())
			return false;
		if (processType == null) {
			if (other.getProcessType() != null)
				return false;
		} else if (!processType.equals(other.getProcessType()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessFlowTypeKey [processType=" + processType 
				+ ", product=" + products
				+ ", stocktakeInOut=" + stocktakeInOut + "]";
	}

}

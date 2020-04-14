package br.com.cupuama.domain.processing;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.cupuama.domain.products.Product;
import br.com.cupuama.enums.StocktakeInOut;

@Embeddable
public class ProcessFlowTypeId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "process_type_id", nullable = false)
	private ProcessType processType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "stocktake_in_out")
	@Enumerated(EnumType.STRING)
	private StocktakeInOut stocktakeInOut;

	public ProcessFlowTypeId() {
	}

	public ProcessFlowTypeId(ProcessType processType, Product product, StocktakeInOut stocktakeInOut) {
		this.processType = processType;
		this.product = product;
		this.stocktakeInOut = stocktakeInOut;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType stocktakeType) {
		this.processType = stocktakeType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		if (product == null) {
			if (other.getProduct() != null)
				return false;
		} else if (!product.equals(other.getProduct()))
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
				+ ", product=" + product 
				+ ", stocktakeInOut=" + stocktakeInOut + "]";
	}

}

package br.com.cupuama.controller.processing.dto;

import java.io.Serializable;

import br.com.cupuama.controller.products.dto.ProductDTO;
import br.com.cupuama.enums.StocktakeInOut;

public class ProcessFlowTypeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProcessTypeDTO processType;
	private ProductDTO product;
	private StocktakeInOut stocktakeInOut;

	public ProcessFlowTypeKey() {
	}

	public ProcessFlowTypeKey(ProcessTypeDTO processType, ProductDTO product, StocktakeInOut stocktakeInOut) {
		this.processType = processType;
		this.product = product;
		this.stocktakeInOut = stocktakeInOut;
	}

	public ProcessTypeDTO getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessTypeDTO processType) {
		this.processType = processType;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	public void setStocktakeInOut(StocktakeInOut stocktakeInOut) {
		this.stocktakeInOut = stocktakeInOut;
	}

}

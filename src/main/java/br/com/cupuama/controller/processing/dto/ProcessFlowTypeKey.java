package br.com.cupuama.controller.processing.dto;

import java.io.Serializable;

import br.com.cupuama.controller.products.dto.ProductsDTO;
import br.com.cupuama.enums.StocktakeInOut;

public class ProcessFlowTypeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProcessTypeDTO processType;
	private ProductsDTO product;
	private StocktakeInOut stocktakeInOut;

	public ProcessFlowTypeKey() {
	}

	public ProcessFlowTypeKey(ProcessTypeDTO processType, ProductsDTO product, StocktakeInOut stocktakeInOut) {
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

	public ProductsDTO getProduct() {
		return product;
	}

	public void setProduct(ProductsDTO product) {
		this.product = product;
	}

	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	public void setStocktakeInOut(StocktakeInOut stocktakeInOut) {
		this.stocktakeInOut = stocktakeInOut;
	}

}

package br.com.cupuama.domain.processing.entity;

import br.com.cupuama.domain.products.entity.ProductFruit;
import br.com.cupuama.domain.stock.entity.Depot;
import br.com.cupuama.domain.stock.entity.StocktakeInOut;

public class ProcessDetail {

	private Long id;
	
	private Process process;
	
	private ProductFruit productFruit;
	
	private Depot depot;
	
	private StocktakeInOut stocktakeInOut;
	
	private Double amount;
	
	private Double price;
	
	private Double discount;
	
}

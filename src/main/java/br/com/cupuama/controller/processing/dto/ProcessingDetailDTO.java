package br.com.cupuama.controller.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.controller.products.dto.FruitsDTO;
import br.com.cupuama.controller.products.dto.ProductsDTO;
import br.com.cupuama.controller.stock.dto.DepotDTO;
import br.com.cupuama.enums.StocktakeInOut;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessingDetailDTO {
	
	private Long id;

	@NotNull(message = "Processing cannot be null!")
	private ProcessingDTO processing;
	
	@NotNull(message = "Product cannot be null!")
	private ProductsDTO product;
	
	@NotNull(message = "Fruit cannot be null!")
	private FruitsDTO fruit;
	
	@NotNull(message = "Depot cannot be null!")
	private DepotDTO depot;
	
	@NotNull(message = "StocktakeInOut cannot be null!")
	private StocktakeInOut stocktakeInOut;
	
	@NotNull(message = "Amount cannot be null!")
	private Double amount;
	
	@NotNull(message = "Price cannot be null!")
	private Double price;
	
	private Double discount;
	
	public ProcessingDetailDTO(Long id, ProcessingDTO processing, ProductsDTO product, FruitsDTO fruit, DepotDTO depot,
							   StocktakeInOut stocktakeInOut, Double amount, Double price, Double discount) {
		this.id = id;
		this.processing = processing;
		this.product = product;
		this.fruit = fruit;
		this.depot = depot;
		this.stocktakeInOut = stocktakeInOut;
		this.amount = amount;
		this.price = price;
		this.discount = discount;
	}

	@JsonProperty
	public Long getId() {
		return id;
	}
	
	@JsonProperty
	public ProcessingDTO getProcessing() {
		return processing;
	}

	@JsonProperty
	public ProductsDTO getProduct() {
		return product;
	}

	@JsonProperty
	public FruitsDTO getFruit() {
		return fruit;
	}
	
	@JsonProperty
	public DepotDTO getDepot() {
		return depot;
	}

	@JsonProperty
	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	@JsonProperty
	public Double getAmount() {
		return amount;
	}

	@JsonProperty
	public Double getPrice() {
		return price;
	}

	@JsonProperty
	public Double getDiscount() {
		return discount;
	}

	public static ProcessingDetailDTOBuilder newBuilder() {
		return new ProcessingDetailDTOBuilder();
	}
	
	public static class ProcessingDetailDTOBuilder {
		private Long id;
		private ProcessingDTO processing;
		private ProductsDTO product;
		private FruitsDTO fruit;
		private DepotDTO depot;
		private StocktakeInOut stocktakeInOut;
		private Double amount;
		private Double price;
		private Double discount;

		public ProcessingDetailDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public ProcessingDetailDTOBuilder setProcessing(ProcessingDTO processing) {
			this.processing = processing;
			return this;
		}

		public ProcessingDetailDTOBuilder setProduct(ProductsDTO product) {
			this.product = product;
			return this;
		}

		public ProcessingDetailDTOBuilder setFruit(FruitsDTO fruit) {
			this.fruit = fruit;
			return this;
		}

		public ProcessingDetailDTOBuilder setDepot(DepotDTO depot) {
			this.depot = depot;
			return this;
		}

		public ProcessingDetailDTOBuilder setStocktakeInOut(StocktakeInOut stocktakeInOut) {
			this.stocktakeInOut = stocktakeInOut;
			return this;
		}

		public ProcessingDetailDTOBuilder setAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public ProcessingDetailDTOBuilder setPrice(Double price) {
			this.price = price;
			return this;
		}

		public ProcessingDetailDTOBuilder setDiscount(Double discount) {
			this.discount = discount;
			return this;
		}

		public ProcessingDetailDTO createDTO() {
			return new ProcessingDetailDTO(id, processing, product, fruit, depot, stocktakeInOut, amount, price, discount);
		}
	}
}

package br.com.cupuama.domain.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.products.dto.FruitDTO;
import br.com.cupuama.domain.products.dto.ProductDTO;
import br.com.cupuama.domain.stock.dto.DepotDTO;
import br.com.cupuama.domain.stock.entity.StocktakeInOut;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDetailDTO {
	
	private Long id;

	@NotNull(message = "Product cannot be null!")
	private ProcessDTO process;
	
	@NotNull(message = "Product cannot be null!")
	private ProductDTO product;
	
	@NotNull(message = "Fruit cannot be null!")
	private FruitDTO fruit;
	
	@NotNull(message = "Depot cannot be null!")
	private DepotDTO depot;
	
	@NotNull(message = "StocktakeInOut cannot be null!")
	private StocktakeInOut stocktakeInOut;
	
	@NotNull(message = "Amount cannot be null!")
	private Double amount;
	
	@NotNull(message = "Price cannot be null!")
	private Double price;
	
	private Double discount;
	
	public ProcessDetailDTO(Long id, ProcessDTO process, ProductDTO product, FruitDTO fruit, DepotDTO depot, 
								StocktakeInOut stocktakeInOut, Double amount, Double price, Double discount) {
		this.id = id;
		this.process = process;
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
	public ProductDTO getProduct() {
		return product;
	}

	@JsonProperty
	public FruitDTO getFruit() {
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

	public static ProcessDetailDTOBuilder newBuilder() {
		return new ProcessDetailDTOBuilder();
	}
	
	public static class ProcessDetailDTOBuilder {
		private Long id;
		private ProcessDTO process;
		private ProductDTO product;
		private FruitDTO fruit;
		private DepotDTO depot;
		private StocktakeInOut stocktakeInOut;
		private Double amount;
		private Double price;
		private Double discount;

		public ProcessDetailDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public ProcessDetailDTOBuilder setProcess(ProcessDTO process) {
			this.process = process;
			return this;
		}

		public ProcessDetailDTOBuilder setProduct(ProductDTO product) {
			this.product = product;
			return this;
		}

		public ProcessDetailDTOBuilder setFruit(FruitDTO fruit) {
			this.fruit = fruit;
			return this;
		}

		public ProcessDetailDTOBuilder setDepot(DepotDTO depot) {
			this.depot = depot;
			return this;
		}

		public ProcessDetailDTOBuilder setStocktakeInOut(StocktakeInOut stocktakeInOut) {
			this.stocktakeInOut = stocktakeInOut;
			return this;
		}

		public ProcessDetailDTOBuilder setAmount(Double amount) {
			this.amount = amount;
			return this;
		}

		public ProcessDetailDTOBuilder setPrice(Double price) {
			this.price = price;
			return this;
		}

		public ProcessDetailDTOBuilder setDiscount(Double discount) {
			this.discount = discount;
			return this;
		}

		public ProcessDetailDTO createProcessDetailDTO() {
			return new ProcessDetailDTO(id, process, product, fruit, depot, stocktakeInOut, amount, price, discount);
		}
	}
}

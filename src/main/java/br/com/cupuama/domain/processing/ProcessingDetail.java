package br.com.cupuama.domain.processing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cupuama.domain.products.Fruits;
import br.com.cupuama.domain.products.Products;
import br.com.cupuama.domain.stock.Depot;
import br.com.cupuama.enums.StocktakeInOut;
import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "processing_detail")
public class ProcessingDetail extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "processing_id", nullable = false)
	private Processing processing;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Products products;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruit_id", nullable = false)
	private Fruits fruits;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id", nullable = false)
	private Depot depot;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StocktakeInOut stocktakeInOut;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private Double price;

	@Column
	private Double discount;

	public ProcessingDetail() {
	}
	
	public ProcessingDetail(Long id, Processing process, Products products, Fruits fruits, Depot depot,
							StocktakeInOut stocktakeInOut, Double amount, Double price, Double discount) {
		this.id = id;
		this.processing = process;
		this.products = products;
		this.fruits = fruits;
		this.depot = depot;
		this.stocktakeInOut = stocktakeInOut;
		this.amount = amount;
		this.price = price;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Processing getProcessing() {
		return processing;
	}

	public void setProcess(Processing processing) {
		this.processing = processing;
	}

	public Products getProduct() {
		return products;
	}

	public void setProduct(Products products) {
		this.products = products;
	}

	public Fruits getFruit() {
		return fruits;
	}

	public void setFruit(Fruits fruits) {
		this.fruits = fruits;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	public void setStocktakeInOut(StocktakeInOut stocktakeInOut) {
		this.stocktakeInOut = stocktakeInOut;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProcessingDetail other = (ProcessingDetail) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessDetail [id=" + id + ", process=" + processing + ", product=" + products + ", fruit=" + fruits
				+ ", depot=" + depot + ", stocktakeInOut=" + stocktakeInOut + ", amount=" + amount + ", price=" + price
				+ ", discount=" + discount + "]";
	}

}

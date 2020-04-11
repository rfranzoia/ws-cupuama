package br.com.cupuama.domain.processing.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cupuama.domain.products.entity.ProductFruit;
import br.com.cupuama.domain.stock.entity.Depot;
import br.com.cupuama.domain.stock.entity.StocktakeInOut;
import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "process_detail")
public class ProcessDetail implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Process process;

	@Column(nullable = false)
	private ProductFruit productFruit;

	@Column(nullable = false)
	private Depot depot;

	@Column(nullable = false)
	private StocktakeInOut stocktakeInOut;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private Double price;

	@Column
	private Double discount;

	@Embedded
	private Audit audit = new Audit();

	public ProcessDetail() {
	}
	
	public ProcessDetail(Long id, Process process, ProductFruit productFruit, Depot depot,
			StocktakeInOut stocktakeInOut, Double amount, Double price, Double discount) {
		this.id = id;
		this.process = process;
		this.productFruit = productFruit;
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

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public ProductFruit getProductFruit() {
		return productFruit;
	}

	public void setProductFruit(ProductFruit productFruit) {
		this.productFruit = productFruit;
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
	public Audit getAudit() {
		return audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

}

package br.com.cupuama.domain.processing.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import br.com.cupuama.domain.products.entity.Fruit;
import br.com.cupuama.domain.products.entity.Product;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "process_id", nullable = false)
	private Process process;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruit_id", nullable = false)
	private Fruit fruit;

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

	@Embedded
	private Audit audit = new Audit();

	public ProcessDetail() {
	}
	
	public ProcessDetail(Long id, Process process, Product product, Fruit fruit, Depot depot,
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
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
		ProcessDetail other = (ProcessDetail) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessDetail [id=" + id + ", process=" + process + ", product=" + product + ", fruit=" + fruit
				+ ", depot=" + depot + ", stocktakeInOut=" + stocktakeInOut + ", amount=" + amount + ", price=" + price
				+ ", discount=" + discount + "]";
	}

}

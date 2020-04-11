package br.com.cupuama.domain.stock.entity;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cupuama.domain.products.entity.ProductFruitKey;
import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "stocktake")
public class Stocktake implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private ProductFruitKey productFruitKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id", nullable = false)
	private Depot depot;

	@Column(nullable = false, name = "stocktake_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date stocktakeDate;

	@Column(nullable = false, name = "stocktake_inout")
	@Enumerated(EnumType.STRING)
	private StocktakeInOut stocktakeInOut;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double amount;
	
	@Embedded
	private Audit audit;

	public Stocktake() {
	}

	public Stocktake(Long id, ProductFruitKey productFruitKey, Depot depot, Date stocktakeDate, StocktakeInOut stocktakeInOut,
			Double amount) {
		this.id = id;
		this.productFruitKey = productFruitKey;
		this.depot = depot;
		this.stocktakeDate = stocktakeDate;
		this.stocktakeInOut = stocktakeInOut;
		this.amount = amount;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductFruitKey getProductFruitKey() {
		return productFruitKey;
	}

	public void setProductFruitKey(ProductFruitKey productFruitKey) {
		this.productFruitKey = productFruitKey;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public Date getStocktakeDate() {
		return stocktakeDate;
	}

	public void setStocktakeDate(Date stocktakeDate) {
		this.stocktakeDate = stocktakeDate;
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

	@Override
	public Audit getAudit() {
		return this.audit;
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
		Stocktake other = (Stocktake) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stocktake [id=" + id 
				+ ", productFruitKey=" + productFruitKey.toString() 
				+ ", depot=" + depot.toString() 
				+ ", stocktakeDate=" + stocktakeDate 
				+ ", stocktakeInOut=" + stocktakeInOut 
				+ ", amount=" + amount + "]";
	}

}

package br.com.cupuama.domain.stock;

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

import br.com.cupuama.domain.products.ProductFruitId;
import br.com.cupuama.enums.StocktakeInOut;
import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "stocktake")
public class Stocktake extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private ProductFruitId productFruitId;

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
	
	public Stocktake() {
	}

	public Stocktake(Long id, ProductFruitId productFruitId, Depot depot, Date stocktakeDate, StocktakeInOut stocktakeInOut,
			Double amount) {
		this.id = id;
		this.productFruitId = productFruitId;
		this.depot = depot;
		this.stocktakeDate = stocktakeDate;
		this.stocktakeInOut = stocktakeInOut;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductFruitId getProductFruitId() {
		return productFruitId;
	}

	public void setProductFruitId(ProductFruitId productFruitId) {
		this.productFruitId = productFruitId;
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
				+ ", productFruitKey=" + productFruitId.toString() 
				+ ", depot=" + depot.toString() 
				+ ", stocktakeDate=" + stocktakeDate 
				+ ", stocktakeInOut=" + stocktakeInOut 
				+ ", amount=" + amount + "]";
	}

}

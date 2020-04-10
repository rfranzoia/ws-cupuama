package br.com.cupuama.domain.stock;

import java.util.Date;

import javax.persistence.Embedded;

import org.springframework.data.annotation.Id;

import br.com.cupuama.domain.Audit;
import br.com.cupuama.domain.AuditableEntity;
import br.com.cupuama.domain.products.ProductFruitKey;

public class Stocktake implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Embedded
	private ProductFruitKey productFruitKey;
	
	private Depot depot;
	
	private Date stocktakeDate;
	
	private StocktakeType type;
	
	private Double amount;
	
	@Embedded
	private Audit audit;

	@Override
	public Audit getAudit() {
		return this.audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
}

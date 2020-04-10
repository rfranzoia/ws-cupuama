package br.com.cupuama.domain.stock;

import javax.persistence.Embedded;

import org.springframework.data.annotation.Id;

import br.com.cupuama.domain.DefaultEntity;

public class Inventory implements DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Embedded
	private InventoryKey inventoryKey;
	
	private Double initialStock;
	private Double stockIn;
	private Double stockOut;
	
}

package br.com.cupuama.domain.stock;

import java.io.Serializable;

import javax.persistence.Embeddable;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.domain.products.Product;

@Embeddable
public class InventoryKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String period;
	private Product product;
	private Fruit fruit;
	private Depot depot;
	
}

package br.com.cupuama.service.products;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.domain.products.Product;
import br.com.cupuama.domain.products.ProductFruit;
import br.com.cupuama.domain.products.ProductFruitKey;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.repository.ProductFruitRepository;
import br.com.cupuama.service.DefaultService;

public class ProductFruitService extends DefaultService<ProductFruit, ProductFruitKey> {

	private static final Logger LOG = LoggerFactory.getLogger(ProductFruitService.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FruitService fruitService;
	
    public ProductFruitService(CrudRepository<ProductFruit, ProductFruitKey> repository) {
		super(repository);
	}

    /**
     * deletes all association of fruits with the productId 
     * 
     * @param productId
     * @throws EntityNotFoundException
     */
    @Transactional
	public void deleteByProductId(Long productId) throws EntityNotFoundException {
		List<ProductFruit> list = ((ProductFruitRepository) repository).findByProductId(productId);
		if (list.isEmpty()) {
			throw new EntityNotFoundException(String.format("No fruits associated with the product id %d were found!", productId));
		}
		list.stream().forEach(pf -> {
			repository.delete(pf);
		});
	};
    
	/**
     * deletes all association of products with the fruitId 
     * 
     * @param fruitId
     * @throws EntityNotFoundException
     */
    @Transactional
	public void deleteByFruitId(Long fruitId) throws EntityNotFoundException {
		List<ProductFruit> list = ((ProductFruitRepository) repository).findByFruitId(fruitId);
		if (list.isEmpty()) {
			throw new EntityNotFoundException(String.format("No products associated with the fruit id %d were found!", fruitId));
		}
		list.stream().forEach(pf -> {
			repository.delete(pf);
		});
	};

	/**
	 * list all fruits associated to the product id
	 * 
	 * @param productId
	 * @return List<Fruit>
	 */
    public List<Fruit> findByProductId(Long productId) {
    	List<ProductFruit> list = ((ProductFruitRepository) repository).findByProductId(productId);
    	List<Fruit> fruits = new ArrayList<>();
    	list.stream().forEach(pf -> fruits.add(pf.getKey().getFruit()));
    	return fruits;
    }
    
    /**
     * list all products associated to the fruit id
     * 
     * @param fruitId
     * @return List<Product>
     */
    public List<Product> findByFruitId(Long fruitId) {
    	List<ProductFruit> list = ((ProductFruitRepository) repository).findByFruitId(fruitId);
    	List<Product> products = new ArrayList<>();
    	list.stream().forEach(pf -> products.add(pf.getKey().getProduct()));
    	return products;
    }
    
    /**
     * Associate all fruits from List<Fruit> to the productId
     * 
     * @param productId
     * @param fruits
     * @throws EntityNotFoundException
     */
    @Transactional
    public void synchronizeProductFruitByProductId(Long productId, List<Fruit> fruits) throws EntityNotFoundException {
    	Product product = productService.find(productId);
    	
    	// attempts to delete all previous association with the current productId
    	try {
    		deleteByProductId(productId);
		} catch (EntityNotFoundException ex) {
			//just ignore the problem since we are creating new associations anyway
		}
    	
    	ProductFruitKey key = new ProductFruitKey();
    	key.setProduct(product);
    	
    	Predicate<Fruit> createProductFruit = fruit -> {
    		try {
    			key.setFruit(fruit);
    			
        		ProductFruit productFruit = new ProductFruit();
        		productFruit.setKey(key);
				
        		productFruit = create(productFruit);
				
        		return true;
			} catch (ConstraintsViolationException ex) {
				LOG.error(String.format("Error during ProductFruit creation"), ex);
				return false;
			}
    	};
    	
    	createAssociationProductFruit(fruits, createProductFruit);
    	
    }
    
    
    /**
     * Associate all products from List<Product> to the fruitId
     * 
     * @param fruitId
     * @param products
     * @throws EntityNotFoundException
     */
    @Transactional
    public void synchronizeProductFruitByFruitId(Long fruitId, List<Product> products) throws EntityNotFoundException {
    	Fruit fruit = fruitService.find(fruitId);
    	
    	// attempts to delete all previous association with the current fruitId
    	try {
    		deleteByFruitId(fruitId);
		} catch (EntityNotFoundException ex) {
			//just ignore the problem since we are creating new associations anyway
		}
    	
    	ProductFruitKey key = new ProductFruitKey();
    	key.setFruit(fruit);
    	
    	Predicate<Product> createProductFruit = product -> {
    		try {
    			key.setProduct(product);
    			
        		ProductFruit productFruit = new ProductFruit();
        		productFruit.setKey(key);
				
        		productFruit = create(productFruit);
				
        		return true;
			} catch (ConstraintsViolationException ex) {
				LOG.error(String.format("Error during ProductFruit creation"), ex);
				return false;
			}
    	};
    	
    	createAssociationProductFruit(products, createProductFruit);
    	
    }

    /**
     * Create the associations between Product and Fruit from the list, using Predicate<?> for the actual creation
     * Since the predicate controls the actual creation, the ConstraintsViolationException will be handled there
     * 
     * @param <T> Either Product or Fruit can be used
     * @param list the list of Products or Fruits to be associate
     * @param createProductFruit the actual Predicate that makes the association 
     * 
     */
    @Transactional
	private <T> void createAssociationProductFruit(List<T> list, Predicate<T> createProductFruit) {
		list.stream()
    		.filter(createProductFruit)
    		.peek(null)
    		.anyMatch(f -> false);
	}

}
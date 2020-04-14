package br.com.cupuama.services.products;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.products.dto.ProductFruitDTO;
import br.com.cupuama.controller.products.mapper.ProductFruitMapper;
import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.domain.products.Product;
import br.com.cupuama.domain.products.ProductFruit;
import br.com.cupuama.domain.products.ProductFruitId;
import br.com.cupuama.domain.products.repository.ProductFruitRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;

@Service
public class ProductFruitService extends DefaultService<ProductFruit, ProductFruitId> {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private FruitService fruitService;
	
    public ProductFruitService(final CrudRepository<ProductFruit, ProductFruitId> repository) {
		super(repository);
	}

    @Transactional
    public ProductFruitDTO create(ProductFruitDTO dto) throws ConstraintsViolationException {
    	ProductFruit productFruit = ProductFruitMapper.makeProductFruit(dto);
		return ProductFruitMapper.makeDTO(create(productFruit));
    }
    
    public ProductFruit findByProductIdAndFruitId(Long productId, Long fruitId) throws EntityNotFoundException {
    	ProductFruitId key = getProductFruitId(productId, fruitId);
    	return findByIdChecked(key);
    }
    
    public void deleteByProductIdAndFruitId(Long productId, Long fruitId) throws EntityNotFoundException {
    	ProductFruitId key = getProductFruitId(productId, fruitId);
    	delete(key);
    }

	private ProductFruitId getProductFruitId(Long productId, Long fruitId) throws EntityNotFoundException {
		final Product product = productService.find(productId);
		final Fruit fruit = fruitService.find(fruitId);
		return new ProductFruitId(product, fruit);
	}
    /**
     * deletes all association of fruits with the productId 
     * 
     * @param productId
     * @throws EntityNotFoundException
     */
    @Transactional
	public void deleteByProductId(final Long productId) throws EntityNotFoundException {
		List<ProductFruit> list = findByProductId(productId);
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
	public void deleteByFruitId(final Long fruitId) throws EntityNotFoundException {
		List<ProductFruit> list = findByFruitId(fruitId);
		if (list.isEmpty()) {
			throw new EntityNotFoundException(String.format("No products associated with the fruit id %d were found!", fruitId));
		}
		list.stream().forEach(pf -> {
			repository.delete(pf);
		});
	};

	/**
	 * list associations by product id
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductFruit> findByProductId(final Long productId) {
		return ((ProductFruitRepository) repository).findByProductId(productId);
	}
	
	/**
	 * List associations by fruit id
	 * 
	 * @param fruitId
	 * @return
	 */
	public List<ProductFruit> findByFruitId(final Long fruitId) {
		return ((ProductFruitRepository) repository).findByFruitId(fruitId);
	}
	
    /**
     * Associate all fruits from List<Fruit> to the productId
     * 
     * @param productId
     * @param fruits
     * @throws EntityNotFoundException
     */
    @Transactional
    public List<ProductFruitDTO> syncronizeFruitsForProductId(final Long productId, final List<Fruit> fruits) throws EntityNotFoundException {
    	Product product = productService.find(productId);
    	
    	// attempts to delete all previous association with the current productId
    	try {
    		deleteByProductId(productId);
		} catch (EntityNotFoundException ex) {
			//just ignore the problem since we are creating new associations anyway
		}
    	
    	List<ProductFruitDTO> associations = new ArrayList<>();
    	
    	final Predicate<Fruit> createProductFruit = fruit -> {
    		try {
    			fruit = fruitService.find(fruit.getId());
    			final ProductFruitId key = new ProductFruitId(product, fruit);

    			ProductFruit productFruit = new ProductFruit();
        		productFruit.setId(key);
				
        		productFruit = create(productFruit);
        		associations.add(ProductFruitMapper.makeDTO(productFruit));
				
        		return true;
			} catch (ConstraintsViolationException | EntityNotFoundException ex) {
				LOG.error(String.format("Error during ProductFruit creation"), ex);
				return false;
			}
    	};
    	
    	createAssociationProductFruit(fruits, createProductFruit);
    	return associations;
    	
    }
    
    
    /**
     * Associate all products from List<Product> to the fruitId
     * 
     * @param fruitId
     * @param products
     * @throws EntityNotFoundException
     */
    @Transactional
    public List<ProductFruitDTO> synchronizeProductsForFruitId(final Long fruitId, final List<Product> products) throws EntityNotFoundException {
    	Fruit fruit = fruitService.find(fruitId);
    	
    	// attempts to delete all previous association with the current fruitId
    	try {
    		deleteByFruitId(fruitId);
		} catch (EntityNotFoundException ex) {
			//just ignore the problem since we are creating new associations anyway
		}
    	
    	List<ProductFruitDTO> associations = new ArrayList<>();
    	
    	final Predicate<Product> createProductFruit = product -> {
    		try {
    			product = productService.find(product.getId());
    			final ProductFruitId key = new ProductFruitId(product, fruit);
    			
        		ProductFruit productFruit = new ProductFruit();
        		productFruit.setId(key);
				
        		productFruit = create(productFruit);
        		associations.add(ProductFruitMapper.makeDTO(productFruit));
        		
        		return true;
			} catch (ConstraintsViolationException | EntityNotFoundException ex) {
				LOG.error(String.format("Error during ProductFruit creation"), ex);
				return false;
			}
    	};
    	
    	createAssociationProductFruit(products, createProductFruit);
    	return associations;
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
	private <T> void createAssociationProductFruit(final List<T> list, final Predicate<T> createProductFruit) {
		list.stream()
    		.filter(createProductFruit)
    		.anyMatch(f -> false);
	}

}
package br.com.cupuama.service.products;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.Product;
import br.com.cupuama.dto.ProductDTO;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.repository.ProductRepository;
import br.com.cupuama.service.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some product specific things.
 * <p/>
 */
@Service
public class ProductService extends DefaultService<Product, Long> {

	public ProductService(final ProductRepository productRepository) {
		super(productRepository);
	}

	/**
	 * Update a products information.
	 *
	 * @param productId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long productId, final ProductDTO productDTO) throws EntityNotFoundException {
		Product product = findByIdChecked(productId);
		product.setName(productDTO.getName());
		product.setUnit(productDTO.getUnit());
	}

	/**
	 * Find all products by name.
	 *
	 * @param name
	 */
	public List<Product> findByName(final String name) {
		return ((ProductRepository) repository).findByNameLike(name + "%");
	}

}

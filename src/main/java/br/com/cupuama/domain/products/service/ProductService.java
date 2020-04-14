package br.com.cupuama.domain.products.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.dto.ProductDTO;
import br.com.cupuama.domain.products.entity.Product;
import br.com.cupuama.domain.products.mapper.ProductMapper;
import br.com.cupuama.domain.products.repository.ProductRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


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

	@Transactional
	public ProductDTO create(ProductDTO dto) throws ConstraintsViolationException {
		Product product = ProductMapper.makeProduct(dto);
		return ProductMapper.makeDTO(create(product));
	}
	
	/**
	 * Update a products information.
	 *
	 * @param productId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long productId, final ProductDTO dto) throws EntityNotFoundException {
		Product product = findByIdChecked(productId);
		product.setName(dto.getName());
		product.setUnit(dto.getUnit());
		product.getAudit().setDateUpdated(ZonedDateTime.now());
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

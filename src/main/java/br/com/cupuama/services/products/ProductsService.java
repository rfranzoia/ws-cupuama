package br.com.cupuama.services.products;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.products.dto.ProductsDTO;
import br.com.cupuama.controller.products.mapper.ProductsMapper;
import br.com.cupuama.domain.products.Products;
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
public class ProductsService extends DefaultService<Products, Long> {

	public ProductsService(final ProductRepository productRepository) {
		super(productRepository);
	}

	@Transactional
	public ProductsDTO create(ProductsDTO dto) throws ConstraintsViolationException {
		Products products = ProductsMapper.makeProduct(dto);
		return ProductsMapper.makeDTO(create(products));
	}
	
	/**
	 * Update a products information.
	 *
	 * @param productId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long productId, final ProductsDTO dto) throws EntityNotFoundException {
		Products products = findByIdChecked(productId);
		products.setName(dto.getName());
		products.setUnit(dto.getUnit());
		products.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Find all products by name.
	 *
	 * @param name
	 */
	public List<Products> findByName(final String name) {
		return ((ProductRepository) repository).findByNameLike(name + "%");
	}

}

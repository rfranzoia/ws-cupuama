package br.com.cupuama.controller.products;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.controller.products.dto.ProductsDTO;
import br.com.cupuama.controller.products.mapper.ProductsMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.products.ProductsService;

/**
 * All operations with a product will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/products")
public class ProductsController {

	private final ProductsService productsService;

	@Autowired
	public ProductsController(final ProductsService productsService) {
		this.productsService = productsService;
	}

	@GetMapping("/{productId}")
	public ProductsDTO getProduct(@PathVariable final long productId) throws EntityNotFoundException {
		return ProductsMapper.makeDTO(productsService.find(productId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductsDTO createProduct(@Valid @RequestBody final ProductsDTO productsDTO) throws ConstraintsViolationException {
		return productsService.create(productsDTO);
	}

	@DeleteMapping("/{productId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable final long productId) throws EntityNotFoundException {
		productsService.delete(productId);
	}

	@PutMapping("/{productId}")
	public void update(@PathVariable final long productId, @RequestBody final ProductsDTO productsDTO)
			throws EntityNotFoundException {
		productsService.update(productId, productsDTO);
	}

	@GetMapping("/name/{name}")
	public List<ProductsDTO> findProductsByName(@PathVariable final String name) {
		return ProductsMapper.makeListDTO(productsService.findByName(name));
	}

	@GetMapping
	public List<ProductsDTO> findAllProducts() {
		return ProductsMapper.makeListDTO(productsService.findAll());
	}
}

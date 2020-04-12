package br.com.cupuama.domain.products.controller;

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

import br.com.cupuama.domain.products.dto.ProductDTO;
import br.com.cupuama.domain.products.mapper.ProductMapper;
import br.com.cupuama.domain.products.service.ProductService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a product will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{productId}")
	public ProductDTO getProduct(@PathVariable final long productId) throws EntityNotFoundException {
		return ProductMapper.makeProductDTO(productService.find(productId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO createProduct(@Valid @RequestBody final ProductDTO productDTO) throws ConstraintsViolationException {
		return productService.create(productDTO);
	}

	@DeleteMapping("/{productId}")
	public void deleteProduct(@PathVariable final long productId) throws EntityNotFoundException {
		productService.delete(productId);
	}

	@PutMapping("/{productId}")
	public void updateLocation(@PathVariable final long productId, @RequestBody final ProductDTO productDTO)
			throws EntityNotFoundException {
		productService.update(productId, productDTO);
	}

	@GetMapping("/name/{name}")
	public List<ProductDTO> findProductsByName(@PathVariable final String name) {
		return ProductMapper.makeProductDTOList(productService.findByName(name));
	}

	@GetMapping
	public List<ProductDTO> findAllProducts() {
		return ProductMapper.makeProductDTOList(productService.findAll());
	}
}

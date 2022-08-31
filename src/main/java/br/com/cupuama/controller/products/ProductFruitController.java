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

import br.com.cupuama.controller.products.dto.FruitsDTO;
import br.com.cupuama.controller.products.dto.ProductsDTO;
import br.com.cupuama.controller.products.dto.ProductFruitDTO;
import br.com.cupuama.controller.products.mapper.FruitsMapper;
import br.com.cupuama.controller.products.mapper.ProductFruitMapper;
import br.com.cupuama.controller.products.mapper.ProductsMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.products.ProductFruitService;

/**
 * All operations with a productFruit will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/productFruits")
public class ProductFruitController {

	private final ProductFruitService productFruitService;

	@Autowired
	public ProductFruitController(final ProductFruitService productFruitService) {
		this.productFruitService = productFruitService;
	}

	@GetMapping("/product/{productId}/fruit/{fruitId}")
	public ProductFruitDTO getProductFruit(@PathVariable final long productId, @PathVariable final long fruitId) throws EntityNotFoundException {
		return ProductFruitMapper.makeDTO(productFruitService.findByProductIdAndFruitId(productId, fruitId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductFruitDTO createProductFruit(@Valid @RequestBody final ProductFruitDTO productFruitDTO) throws ConstraintsViolationException {
		return productFruitService.create(productFruitDTO);
	}

	@DeleteMapping("/product/{productId}/fruit/{fruitId}")
	public void deleteProductFruit(@PathVariable final long productId, @PathVariable final long fruitId) throws EntityNotFoundException {
		productFruitService.deleteByProductIdAndFruitId(productId, fruitId);
	}
	
	@DeleteMapping("/product/{productId}")
	public void deleteByProductId(@PathVariable final long productId) throws EntityNotFoundException {
		productFruitService.deleteByProductId(productId);
	}
	
	@DeleteMapping("/fruit/{fruitId}")
	public void deleteByFruitId(@PathVariable final long fruitId) throws EntityNotFoundException {
		productFruitService.deleteByFruitId(fruitId);
	}

	@GetMapping("/product/{productId}")
	public List<ProductFruitDTO> findByProductId(@PathVariable final long productId) {
		return ProductFruitMapper.makeListDTO(productFruitService.findByProductId(productId));
	}
	
	@GetMapping("/fruit/{fruitId}")
	public List<ProductFruitDTO> findByFruitId(@PathVariable final long fruitId) {
		return ProductFruitMapper.makeListDTO(productFruitService.findByFruitId(fruitId));
	}
	
	@GetMapping
	public List<ProductFruitDTO> findAllProductFruits() {
		return ProductFruitMapper.makeListDTO(productFruitService.findAll());
	}
	
	@PutMapping("/product/{productId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<ProductFruitDTO> syncronizeFruitsForProductId(@PathVariable final long productId, @RequestBody final List<FruitsDTO> fruits) throws EntityNotFoundException {
		return productFruitService.syncronizeFruitsForProductId(productId, FruitsMapper.makeList(fruits));
	}
	
	@PutMapping("/fruit/{fruitId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<ProductFruitDTO> synchronizeProductsForFruitId(@PathVariable final long fruitId, @RequestBody final List<ProductsDTO> products) throws EntityNotFoundException {
		return productFruitService.synchronizeProductsForFruitId(fruitId, ProductsMapper.makeList(products));
	}
}

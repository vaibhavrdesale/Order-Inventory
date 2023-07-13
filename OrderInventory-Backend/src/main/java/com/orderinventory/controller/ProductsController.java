package com.orderinventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.ProductDto;
import com.orderinventory.dto.ProductsDto;
import com.orderinventory.services.ProductServices;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductsController {

	@Autowired
	private ProductServices productServices;

	@PostMapping("/products")
	public ResponseEntity<ProductsDto> saveProduct(@Valid @RequestBody ProductsDto productsDto) {
		ProductsDto saveProduct = productServices.saveProduct(productsDto);
		return new ResponseEntity<ProductsDto>(productsDto,HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductsDto>> getAllProducts() {
		List<ProductsDto> productsDtos = productServices.getAllProducts();
		return new ResponseEntity<List<ProductsDto>>(productsDtos, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductsDto> getProductById(@PathVariable Integer productId) {
		ProductsDto productsDto = productServices.getProductById(productId);
		return new ResponseEntity<ProductsDto>(productsDto, HttpStatus.OK);
	}

	@GetMapping("/productname/{productName}")
	public ResponseEntity<List<ProductsDto>> getProductsByName(@PathVariable String productName) {
		List<ProductsDto> productsDtos = productServices.getProductsByName(productName);
		return new ResponseEntity<List<ProductsDto>>(productsDtos, HttpStatus.OK);
	}

	@GetMapping("/products/findtoptenproductsonprice")
	public ResponseEntity<List<ProductsDto>> findTopTenProductsByPrice() {
		List<ProductsDto> productsDtos = productServices.findTopTenProductsByPrice();
		return new ResponseEntity<List<ProductsDto>>(productsDtos, HttpStatus.OK);
	}

	@GetMapping("/products/unitprice")
	public ResponseEntity<List<ProductsDto>> findByUnitPriceBetween(@RequestParam("min") double minPrice,
			@RequestParam("max") double maxPrice) {
		List<ProductsDto> productsDtos = productServices.findByUnitPriceBetween(minPrice, maxPrice);
		return new ResponseEntity<List<ProductsDto>>(productsDtos, HttpStatus.OK);
	}

	@PutMapping("/products")
	public ResponseEntity<ProductsDto> updateProduct(@Valid @RequestBody ProductsDto productsDto) {
		ProductsDto updateProduct = productServices.updateProduct(productsDto);
		return new ResponseEntity<ProductsDto>(updateProduct, HttpStatus.OK);
	}

	@GetMapping("/products/sort")
	public ResponseEntity<List<ProductsDto>> getSortedProducts(@RequestParam("field") String field) {
		Sort sort = Sort.by(field);
		List<ProductsDto> sortedProducts = productServices.getSortedProducts(sort);
		return new ResponseEntity<List<ProductsDto>>(sortedProducts, HttpStatus.OK);
	}

}

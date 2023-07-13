package com.orderinventory.services;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.orderinventory.dto.ProductsDto;

public interface ProductServices {

	public ProductsDto saveProduct(ProductsDto productsDto);

	public List<ProductsDto> getAllProducts();

	public ProductsDto getProductById(Integer productsId);

	public List<ProductsDto> getProductsByName(String Name);

	public ProductsDto updateProduct(ProductsDto productDto);

	public List<ProductsDto> findTopTenProductsByPrice();

	public List<ProductsDto> findByUnitPriceBetween(double minPrice, double maxPrice);

	public List<ProductsDto> getSortedProducts(Sort sort);
	

}

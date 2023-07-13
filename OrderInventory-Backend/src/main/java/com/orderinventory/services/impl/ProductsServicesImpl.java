package com.orderinventory.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.orderinventory.dto.ProductsDto;
import com.orderinventory.entity.Products;
import com.orderinventory.exception.ProductsNotFoundException;
import com.orderinventory.repository.ProductRepository;
import com.orderinventory.services.ProductServices;

@Service
public class ProductsServicesImpl implements ProductServices {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductsDto saveProduct(ProductsDto productsDto) {
		Products product = new Products();
		BeanUtils.copyProperties(productsDto, product);
		productRepository.save(product);
		return productsDto;
	}

	@Override
	public List<ProductsDto> getAllProducts() throws ProductsNotFoundException {
		List<Products> pList = productRepository.findAll();
		if (pList.isEmpty()) {
			throw new ProductsNotFoundException("No products Available");
		}
		List<ProductsDto> productsDtos = new ArrayList<>();
		for (Products item : pList) {
			ProductsDto productsDto = new ProductsDto();
			BeanUtils.copyProperties(item, productsDto);
			productsDtos.add(productsDto);
		}
		return productsDtos;

	}

	@Override
	public List<ProductsDto> getProductsByName(String productName) {
		List<Products> pList = productRepository.findByProductNameContaining(productName);
		if (pList.isEmpty()) {
			throw new ProductsNotFoundException("No products Available");
		}
		List<ProductsDto> productsDtos = new ArrayList<>();
		for (Products item : pList) {
			ProductsDto productsDto = new ProductsDto();
			BeanUtils.copyProperties(item, productsDto);
			productsDtos.add(productsDto);
		}
		return productsDtos;
	}

	@Override
	public ProductsDto updateProduct(ProductsDto productDto) {
		if (!productRepository.existsById(productDto.getProductId())) {
			throw new ProductsNotFoundException("No product found for this id");
		}
		Products products = new Products();
		BeanUtils.copyProperties(productDto, products);
		productRepository.save(products);
		return productDto;

	}

	@Override
	public ProductsDto getProductById(Integer productsId) {
		Optional<Products> findById = productRepository.findById(productsId);

		if (findById.isPresent()) {
			ProductsDto productsDto = new ProductsDto();
			BeanUtils.copyProperties(findById.get(), productsDto);
			return productsDto;
		}
		throw new ProductsNotFoundException("No product Available");

	}

	@Override
	public List<ProductsDto> findByUnitPriceBetween(double minPrice, double maxPrice) {
		BigDecimal min = new BigDecimal(minPrice); // Noncompliant; see comment above
		BigDecimal max = new BigDecimal(maxPrice); // Noncompliant; same result
		List<Products> productsList = productRepository.findByUnitPriceBetween(min, max);
		if (productsList.isEmpty()) {
			throw new ProductsNotFoundException("Products not find between this unit price range");
		}
		List<ProductsDto> dtos = new ArrayList<>();
		for (Products item : productsList) {
			ProductsDto productsDto = new ProductsDto();
			BeanUtils.copyProperties(item, productsDto);
			dtos.add(productsDto);
		}
		return dtos;
	}
	
	
	@Override
	public List<ProductsDto> getSortedProducts(Sort sort) {
		List<Products> sortedProducts = productRepository.findAll(sort);
		if (sortedProducts.isEmpty()) {
			throw new ProductsNotFoundException("Products not founds to sort");
		}
		List<ProductsDto> productsDtos = new ArrayList<>();
		for (Products item : sortedProducts) {
			ProductsDto productsDto = new ProductsDto();
			BeanUtils.copyProperties(item, productsDto);
			productsDtos.add(productsDto);
		}
		return productsDtos;
	}

	@Override
	public List<ProductsDto> findTopTenProductsByPrice() {
		List<Products> findTopTen = productRepository.findTop10ByOrderByUnitPriceDesc();
		List<ProductsDto> productsDtos = new ArrayList<>();
		for (Products item : findTopTen) {
			ProductsDto productsDto = new ProductsDto();
			BeanUtils.copyProperties(item, productsDto);
			productsDtos.add(productsDto);
		}
		return productsDtos;
	}

}

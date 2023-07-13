package com.orderinventory.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderinventory.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

	public List<Products> findByProductNameContaining(String productName);

	public List<Products> findTop10ByOrderByUnitPriceDesc();

	public List<Products> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

	public List<Products> findAll(Sort sort);

}

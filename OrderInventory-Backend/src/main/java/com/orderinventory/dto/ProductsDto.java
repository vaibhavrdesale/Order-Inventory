package com.orderinventory.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductsDto {
	private Integer productId;
	@NotBlank
	@Size(min = 2 , max = 255)
	private String productName;
	private BigDecimal unitPrice;
	@Size(max = 255)
	private String productDetails;

	public ProductsDto() {
		super();
	}

	public ProductsDto(Integer productId, String productName, BigDecimal unitPrice, String productDetails) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.productDetails = productDetails;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

}

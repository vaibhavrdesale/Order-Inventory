package com.orderinventory.dto;

import java.math.BigDecimal;

public class ProductDto {

    private Integer productId;
    private String productName;
    private BigDecimal unitPrice;
    private String productDetails;
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
	public void setProductDetails(String string) {
		this.productDetails = string;
	}
	public ProductDto(Integer productId, String productName, BigDecimal unitPrice, String productDetails) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.productDetails = productDetails;
	}
	
	
	public ProductDto() {
		super();
	}

	
	

    
}


package com.orderinventory.dto;

import java.math.BigDecimal;

public class InventoryProductDTO {

	Long inventoryId;

	Integer productId;

	String productName;

	BigDecimal UnitPrice;

	String productDetails;

	public Long getInventoryId() {

	    return inventoryId;

	}

	public void setInventoryId(Long inventoryId) {

	    this.inventoryId = inventoryId;

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

	    return UnitPrice;

	}

	public void setUnitPrice(BigDecimal unitPrice) {

	    UnitPrice = unitPrice;

	}

	public String getProductDetails() {

	    return productDetails;

	}

	public void setProductDetails(String productDetails) {

	    this.productDetails = productDetails;

	}

	 
}


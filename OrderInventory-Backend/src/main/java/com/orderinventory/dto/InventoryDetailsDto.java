package com.orderinventory.dto;

import java.math.BigDecimal;

public class InventoryDetailsDto {
    private String productName;
    private BigDecimal unitPrice;
    private String storeName;
    private String webAddress;
    private String physicalAddress;
    private String shipmentStatus;
    private BigDecimal totalAmount;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getWebAddress() {
		return webAddress;
	}
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
	public String getPhysicalAddress() {
		return physicalAddress;
	}
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

    // Constructors, getters, and setters
	
	public InventoryDetailsDto(String productName, BigDecimal unitPrice, String storeName, String webAddress, String physicalAddress, String shipmentStatus, BigDecimal totalAmount) {
	    this.productName = productName;
	    this.unitPrice = unitPrice;
	    this.storeName = storeName;
	    this.webAddress = webAddress;
	    this.physicalAddress = physicalAddress;
	    this.shipmentStatus = shipmentStatus;
	    this.totalAmount = totalAmount;
	}
	public InventoryDetailsDto() {
		super();
	}
	
	

    
    
}


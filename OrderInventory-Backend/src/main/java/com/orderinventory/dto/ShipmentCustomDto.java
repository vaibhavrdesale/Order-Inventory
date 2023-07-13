package com.orderinventory.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ShipmentCustomDto {
	
	
     private Integer shipmentId;  //Unique Identifier for the shipment
     @NotNull(message="storeId can not be null")
	private Integer storeId;     //ID of the store from which the shipment was made
	
	private Integer customerId;  //ID of the customers who received order
	@NotBlank(message="deliveryAddress can not be blank")
	@Size(min=3,max=250,message="deliveryAddress can be minimum 3 and maximum 250 characters long")
	private String deliveryAddress; //Delivery address for the shipment
	@NotBlank(message="shipmentStatus can not be blank")
	@Size(message="shipmentStatus can be minimun 3 and maximum 250 characters long")
	private String shipmentStatus;  //Current status of the shipment

	public Integer getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	
	
}

package com.orderinventory.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ShipmentsDto {

	private Integer shipmentId;  //Unique Identifier for the shipment
	@NotNull(message="storeId can not be null")
	private Integer storeId;     //ID of the store from which the shipment was made
	
	//private Customers customer;  //ID of the customers who received order
	@NotBlank(message="deliveryAddress can not be blank")
	@Size(min=3,max=250,message="deliveryAddress can be minimum 3 and maximum 250 characters long")
	private String deliveryAddress; //Delivery address for the shipment
	@NotBlank(message="shipmentStatus can not be blank")
	@Size(message="shipmentStatus can be minimun 3 and maximum 250 characters long")
	private String shipmentStatus;  //Current status of the shipment
	@NotNull(message="customerId can not be null")
	private Integer customerId;

	//Getters and Setters
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

	
//	public Customers getCustomer() {
//		return this.customer;
//	}
//
//	public void setCustomer(Customers customer) {
//		this.customer = customer;
//	}

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

	  public Integer getCustomerId() {
	        return customerId;
	    }

	    public void setCustomerId(Integer customerId) {
	        this.customerId = customerId;
	    }

		@Override
		public int hashCode() {
			return Objects.hash(customerId, deliveryAddress, shipmentId, shipmentStatus, storeId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShipmentsDto other = (ShipmentsDto) obj;
			return Objects.equals(customerId, other.customerId)
					&& Objects.equals(deliveryAddress, other.deliveryAddress)
					&& Objects.equals(shipmentId, other.shipmentId)
					&& Objects.equals(shipmentStatus, other.shipmentStatus) && Objects.equals(storeId, other.storeId);
		}
	    
	    

}

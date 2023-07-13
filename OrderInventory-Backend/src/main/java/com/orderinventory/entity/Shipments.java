package com.orderinventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shipments")
public class Shipments implements Serializable {


	private static final long serialVersionUID = -7373968493868792752L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipment_id", nullable = false)
	private Integer shipmentId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Stores store;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customers customer;

	@Column(name = "delivery_address", nullable = false)
	private String deliveryAddress;

	@Column(name = "shipment_status", nullable = false)
	private String shipmentStatus;

//getters and setters
	public Integer getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Stores getStore() {
		return store;
	}

	public void setStore(Stores store) {
		this.store = store;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
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

	@Override
	public String toString() {
		return "Shipments [shipmentId=" + shipmentId + ", storeId=" + store + ", customer=" + customer
				+ ", deliveryAddress=" + deliveryAddress + ", shipmentStatus=" + shipmentStatus + "]";
	}

	
	
}


package com.orderinventory.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inventoryId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Stores store;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Products product;

	private int productInventory;

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public void setProductInventory(int productInventory) {
		this.productInventory = productInventory;
	}

	public Stores getStore() {
		return store;
	}

	public void setStore(Stores store) {
		this.store = store;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Integer getProductInventory() {
		return productInventory;
	}

	public void setProductInventory(Integer productInventory) {
		this.productInventory = productInventory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Inventory(Long inventoryId, Stores store, Products product, int productInventory) {
		super();
		this.inventoryId = inventoryId;
		this.store = store;
		this.product = product;
		this.productInventory = productInventory;
	}

	public Inventory() {
		super();
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", product=" + product + ", productInventory="
				+ productInventory + "]";
	}

}

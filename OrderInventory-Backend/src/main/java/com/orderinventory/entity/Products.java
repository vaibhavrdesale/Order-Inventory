package com.orderinventory.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Creating Entity table with table name "products"
@Entity
@Table(name = "products")
public class Products implements Serializable {

	private static final long serialVersionUID = -7357045868158958530L;

	// assigning primary-key and autoIncrement
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private int productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "unit_price", nullable = true)
	private BigDecimal unitPrice;

	@Column(name = "product_details")
	private String productDetails;
	
	  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	    private List<Inventory> inventoryList;
	

	// Getters and Setters
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
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

	public List<Inventory> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<Inventory> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public Products(int productId, String productName, BigDecimal unitPrice, String productDetails,
			List<Inventory> inventoryList) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.productDetails = productDetails;
		this.inventoryList = inventoryList;
	}

	public Products() {
		super();
	}

	@Override
	public String toString() {
		return "Products [productId=" + productId + ", productName=" + productName + ", unitPrice=" + unitPrice
				+ ", productDetails=" + productDetails + ", inventoryList=" + inventoryList + "]";
	}
	
	
	
	

}

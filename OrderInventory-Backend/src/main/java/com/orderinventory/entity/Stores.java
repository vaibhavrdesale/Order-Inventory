package com.orderinventory.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "stores")
public class Stores implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id")
	private Integer storeId;

	@Column(name = "store_name")
	private String storeName;

	@Column(name = "web_address", nullable = false)
	private String webAddress;

	@Column(name = "physical_address")
	private String physicalAddress;

	@Column(name = "latitude", precision = 9, scale = 6)
	private BigDecimal latitude;

	@Column(name = "longitude", precision = 9, scale = 6)
	private BigDecimal longitude;

	@Column(name = "logo", length = 255)
	private String logo;

	@Column(name = "logo_mime_type")
	private String logoMimeType;

	@Column(name = "logo_filename")
	private String logoFilename;

	@Column(name = "logo_charset")
	private String logoCharset;

	@Column(name = "logo_last_updated")
	private LocalDate logoLastUpdated;

	@Transient
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Inventory> inventoryList;

	@OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
	private List<Orders> orders;


	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Shipments> shipments;
	
	//getter and setters

	public List<Shipments> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipments> shipments) {
		this.shipments = shipments;
	}
	
	public Stores(Integer storeId) {

        this.storeId=storeId;

    }

	public void setStoreId(Integer storeId) {
	    this.storeId = storeId;
	}
	
	public Stores() {
		super();
	}

	public List<Inventory> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<Inventory> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
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

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoMimeType() {
		return logoMimeType;
	}

	public void setLogoMimeType(String logoMimeType) {
		this.logoMimeType = logoMimeType;
	}

	public String getLogoFilename() {
		return logoFilename;
	}

	public void setLogoFilename(String logoFilename) {
		this.logoFilename = logoFilename;
	}

	public String getLogoCharset() {
		return logoCharset;
	}

	public void setLogoCharset(String logoCharset) {
		this.logoCharset = logoCharset;
	}

	public LocalDate getLogoLastUpdated() {
		return logoLastUpdated;
	}

	public void setLogoLastUpdated(LocalDate logoLastUpdated) {
		this.logoLastUpdated = logoLastUpdated;
	}

}

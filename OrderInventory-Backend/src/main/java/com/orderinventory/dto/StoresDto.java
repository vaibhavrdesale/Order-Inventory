package com.orderinventory.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.orderinventory.entity.Inventory;

public class StoresDto {
	



        private Integer storeId;

        @NotBlank(message="storeName can not be blank")

        @Size(min=3,max=250,message="storeName should be minimum 3 and maximun 250 charcters long")

        private String storeName;

        @Size(min=3,max=250,message="webAddress should be minimum 3 and maximum 250 characters long")

        private String webAddress;

        @Size(min=3,max=250,message="physicalAddress should be minimum 3 and maximum 250 characters long")

        private String physicalAddress;


        private BigDecimal latitude;

        private BigDecimal longitude;

        @Size(min=3,max=250,message="logo should be minimum 3 and maximum 250 characters long")

        private String logo;

        @Size(min=3,max=250,message="logoMimeType should be minimum 3 and maximum 250 characters long")

        private String logoMimeType;

        @Size(min=3,max=250,message="logoFilename should be minimum 3 and maximum 250 characters long")

        private String logoFilename;

        @Size(min=3,max=250,message="logoCharset should be minimum 3 and maximum 250 characters long")

        private String logoCharset;

        private LocalDate logoLastUpdated;

        private List<Inventory> inventoryList;

        
		
		public Integer getStoreId() {
			return storeId;
		}
		public void setStoreId(Integer storeId) {
			this.storeId = storeId;
		}
		
		//reference
		public List<Inventory> getInventoryList() {
			return inventoryList;
		}
		public void setInventoryList(List<Inventory> inventoryList) {
			this.inventoryList = inventoryList;
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
		
		
		public StoresDto(Integer storeId, String storeName, String webAddress, String physicalAddress,
				BigDecimal latitude, BigDecimal longitude, String logo, String logoMimeType, String logoFilename,
				String logoCharset, LocalDate logoLastUpdated) {
			super();
			this.storeId = storeId;
			this.storeName = storeName;
			this.webAddress = webAddress;
			this.physicalAddress = physicalAddress;
			this.latitude = latitude;
			this.longitude = longitude;
			this.logo = logo;
			this.logoMimeType = logoMimeType;
			this.logoFilename = logoFilename;
			this.logoCharset = logoCharset;
			this.logoLastUpdated = logoLastUpdated;
		}
		public StoresDto() {
			super();
		}
		@Override
		public String toString() {
			return "StoresDto [storeId=" + storeId + ", storeName=" + storeName + ", webAddress=" + webAddress
					+ ", physicalAddress=" + physicalAddress + ", latitude=" + latitude + ", longitude=" + longitude
					+ ", logo=" + logo + ", logoMimeType=" + logoMimeType + ", logoFilename=" + logoFilename
					+ ", logoCharset=" + logoCharset + ", logoLastUpdated=" + logoLastUpdated + ", inventoryList="
					+ inventoryList + "]";
		}
		@Override
		public int hashCode() {
			return Objects.hash(inventoryList, latitude, logo, logoCharset, logoFilename, logoLastUpdated, logoMimeType,
					longitude, physicalAddress, storeId, storeName, webAddress);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StoresDto other = (StoresDto) obj;
			return Objects.equals(inventoryList, other.inventoryList) && Objects.equals(latitude, other.latitude)
					&& Objects.equals(logo, other.logo) && Objects.equals(logoCharset, other.logoCharset)
					&& Objects.equals(logoFilename, other.logoFilename)
					&& Objects.equals(logoLastUpdated, other.logoLastUpdated)
					&& Objects.equals(logoMimeType, other.logoMimeType) && Objects.equals(longitude, other.longitude)
					&& Objects.equals(physicalAddress, other.physicalAddress) && Objects.equals(storeId, other.storeId)
					&& Objects.equals(storeName, other.storeName) && Objects.equals(webAddress, other.webAddress);
		}
		
		
		
		
	}



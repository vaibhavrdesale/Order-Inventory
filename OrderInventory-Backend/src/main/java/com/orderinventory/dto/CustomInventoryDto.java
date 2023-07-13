package com.orderinventory.dto;

public class CustomInventoryDto {
    private Long inventoryId;
    private ProductDto productData;
    private StoresDto storeData;
    private CustomersDto customerData;

    public CustomInventoryDto() {
    }

    public CustomInventoryDto(Long inventoryId, ProductDto productData, StoresDto storeData, CustomersDto customerData) {
        this.inventoryId = inventoryId;
        this.productData = productData;
        this.storeData = storeData;
        this.customerData = customerData;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public ProductDto getProductData() {
        return productData;
    }

    public void setProductData(ProductDto productData) {
        this.productData = productData;
    }

    public StoresDto getStoreData() {
        return storeData;
    }

    public void setStoreData(StoresDto storeData) {
        this.storeData = storeData;
    }

    public CustomersDto getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomersDto customerData) {
        this.customerData = customerData;
    }

	public void setCustomerName(String customerName) {
		// TODO Auto-generated method stub
		
	}
}



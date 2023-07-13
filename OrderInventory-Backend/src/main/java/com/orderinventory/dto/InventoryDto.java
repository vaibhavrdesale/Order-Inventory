package com.orderinventory.dto;

public class InventoryDto {
	 private Long inventoryId;
	    private ProductDto productData;
	    private StoresDto storeData = new StoresDto(); // Initialize storeData with a new instance
	    private int productInventory;

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

    public int getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(int productInventory) {
        this.productInventory = productInventory;
    }
}

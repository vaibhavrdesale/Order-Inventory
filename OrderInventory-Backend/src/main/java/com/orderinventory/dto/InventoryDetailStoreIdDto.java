package com.orderinventory.dto;

public class InventoryDetailStoreIdDto {
    private Long inventoryId;
    private ProductDto productData;
    private StoresDto storeData;
    private String orderStatus;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}


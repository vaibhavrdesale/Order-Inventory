package com.orderinventory.dto;

import java.util.List;

public class InventoryWithShipmentsDto {
    private Long inventoryId;
    private ProductDto productData;
    private StoresDto storeData;
    private int productInventory;
    private List<ShipmentsDto> shipmentData;

    public InventoryWithShipmentsDto() {
    }

    public InventoryWithShipmentsDto(Long inventoryId, ProductDto productData, StoresDto storeData, int productInventory, List<ShipmentsDto> shipmentData) {
        this.inventoryId = inventoryId;
        this.productData = productData;
        this.storeData = storeData;
        this.productInventory = productInventory;
        this.shipmentData = shipmentData;
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

    public int getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(int productInventory) {
        this.productInventory = productInventory;
    }

    public List<ShipmentsDto> getShipmentData() {
        return shipmentData;
    }

    public void setShipmentData(List<ShipmentsDto> shipmentData) {
        this.shipmentData = shipmentData;
    }
}


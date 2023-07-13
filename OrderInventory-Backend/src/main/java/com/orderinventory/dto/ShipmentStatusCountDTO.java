package com.orderinventory.dto;

public class ShipmentStatusCountDTO {
    private String shipmentStatus;
    private Integer count;

    public ShipmentStatusCountDTO() {
    }

    public ShipmentStatusCountDTO(String shipmentStatus, Integer count) {
        this.shipmentStatus = shipmentStatus;
        this.count = count;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}



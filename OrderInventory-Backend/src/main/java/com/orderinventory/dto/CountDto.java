package com.orderinventory.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CountDto {
@NotBlank
public String shipmentStatus;
@NotNull
public Integer countCustomer;
public String getShipmentStatus() {
	return shipmentStatus;
}
public void setShipmentStatus(String shipmentStatus) {
	this.shipmentStatus = shipmentStatus;
}
public Integer getCountCustomer() {
	return countCustomer;
}
public void setCountCustomer(Integer countCustomer) {
	this.countCustomer = countCustomer;
}


}

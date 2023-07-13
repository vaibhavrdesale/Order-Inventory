package com.orderinventory.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomersDto {

	private Integer customerId;
	@NotBlank(message="emailAddress can not be null")
	@Size(min=3,max=250,message="The emailAddress should be minimum 3 and maximum 250 characters")
	private String emailAddress;
	@NotBlank(message = "fullName can not be blank")
	@Size(min =3,max = 250, message= "fullname should be minimum 3 and maximum 250 characters long")
	private String fullName;
	
	//setter and getter
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(customerId, emailAddress, fullName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomersDto other = (CustomersDto) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(emailAddress, other.emailAddress)
				&& Objects.equals(fullName, other.fullName);
	}
	
	
	
	
	
	
}


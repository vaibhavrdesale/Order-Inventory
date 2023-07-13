package com.orderinventory.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Creating Entity table with table Name "customers"
@Entity
@Table(name = "customers")
public class Customers implements Serializable {

	private static final long serialVersionUID = -7536894331506897148L;
//assigning primary-key and autoIncrement
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	

	@Column(name = "email_address", length = 255, nullable = false, unique = true)
	private String emailAddress;

	@Column(name = "full_name", length = 255, nullable = false)
	private String fullName;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Orders> orders;

	public Customers(Integer customerId) {
		this.customerId = customerId;
	}

	public Customers() {
	}

	// getters and setters


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

}

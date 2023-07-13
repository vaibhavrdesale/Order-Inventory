package com.orderinventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderinventory.dto.CountDto;
import com.orderinventory.dto.CustomersDto;
import com.orderinventory.dto.OrderDto;
import com.orderinventory.dto.ResponseDto;
import com.orderinventory.dto.ShipmentCustomDto;
import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Orders;
import com.orderinventory.entity.Shipments;
import com.orderinventory.exception.CustomerNotFoundException;
import com.orderinventory.repository.CustomersRepository;
import com.orderinventory.repository.OrderRepository;
import com.orderinventory.repository.ShipmentsRepository;
import com.orderinventory.services.CustomersServices;

@Service
public class CustomersServicesImpl implements CustomersServices {

	@Autowired
	private CustomersRepository customerRepository;
	@Autowired
	private ShipmentsRepository shipmentsRepository;
	@Autowired
	private OrderRepository orderRepository;
	private ResponseDto responseDto = new ResponseDto();

	@Override
	public List<CustomersDto> getCustomersByFullName(String fullname) {

		List<Customers> customersList = customerRepository.findByFullName(fullname);

		List<CustomersDto> customerDtos = new ArrayList<>();

		if (customersList.isEmpty()) {
			throw new CustomerNotFoundException("No Customer Found with full name : " + fullname);
		}

		for (Customers customer : customersList) {
			CustomersDto customerdto = new CustomersDto();
			BeanUtils.copyProperties(customer, customerdto);
			customerDtos.add(customerdto);
		}

		return customerDtos;
	}

	@Override
	public List<CustomersDto> getCustomersByEmail(String email) {
		List<Customers> customersList = customerRepository.findByEmailAddress(email);
		List<CustomersDto> customerDtos = new ArrayList<>();
		if (customersList.isEmpty()) {
			throw new CustomerNotFoundException("No Customer Found with Email : " + email);
		}
		for (Customers customer : customersList) {
			CustomersDto customerDto = new CustomersDto();
			BeanUtils.copyProperties(customer, customerDto);
			customerDtos.add(customerDto);
		}
		return customerDtos;
	}


	@Override
	public ResponseDto saveCustomers(CustomersDto customerDto) {
		if (customerRepository.existsByEmailAddress(customerDto.getEmailAddress())) {
			System.out.println("Email already exist");
			throw new CustomerNotFoundException("Email already exist");
		}
		try {
			Customers customer = new Customers();
			BeanUtils.copyProperties(customerDto, customer);
			customerRepository.save(customer);
			responseDto.setResponseMessage("Record Created Successfully");
			return responseDto;
		} catch (Exception ex) {
			throw new CustomerNotFoundException("sorry failed to add customer");
		}
	}

	@Override
	public ResponseDto updateCustomer(CustomersDto customerDto) {
		Optional<Customers> findById = customerRepository.findById(customerDto.getCustomerId());
		if (findById.isEmpty()) {
			throw new CustomerNotFoundException("No customer avaliable with given id:" + customerDto.getCustomerId());
		}
		try {
			Customers customers = new Customers();
			BeanUtils.copyProperties(customerDto, customers);
			 customerRepository.save(customers);
			responseDto.setResponseMessage("Record updated Successfully");
			return responseDto;
		} catch (Exception e) {
			throw new CustomerNotFoundException("Failed to update customer");
		}
	}

	@Override
	public List<CustomersDto> getAllCustomers() {
		List<Customers> customersList = customerRepository.findAll();
		List<CustomersDto> customerDtos = new ArrayList<>();
		if (customersList.isEmpty()) {
			throw new CustomerNotFoundException("No Customer Found");
		}
		for (Customers customer : customersList) {
			CustomersDto custDto = new CustomersDto();
			BeanUtils.copyProperties(customer, custDto);
			customerDtos.add(custDto);
		}
		return customerDtos;
	}

	@Override
	public CountDto getCustomerCountByShipmentStatus(String shipmentStatus) {
		Integer customerCount = customerRepository.getCustomerCountByShipmentStatus(shipmentStatus);
		CountDto countDto = new CountDto();
		countDto.setCountCustomer(customerCount);
		countDto.setShipmentStatus(shipmentStatus);
		return countDto;
	}

	@Override
	public List<ShipmentCustomDto> getShipmentsByCustomerId(Integer customerId) {
		Optional<Customers> findById = customerRepository.findById(customerId);
		if (findById.isPresent()) {
			List<Shipments> ShipmentsList = shipmentsRepository.findByCustomer(findById.get());
			List<ShipmentCustomDto> shipmentdtos = new ArrayList<>();
			for (Shipments shipment : ShipmentsList) {
				ShipmentCustomDto shipmentCustomDto = new ShipmentCustomDto();
				BeanUtils.copyProperties(shipment, shipmentCustomDto);
				shipmentCustomDto.setCustomerId(shipment.getCustomer().getCustomerId());
				shipmentCustomDto.setStoreId(shipment.getStore().getStoreId());
				shipmentdtos.add(shipmentCustomDto);
			}
			return shipmentdtos;
		}
		throw new CustomerNotFoundException("No Shipments Found with given customerId: " + customerId);
	}

	@Override
	public List<OrderDto> getOrdersByCustomerId(Integer customerId) {
		if (customerId == null) {
			throw new CustomerNotFoundException("customerId can not be blank");
		}
		Optional<Customers> findById = customerRepository.findById(customerId);
		
		if (findById.isPresent()) {
			List<Orders> orders = orderRepository.findByCustomer(findById.get());
			List<OrderDto> orderDtos = new ArrayList<>();
			for (Orders order : orders) {
				OrderDto orderDto = new OrderDto();
				BeanUtils.copyProperties(order, orderDto);
				orderDto.setCustomerId(order.getCustomer().getCustomerId());
				orderDto.setStoreId(order.getStore().getStoreId());
				orderDtos.add(orderDto);
			}
			return orderDtos;
		}
		throw new CustomerNotFoundException("No orders found with givern customerId: " + customerId);
	}

}

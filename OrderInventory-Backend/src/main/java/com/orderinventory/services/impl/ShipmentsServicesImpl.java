package com.orderinventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.orderinventory.dto.CustomDto;
import com.orderinventory.dto.ShipmentsDto;
import com.orderinventory.entity.Customers;
import com.orderinventory.entity.Shipments;
import com.orderinventory.entity.Stores;
import com.orderinventory.repository.ShipmentsRepository;
import com.orderinventory.repository.StoreRepository;
import com.orderinventory.services.ShipmentsServices;


@Service
public class ShipmentsServicesImpl implements ShipmentsServices {

	@Autowired
	private ShipmentsRepository shipmentsRepository;

	@Autowired
	private StoreRepository storesRepository;

	@Override
	public List<ShipmentsDto> getShipmentsByCustomerId(Customers customerId) {



		List<Shipments> shipmentsList = shipmentsRepository.findByCustomer(customerId);
		List<ShipmentsDto> dtolist = new ArrayList<ShipmentsDto>();

		for (Shipments items : shipmentsList) {
			ShipmentsDto dto = new ShipmentsDto();
			// System.out.println(items.getcustomerId());
			BeanUtils.copyProperties(items, dto);
			dto.setStoreId(items.getStore().getStoreId());
			dtolist.add(dto);
		}
		return dtolist;

	}

	@Override
	public List<CustomDto> getTopTenMostShippedCustomers() {
		//Pageable pageable = PageRequest.of(0, 10);
		List<CustomDto> results = shipmentsRepository.getCustomDto(PageRequest.of(0, 10));
        System.out.println(results.get(0).getFullName());
		

		return results;
	}

	@Override
	public List<ShipmentsDto> getShipmentsByStoreId(Stores stordId) {
		

		                  List<Shipments> findByStore = shipmentsRepository.findByStore(stordId);
		                  List<ShipmentsDto> shipmentDtos=new ArrayList<>();
		                  
		                  for(Shipments shipment:findByStore) {
		                	  ShipmentsDto shipmentDto=new ShipmentsDto();
		                	  shipmentDto.setStoreId(shipment.getStore().getStoreId());
		                	  BeanUtils.copyProperties(shipment, shipmentDto);
		                	  shipmentDtos.add(shipmentDto);
		                  }
		                  return shipmentDtos;
	}

	@Override
	public String saveShipments(ShipmentsDto shipmentsDto) {
		
		Shipments shipments = new Shipments();
		Optional<Stores> findById = storesRepository.findById(shipmentsDto.getStoreId());
		shipments.setStore(findById.get());
		BeanUtils.copyProperties(shipmentsDto, shipments);

		shipmentsRepository.save(shipments);
		return "Record Created Successfully";
	}


	

}

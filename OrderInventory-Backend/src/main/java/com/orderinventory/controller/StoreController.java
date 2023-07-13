package com.orderinventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.InventoryProductDTO;
import com.orderinventory.dto.StoresDto;
import com.orderinventory.services.StoreService;

@RestController
@RequestMapping("/api/v1/store")
@CrossOrigin(origins = "http://localhost:4200")
public class StoreController {

		@Autowired
	   private StoreService storeService;
	

	// save store #working
	    @PostMapping("/")
	    public ResponseEntity<String> addStore(@Valid @RequestBody StoresDto storesDto) {
	        String message = storeService.saveStore(storesDto);
	        return new ResponseEntity<>(message, HttpStatus.CREATED);
	    }

	// update store by webAddress #working
	@PutMapping("/webaddress")
	public ResponseEntity<StoresDto> updateWebAddress(@Valid @RequestParam("storeId") Integer storeId,
			@RequestBody String webAddress) {
		StoresDto updatedStoreDto = storeService.updateWebAddress(storeId, webAddress);
		if (updatedStoreDto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updatedStoreDto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	    



	// get single store # working
	@GetMapping("/storeid/{storeId}")
	public ResponseEntity<StoresDto> getStoreById(@PathVariable Integer storeId) {
		StoresDto store = storeService.getStoreById(storeId);
		if (store != null) {
			return new ResponseEntity<StoresDto>(store, HttpStatus.OK);
		}
		return new ResponseEntity<StoresDto>(HttpStatus.NOT_FOUND);
	}

	// get all store #working
	@GetMapping("/all")
	public ResponseEntity<List<StoresDto>> getAllStores() {
		List<StoresDto> stores = storeService.getAllStores();
		return new ResponseEntity<List<StoresDto>>(stores, HttpStatus.OK);
	}

	// delete store for reference used don't implement
//	@DeleteMapping("/storeid/{storeId}")
//	public ResponseEntity<Boolean> deleteStoreById(@PathVariable Integer storeId){
//		storeService.deleteStoreById(storeId);
//		return new ResponseEntity<Boolean>(HttpStatus.OK) ;
//	}
	
	
	@GetMapping("/{storeId}/inventory")
    public List<InventoryProductDTO> getInventoryByStoreId(@PathVariable int storeId) {
		return storeService.getInventoryAndProductsByStoreId(storeId);
    }

}

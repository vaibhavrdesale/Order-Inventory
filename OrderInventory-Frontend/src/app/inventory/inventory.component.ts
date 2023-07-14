import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { InventoryService } from '../services/inventory.service';
import {InventoryByOrderId, InventoryDetails, InventoryDetailStoreIdDto, InventoryResponse, InventoryWithShipmentsDto, ShipmentStatusCountDTO} from '../Interface/InventoryResponse';
import { ShipmentService } from '../services/shipment.service';
@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {


  inventories: InventoryResponse[] = [];

  storeId!: number;
  inventoriesStoreId: InventoryDetailStoreIdDto[]=[];


  inventoriesShipment: InventoryWithShipmentsDto[] = [];
  deliveryAddress: string = '';


  orderId!: number;
  inventory: InventoryByOrderId|null=null;
  option!: string;



  shipmentStatus!: string;
  shipmentCounts: ShipmentStatusCountDTO[] = [];


  // orderId!: number;
  inventoryDetails: InventoryDetails[]=[];
 


  constructor(private inventoryService: InventoryService,private shipmentService: ShipmentService) { }

  ngOnInit() {
    this.fetchAllInventory();
    this.fetchInventoryByStoreId();
    this.fetchInventoryWithShipments();
    this.fetchInventoryByOrderId();
    this.fetchShipmentCounts();
  }

  searchByOption(): void {
    if (this.option === '1') {
      // get all inventories
      this.fetchAllInventory();
    } else if (this.option === '2') {
      // Shipment Status Wise Count of Customers
      this.fetchInventoryByStoreId();
    } else if (this.option==='3'){
     this.fetchInventoryWithShipments();
    } else if(this.option==='4'){
      this.fetchInventoryWithShipments();
    }
    else if(this.option==='4'){
      this.fetchInventoryByOrderId();
    }
    else if(this.option==='5'){
      this.fetchShipmentCounts();
    }
    else if(this.option==='6'){
      this.fetchInventoryDetailsByOrderId();
    }
    
  }


  // methods down below

  fetchAllInventory(): void {
    this.inventoryService.getAllInventory().subscribe(
      (inventoryList: InventoryResponse[]) => {
        console.log(inventoryList); // Log the response to check the structure
        this.inventories = inventoryList;
      },
    );
  }
  


  // fetch product data store data orderstatus
  fetchInventoryByStoreId(): void {
    this.inventoryService.getInventoryByStoreId(this.storeId).subscribe(
      (inventoryList: InventoryDetailStoreIdDto[]) => {
        this.inventoriesStoreId = inventoryList;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage ;
        console.error( errMsg);
        
      }
    );
  }
  
  isStoreIdValid(): boolean {
    // Replace this with your actual logic to check if storeId is available in the database
    // For demonstration purposes, assume storeId is not available in the database
    const storeIdsInDatabase: number[] = [2,3,4,6,8,9,12,14,17,18,19,20,21,40,50,60]; 
    return !storeIdsInDatabase.includes(this.storeId);
  }
  



  fetchInventoryWithShipments(): void {
    this.inventoryService.getInventoryWithShipmentsByDeliveryAddress(this.deliveryAddress).subscribe(
      (inventoryList: InventoryWithShipmentsDto[]) => {
        this.inventoriesShipment = inventoryList;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage ;
        console.error( errMsg);
        
        
      }
    );
  }
  



  fetchInventoryByOrderId(): void {
    this.inventoryService.getInventoryByOrderId(this.orderId).subscribe(
      (inventory: InventoryByOrderId) => {
        this.inventory = inventory;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage;
        console.error( errMsg);
        
        
      }
    );
  }
  
  isOrderIdValid(): boolean {
    const orderIdsInDatabase: number[] = [1,3,4,7,8]; 
    return !orderIdsInDatabase.includes(this.orderId);
  }
  


  fetchShipmentCounts(): void {
    this.shipmentService.countProductsByShipmentStatus(this.shipmentStatus).subscribe(
      (counts: ShipmentStatusCountDTO[]) => {
        this.shipmentCounts = counts;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage;
        console.error( errMsg);
       
        
      }
    );
  }
  



  fetchInventoryDetailsByOrderId(): void {
    this.inventoryService.getInventoryDetailsByOrderId(this.orderId).subscribe(
      (details: InventoryDetails[]) => {
        this.inventoryDetails = details;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage;
        console.error( errMsg);
        
        
      }
    );
  }
  
  isOrderIdAvailable(): boolean {
    const orderIdsInDatabase: number[] = [1, 2, 3]; // Example list of order IDs in the database
    return !orderIdsInDatabase.includes(Number(this.orderId));
  }
  
  

}

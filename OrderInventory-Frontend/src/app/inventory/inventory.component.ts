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
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch inventory. Please try again.';
        console.error('Error fetching inventory:', errMsg);
        
        // Add any error handling or notifications here
      }
    );
  }
  


  // fetch product data store data orderstatus
  fetchInventoryByStoreId(): void {
    this.inventoryService.getInventoryByStoreId(this.storeId).subscribe(
      (inventoryList: InventoryDetailStoreIdDto[]) => {
        this.inventoriesStoreId = inventoryList;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch inventory by store ID. Please try again.';
        console.error('Error fetching inventory:', errMsg);
        alert(errMsg);
        
      }
    );
  }
  



  fetchInventoryWithShipments(): void {
    this.inventoryService.getInventoryWithShipmentsByDeliveryAddress(this.deliveryAddress).subscribe(
      (inventoryList: InventoryWithShipmentsDto[]) => {
        this.inventoriesShipment = inventoryList;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch inventory with shipments. Please try again.';
        console.error('Error fetching inventory with shipments:', errMsg);
        alert(errMsg);
        
      }
    );
  }
  



  fetchInventoryByOrderId(): void {
    this.inventoryService.getInventoryByOrderId(this.orderId).subscribe(
      (inventory: InventoryByOrderId) => {
        this.inventory = inventory;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch inventory by order ID. Please try again.';
        console.error('Error fetching inventory:', errMsg);
        alert(errMsg);
        
      }
    );
  }
  


  fetchShipmentCounts(): void {
    this.shipmentService.countProductsByShipmentStatus(this.shipmentStatus).subscribe(
      (counts: ShipmentStatusCountDTO[]) => {
        this.shipmentCounts = counts;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch shipment counts. Please try again.';
        console.error('Error fetching shipment counts:', errMsg);
        alert(errMsg);
        
      }
    );
  }
  



  fetchInventoryDetailsByOrderId(): void {
    this.inventoryService.getInventoryDetailsByOrderId(this.orderId).subscribe(
      (details: InventoryDetails[]) => {
        this.inventoryDetails = details;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch inventory details. Please try again.';
        console.error('Error fetching inventory details:', errMsg);
        alert(errMsg);
        
      }
    );
  }
  
  

}

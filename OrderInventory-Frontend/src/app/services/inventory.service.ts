import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
 import {InventoryByOrderId, InventoryDetails, InventoryDetailStoreIdDto, InventoryResponse, InventoryWithShipmentsDto, ShipmentStatusCountDTO} from '../Interface/InventoryResponse';





@Injectable({
  providedIn: 'root'
})
export class InventoryService{

  
  constructor(private http: HttpClient) {}



  private baseUrl = 'http://localhost:8081/api/v1';

  // methods
 
  getAllInventory(): Observable<InventoryResponse[]> {
    return this.http.get<InventoryResponse[]>(`${this.baseUrl}/inventory`).pipe(
      map((response: InventoryResponse[]) => {
        return response.map(inventory => ({
          inventoryId: inventory.inventoryId,
          storeData: {
            storeId: inventory.storeData.storeId,

          },
          productData: {
            productId: inventory.productData.productId
          },
          productInventory: inventory.productInventory
        }));
      })
    );
  }


  getInventoryByStoreId(storeId: number): Observable<InventoryDetailStoreIdDto[]> {
    return this.http.get<InventoryDetailStoreIdDto[]>(`${this.baseUrl}/inventory/store/${storeId}`);
  }



  getInventoryWithShipmentsByDeliveryAddress(deliveryAddress: string): Observable<InventoryWithShipmentsDto[]> {
    const url: string = `${this.baseUrl}/inventory/shipment?deliveryAddress=${deliveryAddress}`;
    return this.http.get<InventoryWithShipmentsDto[]>(url);
  }




  getInventoryByOrderId(orderId: number): Observable<InventoryByOrderId> {
    return this.http.get<InventoryByOrderId>(`${this.baseUrl}/inventory/${orderId}`);
  }


  countProductsByShipmentStatus(shipmentStatus: string): Observable<ShipmentStatusCountDTO[]> {
    return this.http.get<ShipmentStatusCountDTO[]>(`${this.baseUrl}/shipment/count?shipmentStatus=${shipmentStatus}`);
  }


  getInventoryDetailsByOrderId(orderId: number): Observable<InventoryDetails[]> {
    const url = `${this.baseUrl}/inventory/${orderId}/details`;
    return this.http.get<InventoryDetails[]>(url);
  }


}

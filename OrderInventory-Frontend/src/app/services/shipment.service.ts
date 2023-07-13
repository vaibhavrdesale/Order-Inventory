import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ShipmentStatusCountDTO } from '../Interface/InventoryResponse';

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {
  private baseUrl = 'http://localhost:8081/api/v1';

  constructor(private http: HttpClient) { }

  countProductsByShipmentStatus(shipmentStatus: string): Observable<ShipmentStatusCountDTO[]> {
    return this.http.get<ShipmentStatusCountDTO[]>(`${this.baseUrl}/shipment/count?shipmentStatus=${shipmentStatus}`);
  }
}

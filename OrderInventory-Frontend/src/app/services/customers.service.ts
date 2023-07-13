import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CountDto, Customers, OrderDto, Shipment } from '../Interface/customers';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {


  private apiUrl = 'http://localhost:8081/api/v1';
  constructor(private http: HttpClient) { }

  getAllCustomers(): Observable<Customers[]> {
    return this.http.get<Customers[]>(`${this.apiUrl}/customers`);
  }

  addCustomer(customer: any): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/customers`, customer);
  }

  searchCustomersByEmail(email: string): Observable<Customers[]> {
    return this.http.get<Customers[]>(`${this.apiUrl}/customers/email-${email}`);
  }

  searchCustomersByName(fullName: string): Observable<Customers[]> {
    return this.http.get<Customers[]>(`${this.apiUrl}/customers/name/${fullName}`);
  }
  // get orders by customerid
  getOrdersByCustomerId(customerId: number): Observable<OrderDto[]> {

    return this.http.get<OrderDto[]>(`${this.apiUrl}/customers/${customerId}/order`);
  }
  updateCustomer(updatecustomer: any): Observable<object> {
    return this.http.put<object>(`${this.apiUrl}/customers`, updatecustomer);
  }
  getCustomerCountByShipmentStatus(shipmentStatus: string): Observable<CountDto> {
    const url = `${this.apiUrl}/customers/shipment/${shipmentStatus}`;
    return this.http.get<CountDto>(url);
  }

  getShipmentStatusByCustomerId(customerId: number): Observable<Shipment[]> {
    const url = `${this.apiUrl}/customerss/${customerId}`;
    return this.http.get<Shipment[]>(url);
  }

}

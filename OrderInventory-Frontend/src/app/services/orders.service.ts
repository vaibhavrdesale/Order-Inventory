import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { order } from '../Interface/order';
import { OrderStatusCount } from '../Interface/order-status-count';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  private apiUrl = 'http://localhost:8081/api/v1';

  constructor(private http: HttpClient) {}

  getAllOrders(): Observable<order[]> {
    return this.http.get<order[]>(`${this.apiUrl}/orders`);
  }

  getStatusCount=()=>{
    return this.http.get<any>(`${this.apiUrl}/orders/status`)
  }

  addOrder(order:any): Observable<object> {
    return this.http.post(`${this.apiUrl}/orders`, order);
  }

  findByStoreName(storeName: string): Observable<order[]> {
    const url = `${this.apiUrl}/orders/${storeName}`;
    return this.http.get<order[]>(url);
  }

  findByOrderStatus=(status:string)=>{
    return this.http.get(`${this.apiUrl}/orderstatus/${status}`)
  }
  
  updateOrder(updatedOrder: any): Observable<any> {
    console.log(updatedOrder);
    return this.http.put(`${this.apiUrl}/orders`, updatedOrder);
  }
  
  // public getCurrentDate=(orderId:any)=>{
 
}
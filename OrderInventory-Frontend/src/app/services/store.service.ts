import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { catchError, Observable } from 'rxjs';

import { Store, StoreIdInventories, } from '../Interface/Store';

@Injectable({

  providedIn: 'root'

})

export class StoreService {
  private apiUrl = 'http://localhost:8081/api/v1/store';

  constructor(private http: HttpClient) { }

  // fetch all stores

  getAllStores(): Observable<Store[]> {
    return this.http.get<Store[]>(`${this.apiUrl}/all`);
  }

  


  // add stores

 
  addStore(store: Store): Observable<string> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post(`${this.apiUrl}/`, store, {
      headers: headers,
      responseType: 'text'
    }) as Observable<string>;
  }

 

 
//update
updateWebAddress(storeId: number, webAddress: string, headers: HttpHeaders): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/webaddress?storeId=${storeId}`, webAddress, { headers: headers });
}



getStoreById(storeId: number): Observable<any> {
  return this.http.get(`${this.apiUrl}/storeid/${storeId}`);
}


getInventoryByStoreId(storeId: number): Observable<StoreIdInventories[]> {

  const url = `${this.apiUrl}/${storeId}/inventory`;

  return this.http.get<StoreIdInventories[]>(url);

}

}



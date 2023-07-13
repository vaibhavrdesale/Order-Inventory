import { Injectable, forwardRef } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Product, ProductsDto } from '../Interface/Product'; 
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  apiUrl = 'http://localhost:8081/api/v1';

  
  constructor(public http: HttpClient) { }
  
  
  
  //this is for the adding products

  saveProduct(product: Product): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/products`, product);
  }
 
 //this is used for the getingallproducts
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products`);
  }

//this is for updating the products  
updateProduct(product: Product): Observable<Product> {
  return this.http.put<Product>(`${this.apiUrl}/products`, product);
}

//searching the products by name
  searchByProdName(prodName: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/productname/${prodName}`);
  }

  getProductsByPriceRange(minPrice: number, maxPrice: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products/unitprice?min=${minPrice}&max=${maxPrice}`);
  }
  

  getSortedProducts(field: string): Observable<ProductsDto[]> {
    return this.http.get<ProductsDto[]>(`${this.apiUrl}/products/sort?field=${field}`);
  }
}


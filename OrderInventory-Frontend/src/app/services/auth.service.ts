import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthResponse } from '../Interface/auth-response.model';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/v1';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<AuthResponse> {
    const credentials = { email, password };
    return this.http.post<AuthResponse>(`${this.apiUrl}/authenticate`, credentials);
  }

  register(email: string, password: string): Observable<any> {
    const user = { email, password };

    return this.http.post<any>(`${this.apiUrl}/register`, user);
    
  }

  logout(): Observable<any> {
    // Clear the token from local storage
    localStorage.removeItem('token');

    // Optionally, you can also clear any other user-related data stored in the local storage

    // Add any additional logic for logout, such as notifying the backend or performing cleanup actions

    // Return an observable or promise with the logout result
    return of(true); // Example: returning a successful result
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    // Check if token exists and is not expired
    if (token) {
      const tokenExpiration = this.getTokenExpiration(token);
      return tokenExpiration > new Date();
    }
    return false;
  }

  private getTokenExpiration(token: string): Date {
    const decodedToken = this.decodeToken(token);
    if (decodedToken && decodedToken.exp) {
      // Token expiration is specified in seconds, convert it to milliseconds
      const expirationSeconds = decodedToken.exp;
      const expirationMilliseconds = expirationSeconds * 1000;
      return new Date(expirationMilliseconds);
    }
    return new Date();
  }

  private decodeToken(token: string): any {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map((c) => {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  }
}

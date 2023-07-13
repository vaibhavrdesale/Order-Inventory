import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';

interface Order {
  orderId: number;
  orderTms: string;
  customerId: number;
  orderStatus: string;
  storeId: number;
}

@Component({
  selector: 'app-update-order',
  templateUrl: './update-order.component.html',
  styleUrls: ['./update-order.component.css']
})
export class UpdateOrderComponent implements OnInit {
  order: Order = {
    orderId: 0,
    orderTms: '',
    customerId: 0,
    orderStatus: '',
    storeId: 0
  };

  updateClicked: boolean = false; // Track if the update button is clicked
  @ViewChild('orderForm') orderForm!: NgForm; // Reference to the form

  constructor(private http: HttpClient, private router: ActivatedRoute) {
    const currentDate = new Date();
    this.order.orderTms = currentDate.toISOString();
  }

  ngOnInit(): void {
    this.order.orderId = this.router.snapshot.params['orderId'];
  }

  updateOrder() {
    this.updateClicked = true; // Set updateClicked to true when the button is clicked

    if (this.orderForm.invalid) {
      return; // Stop the update process if the form is invalid
    }

    const endpoint = '/orders';
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    this.http.put('http://localhost:8081/api/v1/orders', this.order, { headers, responseType: 'text' }).subscribe(
      response => {
        console.log('Order updated successfully:', response);
        alert(response);
        // Add any further actions or notifications for success here
      },
      (error: HttpErrorResponse) => {
        if (error.error instanceof ErrorEvent) {
          // Client-side error
          console.error('An error occurred:', error.error.message);
        } else {
          // Server-side error
          console.error(`Backend returned code ${error.status}, body was:`, error.error);
        }
        // Add any error handling or notifications here
      }
    );
  }
}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrdersService } from '../services/orders.service';

@Component({
  selector: 'app-add-order',
  templateUrl: './add-order.component.html',
  styleUrls: ['./add-order.component.css']
})
export class AddOrderComponent implements OnInit {
  orderForm!: FormGroup;
  isSubmitClicked = false;

  constructor(
    private formBuilder: FormBuilder,
    private ordersService: OrdersService
  ) { }

  ngOnInit(): void {
    this.initOrderForm();
  }

  initOrderForm(): void {
    this.orderForm = this.formBuilder.group({
      orderId: [0, Validators.required],
      customerId: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      orderStatus: ['', Validators.required],
      storeId: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    });
  }

  onSubmitClick(): void {
    this.isSubmitClicked = true;
  }

  onSubmit(): void {
    if (this.orderForm.invalid) {
      return;
    }
  
    const orderData = this.orderForm.value;
    orderData.orderTms = new Date().toISOString();
    console.log(orderData);
  
    this.ordersService.addOrder(orderData).subscribe(
      (response) => {
        console.log('Order added successfully!', response);
        alert('Order added successfully!');
        this.orderForm.reset();
      },
      (error) => {
        const errMsg = error.error.errorMessage;
        console.error('Error adding order:', errMsg);
        alert(errMsg);
      }
    );
  }
}

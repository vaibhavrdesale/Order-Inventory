
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { CountDto, Customers,Customersname, OrderDto, Shipment } from '../Interface/customers';
import { Orderid } from '../Interface/orderid';
import { CustomersService } from '../services/customers.service';



@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  customers: Customers[] = [];
  pagedCustomers: Customers[] = [];
  currentPage = 1;
  itemsPerPage = 10;
  totalPages = 0;
  newCustomer: Customers = {customerId: 0,emailAddress: '', fullName: '' };
  searchEmail = '';
  addCustomerForm: FormGroup;
  option!: number ;
  orderByField: any;
  customerForm!: FormGroup;
  visiblePages: number[] = [];
  
  

  orders: Orderid[] = [];
  customerIdship: number=0;
  orderService: any;
  

  // addCustomerForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private customersService: CustomersService,private http:HttpClient) {
    this.addCustomerForm = this.formBuilder.group({
      emailAddress: ['', [Validators.required, Validators.email]], //for adding customers validations
      fullName: ['', Validators.required]
    });
    
    
  }
  

  ngOnInit(): void {
    
    this.fetchAllCustomers();
   
  }



// fetching all customers 
fetchAllCustomers(): void {
  this.customersService.getAllCustomers().subscribe(
    (data: Customers[]) => {
      this.customers = data;
      this.calculateTotalPages();
      this.setPage(1);
    },
    (error: HttpErrorResponse) => {
      const errMsg = error.error.errorMessage || 'Failed to fetch customers. Please try again.';
      console.error('Error fetching customers:', errMsg);
      alert(errMsg);
      
    }
  );
}



// this is for pagination  

  calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.customers.length / this.itemsPerPage);
  }

  setPage(page: number): void {
    const startIndex = (page - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.pagedCustomers = this.customers.slice(startIndex, endIndex);
    this.currentPage = page;
  }

  pageChanged(page: number): void {
    if (page < 1 || page > this.totalPages) {
      return;
    }
    this.setPage(page);
  }

  previousPage(): void {
    const prevPage = this.currentPage - 1;
    if (prevPage >= 1) {
      this.setPage(prevPage);
    }
  }

  nextPage(): void {
    const nextPage = this.currentPage + 1;
    if (nextPage <= this.totalPages) {
      this.setPage(nextPage);
    }
  }
  lastPage() {
    this.currentPage = this.totalPages;
    this.updatePagedCustomers();
  }
  firstPage() {
    this.currentPage = 1;
    this.updatePagedCustomers();
  }
  updatePagedCustomers(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.pagedCustomers = this.customers.slice(startIndex, endIndex);
  }
  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePagedCustomers();
      this.calculateVisiblePages();
    }
  }
  calculateVisiblePages(): void {
    const maxVisiblePages = 5; // Adjust this value as per your requirements
    const startPage = Math.max(this.currentPage - Math.floor(maxVisiblePages / 2), 1);
    const endPage = Math.min(startPage + maxVisiblePages - 1, this.totalPages);

    this.visiblePages = [];
    for (let page = startPage; page <= endPage; page++) {
      this.visiblePages.push(page);
    }
  }


//add customer 
addCustomer(): void {
  if (this.addCustomerForm.valid) {
    const newCustomer = {
      emailAddress: this.addCustomerForm.value.emailAddress,
      fullName: this.addCustomerForm.value.fullName
    };

    this.customersService.addCustomer(newCustomer).subscribe(
      () => {
        this.addCustomerForm.reset();
        this.fetchAllCustomers();
        alert('Customer added successfully!');
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to add customer. Please try again.';
        console.error('Error adding customer:', errMsg);
        alert(errMsg);
        
      }
    );
  } 
}
validateInput(controlName: string) {
  const control = this.addCustomerForm.get(controlName);
  if (control?.value) {
    if (!isNaN(control.value)) {
      control.setErrors({ numeric: true });
    } else {
      control.setErrors(null);
    }
  }
}

isNumeric(controlName: string): boolean {
  return !!this.addCustomerForm.get(controlName)?.hasError('numeric');
}


  
   

  

  
 
  
  
  
  // search by email
  searchTerm!: string;
  errorMessage!: string;
  filteredCustomers!: Customers[];
  searchFullName: any;

  searchCustomersByEmail(email: string): void {
    this.customersService.searchCustomersByEmail(email).subscribe(
      (filteredCustomers) => {
        if (filteredCustomers.length > 0) {
          this.pagedCustomers = filteredCustomers.slice(0, this.itemsPerPage);
          this.currentPage = 1;
          this.errorMessage = '';
        } else {
          this.errorMessage = 'No customers found with the provided email.';
          this.pagedCustomers = [];
        }
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Error occurred while searching customers.';
        console.error('Error searching customers by email:', errMsg);
        alert(errMsg);
        // Add any error handling or notifications here
        this.pagedCustomers = [];
      }
    );
  }
  
  
 
  // customer by name

  fullName!: string;
  fullNameInvalid: boolean = false;
  
  searchCustomersByFullName(): void {
    if (this.fullName.trim() !== '') {
      this.customersService.searchCustomersByName(this.fullName)
        .subscribe(customers => this.customers = customers);
      this.fullNameInvalid = false;
    } else {
      this.fullNameInvalid = true;
      this.customers = [];
    }
  }
  
  searchCustomers(): void {
    if (this.searchEmail.trim() !== '') {
      this.searchCustomersByEmail(this.searchEmail.trim());
    } else {
      this.errorMessage = 'Please enter an email for the search.';
      this.pagedCustomers = [];
    }
  }
  
  clearSearch(): void {
    this.searchEmail = '';
    this.searchFullName = '';
    this.errorMessage = '';
    this.setPage(1);
  }
  

  searchByOption(): void {
    if (this.option == 1) {
      // Search Customers Matching Name
      // this.getCustomersByName(this.searchName);

      this.fetchAllCustomers();
    }else if(this.option == 2){
       this.addCustomer();
    }else if(this.option == 3){
        //this.updateCustomer();
    }else if(this.option == 4){
        this.searchCustomers();
    }else if(this.option === 5){
      
    }
    else if (this.option === 6) {
      // Search Customers Matching Name
      
    } else if (this.option == 7) {
      // Shipment Status Wise Count of Customers
      // this.getCustomerCountByShipmentStatus();
    } else if(this.option == 8){
      
    }

  
    
  }
  

  //shipment status count 
  shipmentStatus!: string;
  shipmentStatusInvalid: boolean = false;
  countDto!: CountDto;

  getCustomerCount(): void {
    if (this.isValidShipmentStatus()) {
      this.customersService.getCustomerCountByShipmentStatus(this.shipmentStatus)
        .subscribe((response: CountDto) => {
          this.countDto = response;
        });
    } else {
      this.shipmentStatusInvalid = true;
    }
  }

  private isValidShipmentStatus(): boolean {
    const pattern = /^[A-Za-z\s]{3,20}$/;
    if (this.shipmentStatus && this.shipmentStatus.trim().length > 0) {
      return pattern.test(this.shipmentStatus);
    }
    return false;
  }

  

  //shipmentstatus by customer id 
customerId!: number;
shipmentStatusList: Shipment[] = [];

getShipmentStatus(): void {
  if (!this.isValidCustomerId()) {
    return;
  }

  this.customersService.getShipmentStatusByCustomerId(this.customerId)
    .subscribe(
      (response: Shipment[]) => {
        this.shipmentStatusList = response;
      },
      (error) => {
        console.error('Error occurred while fetching shipment status:', error);
      }
    );
}

isValidCustomerId(): boolean {
  if (!this.customerId || isNaN(this.customerId)) {
    console.error('Invalid customer ID');
    return false;
  }
  return true;
}


  //get orders by customer id
  
  ordersone: OrderDto[] = [];
    customerIdInvalid = false;

  getOrdersByCustomerId(): void {
    if (this.customerId === undefined) {
      this.customerIdInvalid = true;
      return;
    }

    this.customerIdInvalid = false;

    this.customersService.getOrdersByCustomerId(this.customerId).subscribe(
      (orders: OrderDto[]) => {
        this.ordersone = orders;
      },
      error => {
        console.error('Error occurred while fetching orders:', error);
        this.ordersone = [];
      }
    );
  }

  // update customers 
  updateCustomer(customer: Customers): void {
    // Make API call to update the customer using the customerService
    this.customersService.updateCustomer(customer).subscribe(
      () => {
        alert('Customer updated successfully!');
        this.fetchAllCustomers();
      },
      (error) => {
        console.error('Error updating customer:', error);
        alert('Failed to update customer. Please try again.');
      }
    );
  }
  
  isCustomerIdValid(): boolean {
    // Implement your validation logic here
    return this.customerId !== undefined && this.customerId > 0;
  }
  
  isFullNameValid(): boolean {
    // Implement your validation logic here
    return this.fullName !== undefined && this.fullName.trim() !== '';
  }
  isNumerice(value: string): boolean {
    return !isNaN(Number(value));
  }
    
}

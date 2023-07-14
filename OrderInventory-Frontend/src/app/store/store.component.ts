import { Component, OnInit } from '@angular/core';
import { StoreService } from '../services/store.service';
import { Store, StoreIdInventories } from '../Interface/Store';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  stores: Store[] = [];
  storeId!: number;
  pagedCustomers: Store[] = [];
  addStoreForm!: FormGroup;
  Store: any;


  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private storeService: StoreService
  ) {}

  ngOnInit(): void {
    this.getAllStores();
    this.storeId = this.route.snapshot.params['storeId'];

    this.addStoreForm = this.formBuilder.group({
      storeName: ['', Validators.required],
      webAddress: ['', Validators.required],
      physicalAddress: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required],
      logo: ['', Validators.required],
      logoMimeType: ['', Validators.required],
      logoFilename: ['', Validators.required],
      logoCharset: ['', Validators.required],
      logoLastUpdated: ['', Validators.required]
    });
  }

  


  currentPage = 1; // Current page number
  pageSize = 10; // Number of items per page
  paginatedStores: Store[] = []; // Array to store paginated stores
  totalPages = 0; // Total number of pages
  visiblePages: number[] = []; // Array to store visible page numbers
  
  getAllStores() {
    this.storeService.getAllStores().subscribe(
      (res: Store[]) => {
        this.stores = res;
        this.totalPages = Math.ceil(this.stores.length / this.pageSize);
        this.calculateVisiblePages();
        this.goToPage(1); // Initialize with first page
      },
      (error: HttpErrorResponse) => {
        console.error('Failed to get stores:', error.error.message);
      }
    );
  }
  
  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      const startIndex = (page - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.stores.length);
      this.paginatedStores = this.stores.slice(startIndex, endIndex);
    }
  }
  
  calculateVisiblePages() {
    const totalPagesToShow = 5; // Change this number to adjust the number of visible pages
    const half = Math.floor(totalPagesToShow / 2);
    let startPage = Math.max(1, this.currentPage - half);
    let endPage = Math.min(startPage + totalPagesToShow - 1, this.totalPages);
  
    if (endPage - startPage + 1 < totalPagesToShow) {
      startPage = Math.max(1, endPage - totalPagesToShow + 1);
    }
  
    this.visiblePages = [];
    for (let i = startPage; i <= endPage; i++) {
      this.visiblePages.push(i);
    }
  }
  
  
  
  



  

 
  
  

  option!: number;

  searchByOption(): void {
    if (this.option == 1) {
      this.getAllStores();
    } else if (this.option == 2) {
      this.addStore();
    } else if (this.option == 3) {
      this.updateWebAddress();
    } else if (this.option == 4) {
      this.getStoreById();
    }
    else if(this.option==5){
      this.getInventoryByStoreId();
    }
  }

  addStore() {
    if (this.addStoreForm.invalid) {
      return;
    }

    const store: Store = this.addStoreForm.value;

    this.storeService.addStore(store).subscribe(
      (response: any) => {
        console.log('Store added successfully:', response);
        // Handle success, show a notification, or navigate to another page
        alert('Store added successfully');
      },
      (error: HttpErrorResponse) => {
        console.error('Failed to add store:', error.error.message);
        // Handle error, show an error message, or perform other actions
      }
    );
  }

 
webAddress!: string;

updateWebAddress() {
  if (!this.storeId || !this.webAddress) {
    console.error('Store ID and web address are required');
    return;
  }

  const headers = new HttpHeaders()
    .set('Content-Type', 'text/plain')
    .set('Access-Control-Allow-Origin', '*');

  this.storeService
    .updateWebAddress(this.storeId, this.webAddress, headers)
    .subscribe(
      (response: any) => {
        console.log('Web address updated successfully:', response);
        alert('Web address updated successfully');
        // Handle success, show a notification, or navigate to another page
      },
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          console.error('Store not found');
          // Handle not found error
        } else {
          console.error('Failed to update web address:', error.error);
          // Handle other errors
        }
      }
    );
}

store: any;
getStoreById(): void {
  if (!this.storeId) {
    console.error('Store ID is required');
    return;
  }

  this.storeService.getStoreById(this.storeId).subscribe(
    (response: any) => {
      this.Store = response;
      console.log('Store retrieved successfully:', this.Store);
      
    },
    (error: HttpErrorResponse) => {
      const errMsg = error.error.errorMessage || 'Failed to retrieve store. Please try again.';
      console.error('Failed to retrieve store:', errMsg);
      alert(errMsg);
      
    }
  );
}





 

inventories: StoreIdInventories[] = [];
errorMessage: string = '';
getInventoryByStoreId(): void {
  if (!this.storeId) {
    this.errorMessage = 'Please enter a store ID.';
    return;
  }

  this.errorMessage = ''; // Clear any previous error message

  this.storeService.getInventoryByStoreId(this.storeId).subscribe(
    (data: StoreIdInventories[]) => {
      this.inventories = data;
    },
    (error: HttpErrorResponse) => {
      const errMsg = error.error.errorMessage || 'Failed to fetch inventory data. Please try again.';
      console.log('Error fetching inventory data:', errMsg);
      alert(errMsg);
      
    }
  );
}




}

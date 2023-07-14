
import { Component } from '@angular/core';
import { OrdersService } from '../services/orders.service';
import { OrderStatusCount } from '../Interface/order-status-count';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  orders: any[] = [];
  totalPagesArray: number[] = [];
  pagedOrders: any[] = [];
  pageSize = 10;
  currentPage = 1;
  totalOrders = 0;
  totalPages = 0;
  orderStatusCounts: OrderStatusCount[] = [];
  option!: number;
  inputData!: any;
  isDeactivate: boolean = true;
  status: any = {
    cancelledCount: 0,
    completeCount: 0
  };

  constructor(private ordersService: OrdersService) { }

  ngOnInit() {
  
  }

  getAllOrders = () => {
    this.ordersService.getAllOrders().subscribe(
      (res: any) => {
        this.orders = [...res];
        this.totalOrders = this.orders.length;
        this.totalPages = Math.ceil(this.totalOrders / this.pageSize);
        this.goToPage(this.currentPage);
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch orders. Please try again.';
        console.error('Error fetching orders:', errMsg);
        alert(errMsg);
        // Add any error handling or notifications here
      }
    );
  }


  search = () => {

    if (this.inputData == "") {
      this.getAllOrders();
    } else {
      switch (Number(this.option)) {
        case 4:
          this.ordersService.findByOrderStatus(this.inputData.toUpperCase()).subscribe(
            (response: any) => {
              this.orders = [...response];
              this.totalOrders = this.orders.length;
              this.totalPages = Math.ceil(this.totalOrders / this.pageSize);
              this.goToPage(this.currentPage);
            },
            (error: HttpErrorResponse) => {
              const errMsg = error.error.errorMessage || 'Failed to retrieve orders by status. Please try again.';
              console.error('Error retrieving orders by status:', errMsg);
              alert(errMsg);

            }
          );
          break;
        case 3:
          this.ordersService.findByStoreName(this.inputData).subscribe(
            (response: any) => {
              this.orders = [...response];
              this.totalOrders = this.orders.length;
              this.totalPages = Math.ceil(this.totalOrders / this.pageSize);
              this.goToPage(this.currentPage);
            },
            // (error: HttpErrorResponse) => {
            //   const errMsg = error.error.errorMessage || 'Failed to retrieve orders by store name. Please try again.';
            //   console.error('Error retrieving orders by store name:', errMsg);
            //   alert(errMsg);
            // }
          );
          break;
          case 4: this.getAllOrders()
          break;
          default: alert("please select only from this options")
          break;
      }
    }
  }

  selectOption():void{
      if (this.option == 4) {
        this.getAllOrders()
      }
  }

  getStatusCount = () => {
    this.ordersService.getStatusCount().subscribe(
      (data: any) => {
        this.status = data;
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to retrieve status count. Please try again.';
        console.error('Error retrieving status count:', errMsg);
        alert(errMsg);

      }
    );
  }


  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      const startIndex = (page - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      this.pagedOrders = this.orders.slice(startIndex, endIndex);

      this.totalPagesArray = Array.from({ length: this.totalPages }, (_, i) => i + 1);
    }
  }

  get visiblePages(): number[] {
    const startPage = Math.max(1, this.currentPage - 2);
    const endPage = Math.min(startPage + 4, this.totalPages);
    return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  }
}



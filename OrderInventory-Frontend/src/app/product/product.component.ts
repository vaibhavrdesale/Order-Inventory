import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product, ProductsDto } from '../Interface/Product';
import { ProductService } from '../services/product.service'; 

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
 
  productForm: FormGroup;
  productsList: Product[] = [];
  
  option!: number;
  minUnitPrice!: number;
  maxUnitPrice!: number;
  currentPage = 1;
  itemsPerPage = 10;
  totalPages = 0;
  orderByField!: string ;

  //required for search by prodname
  productName: string = '';
  searchTerm: string = '';
  searchResults: Product[] = [];
  pagedProducts: Product[] = [];
  visiblePages: number[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private productService: ProductService
  ) {
    this.productForm = this.formBuilder.group({
      productId: [0, Validators.required],
      productName: ['', Validators.required],
      unitPrice: [null, Validators.required],
      productDetails: ['']
    });
  }

  ngOnInit(): void {
    this.getProducts();
  }

  saveProduct(): void {
    if (this.productForm.valid) {
      const product: Product = this.productForm.value;
  
      // Check if the product already exists
      if (this.productAlreadyExists(product)) {
        console.log('Product already exists');
        alert('Product already exists');
        return; // Stop further execution
      }
  
      this.productService.saveProduct(product).subscribe(
        () => {
          console.log('Product saved successfully');
          alert('Product saved successfully');
        },
        (error: HttpErrorResponse) => {
          const errMsg = error.error.errorMessage || 'Error saving product. Please try again.';
          console.error('Error saving product:', errMsg);
          alert(errMsg);
          // Add any error handling or notifications here
        }
      );
    } else {
      // Mark all form controls as touched to trigger validation errors
      this.productForm.markAllAsTouched();
    }
  }
  
  productAlreadyExists(product: Product): boolean {
    const existingProduct = this.productsList.find(p => p.productName === product.productName);
    return !!existingProduct;
  }

  
  
  // For getting all the products  
  getProducts(): void {
    this.productService.getProducts().subscribe(
      (response: any) => {
        this.productsList = [...response];
        this.calculateTotalPages();
        this.setPage(1);
        this.calculateVisiblePages();
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch products. Please try again.';
        console.error('Error fetching products:', errMsg);
        alert(errMsg);
      }
    );
  }

  // Pagination
  calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.productsList.length / this.itemsPerPage);
  }

  setPage(page: number): void {
    const startIndex = (page - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.pagedProducts = this.productsList.slice(startIndex, endIndex);
    this.currentPage = page;
  }

  firstPage(): void {
    this.setPage(1);
  }

  previousPage(): void {
    const prevPage = this.currentPage - 1;
    if (prevPage >= 1) {
      this.setPage(prevPage);
    }
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.setPage(page);
      this.calculateVisiblePages();
    }
  }

  nextPage(): void {
    const nextPage = this.currentPage + 1;
    if (nextPage <= this.totalPages) {
      this.setPage(nextPage);
    }
  }

  lastPage(): void {
    this.setPage(this.totalPages);
  }

  calculateVisiblePages(): void {
    const startPage = Math.max(1, this.currentPage - 3);
    const endPage = Math.min(startPage + 6, this.totalPages);
    this.visiblePages = Array.from({ length: endPage - startPage + 1 }, (_, i) => i + startPage);
  }

  // search by product name method.
  searchByProdName(): void {
    if (this.productName) {
      this.productService.searchByProdName(this.productName).subscribe(
        (products) => {
          this.productsList = products;
        },
        (error: HttpErrorResponse) => {
          const errMsg = error.error.errorMessage || 'Failed to fetch products. Please try again.';
          console.error('Error fetching products:', errMsg);
          alert(errMsg);
          // Add any error handling or notifications here
        }
      );
    } else {
      this.getProducts();
    }
  }
  

//for update 
product: Product = {
    productId: 0,
    productName: '',
    unitPrice: 0,
    productDetails: ''
  };
  updateProduct(): void {
    if (this.productForm.valid) {
      const updatedProduct: Product = this.productForm.value;
      this.productService.updateProduct(updatedProduct).subscribe(
        () => {
          // Product updated successfully
          alert("Product updated successfully.");
        },
        (error: HttpErrorResponse) => {
          const errMsg = error.error.errorMessage || 'Error updating product. Please try again.';
          console.error('Error updating product:', errMsg);
          alert(errMsg);
          // Add any error handling or notifications here
        }
      );
    } else {
      // Mark all form controls as touched to trigger validation errors
      this.productForm.markAllAsTouched();
    }
  }
  
  isNonNumeric(controlName: string): boolean {
    const control = this.productForm.get(controlName);
    return control?.value && isNaN(control.value);
  }

  isNumeric(controlName: string): boolean {
    const control = this.productForm.get(controlName);
    return control?.value && !isNaN(control.value);
  }
  
  

// search by pricerange 

productsListByPrice: Product[]=[]

searchByPriceRange(): void {
  console.log("in price range");
  
  this.productService
    .getProductsByPriceRange(this.minUnitPrice, this.maxUnitPrice)
    .subscribe(
      (response: any) => {
        this.productsListByPrice = [...response]
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to fetch products. Please try again.';
        console.error('Error fetching products:', errMsg);
        alert(errMsg);
        
      }
    );
}


//this wiil be used for the switching one opraration to the another
  searchByOption(): void {
    if (this.option === 1) {
      this.saveProduct()
    } else if (this.option === 2) {
      // Update products by object
    } else if (this.option === 3) {
      //Search Products Matching product name
      this.searchByProdName();
    } else if (this.option === 4) {
      //Fetch List of Products matching between min and max unit price
    } else if (this.option === 5) {
      //Get list of products order by query field
    } else if(this.option==6){
      this.getProducts();
    }
  }

// this methd is perform the operation called sorting of the productscolumn
  sortlist: ProductsDto[] = [];

  sort(field: string) {
    this.productService.getSortedProducts(field).subscribe(
      (sortedProducts: any) => {
        this.sortlist = [...sortedProducts];
      },
      (error: HttpErrorResponse) => {
        const errMsg = error.error.errorMessage || 'Failed to sort products. Please try again.';
        console.error('Error sorting products:', errMsg);
        alert(errMsg);
      }
    );
  }

  invalidInputs: { [key: string]: boolean } = {};

validateInput(inputName: string) {
  const inputControl = this.productForm.get(inputName);
  if (inputControl?.value) {
    if (
      (inputName === 'productName' && !isNaN(inputControl.value)) ||
      (inputName === 'unitPrice' && isNaN(inputControl.value))
    ) {
      this.invalidInputs[inputName] = true;
    } else {
      this.invalidInputs[inputName] = false;
    }
  }
}

isInputInvalid(inputName: string): boolean {
  return this.productForm.get(inputName)!.touched && this.invalidInputs[inputName];
}

  

}

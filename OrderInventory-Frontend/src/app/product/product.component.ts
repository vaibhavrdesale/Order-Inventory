
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
  
  orderByField!: string ;

  //required for serach ny prodname
  productName: string = '';
  searchTerm: string = '';
  searchResults: Product[] = [];

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
      this.productService.saveProduct(product).subscribe(
        () => {
          console.log('Product saved successfully:');
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
  
  
//for getting all the products  
getProducts(): void {
  this.productService.getProducts().subscribe(
    (response: any) => {
      this.productsList = [...response];
    },
    (error: HttpErrorResponse) => {
      const errMsg = error.error.errorMessage || 'Failed to fetch products. Please try again.';
      console.error('Error fetching products:', errMsg);
      alert(errMsg);
      
    }
  );
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

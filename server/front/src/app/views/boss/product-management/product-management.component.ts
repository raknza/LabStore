import { ProductManagementService } from './product-management.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';
import { CategoryDashboardService } from '../category-dashboard/category-dashboard.service';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.scss']
})
export class ProductManagementComponent implements OnInit {

  buyProductModal: any;

  allProduct: any;
  allCategory: any;

  newProductName: string;
  newProductCategory: string;
  newProductDescription: string;
  constructor(private productManagementService: ProductManagementService,private categoryDashboardService: CategoryDashboardService) { }

  ngOnInit() {

    this.productManagementService.getAllProduct().subscribe(
      response => {
        this.allProduct = response['Products'];
      }
    );
    this.categoryDashboardService.getAllCatrgory().subscribe(
      response => {
        this.allCategory = response['Categories'];
      }
    );
  }

  addProduct(){
    this.productManagementService.addProduct(this.newProductName , this.newProductCategory, this.newProductDescription).subscribe(
      response => {
        this.allProduct = response['Products'];
        window.location.reload();
      }
    );
  }

  onCategoryChange(event: any){
    console.log(event.target.value);
    this.newProductCategory = event.target.value
  }
  onNameChange(event: any){
    this.newProductName = event.target.value
  }

  onDescriptionChange(event: any){
    this.newProductDescription = event.target.value
  }

}

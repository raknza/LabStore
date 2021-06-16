import { ShelfProductManagementService } from './shelf-product-management.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';
import { PurchaseDashboardService } from '../purchase-dashboard/purchase-dashboard.service';

@Component({
  selector: 'app-shelf--product-management',
  templateUrl: './shelf-product-management.component.html',
  styleUrls: ['./shelf-product-management.component.scss']
})
export class ShelfProductManagementComponent implements OnInit {

  buyProductModal: any;
  allPurchase: any;
  allShelfProduct: any;

  newShelfProductPurchaseId: any;
  newShelfProductPrice: any;
  newShelfProductCount: any;

  constructor(private shelfProductManagementService: ShelfProductManagementService,private purchaseDashboardService: PurchaseDashboardService) { }

  ngOnInit() {

    this.shelfProductManagementService.getAllShelfProduct().subscribe(
      response => {
        this.allShelfProduct = response['Shelf_Products'];
        console.log(this.allShelfProduct);
      }
    );
    this.purchaseDashboardService.getAllPurchase().subscribe(
      response => {
        this.allPurchase = response['Purchases'];
      }
    );
  }
  onPriceChange(event :any){
    this.newShelfProductPrice = event.target.value;
  }
  onCountChange(event :any){
    this.newShelfProductCount = event.target.value;
  }
  onPurchaseChange(event :any){
    this.newShelfProductPurchaseId = event.target.value;
  }
  addShelfProduct(){
    this.shelfProductManagementService.addShelfProduct(this.newShelfProductPurchaseId , this.newShelfProductPrice, this.newShelfProductCount).subscribe(
      response => {
        window.location.reload();
      }
    );
  }
}

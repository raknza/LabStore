import { PurchaseDashboardService } from './purchase-dashboard.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductManagementService } from '../product-management/product-management.service';

@Component({
  selector: 'app-purchase-dashboard',
  templateUrl: './purchase-dashboard.component.html',
  styleUrls: ['./purchase-dashboard.component.scss']
})
export class PurchaseDashboardComponent implements OnInit {

  buyProductModal: any;
  allPurchase: any;
  allProduct: any;

  addPurchaseName: string;
  addPurchaseCount: string;
  addPurchaseCost: string;

  constructor(private purchaseDashboardService: PurchaseDashboardService, private productManagementService: ProductManagementService) { }

  ngOnInit() {

    this.purchaseDashboardService.getAllPurchase().subscribe(
      response => {
        this.allPurchase = response['Purchases'];
      }
    );

    this.productManagementService.getAllProduct().subscribe(
      response => {
        this.allProduct = response['Products'];
      }
    );
  }

  onProductChange(event: any){
    console.log(event.target.value);
    this.addPurchaseName = event.target.value;
  }
  onCostChange(event: any){
    console.log(event.target.value);
    this.addPurchaseCost = event.target.value;
  }
  onCountChange(event: any){
    console.log(event.target.value);
    this.addPurchaseCount = event.target.value;
  }

  addPurchase(){
    this.purchaseDashboardService.addPurchase(this.addPurchaseName , this.addPurchaseCost, this.addPurchaseCount).subscribe(
      response => {
        window.location.reload();
      }
    );
  }

}

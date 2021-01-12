import { ProductDetailService } from './product-detail.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit {
  @ViewChild('buyProductModal', {static: true}) public buyProductModal: ModalDirective;

  allPurchases: any;
  onSelectedPurchase: any;

  constructor(private productDetailService: ProductDetailService) { }

  ngOnInit() {
    this.productDetailService.getAllPurchase().subscribe(
      response => {
        this.allPurchases = response['Purchases'];
      }
    );
  }
  selectProduct(product: any) {
    console.log(product);
    this.onSelectedPurchase = product;
  }

}

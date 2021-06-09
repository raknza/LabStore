import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-purchase-dashboard',
  templateUrl: './purchase-dashboard.component.html',
  styleUrls: ['./purchase-dashboard.component.scss']
})
export class PurchaseDashboardComponent implements OnInit {

  buyProductModal: any;
  

  constructor() { }

  ngOnInit() {
  }

}

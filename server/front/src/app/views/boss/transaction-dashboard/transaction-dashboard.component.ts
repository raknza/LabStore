import { TransactionDashboardService } from './transaction-dashboard.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-transaction-dashboard',
  templateUrl: './transaction-dashboard.component.html',
  styleUrls: ['./transaction-dashboard.component.scss']
})
export class TransactionDashboardComponent implements OnInit {

  allTransaction: any;

  constructor(private transactionDashboardService: TransactionDashboardService) { }

  ngOnInit() {

    this.transactionDashboardService.getAllTransaction().subscribe(
      response => {
        this.allTransaction = response['Transactions'];
      }
    );
  }

}

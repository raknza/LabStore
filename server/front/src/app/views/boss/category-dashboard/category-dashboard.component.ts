import { ModalDirective } from 'ngx-bootstrap/modal';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-category-dashboard',
  templateUrl: './category-dashboard.component.html',
  styleUrls: ['./category-dashboard.component.scss']
})
export class CategoryDashboardComponent implements OnInit {


  @ViewChild('categoryAddModal', {static: true}) public categoryAddModal: ModalDirective;

  selectedCategory: any;
  assessments: any;
  categories: any;
  metricsCategoryEditModal: any;
  metricsCategoryDeleteModal: any;

  constructor() { }

  ngOnInit() {
  }

}

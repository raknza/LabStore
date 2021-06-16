import { CategoryDashboardService } from './category-dashboard.service';
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
  metricsCategoryEditModal: any;
  metricsCategoryDeleteModal: any;
  allCategory: any;

  newCategory: string;
  

  constructor(private categoryDashboardService: CategoryDashboardService) { }

  ngOnInit() {

    this.categoryDashboardService.getAllCatrgory().subscribe(
      response => {
        this.allCategory = response['Categories'];
      }
    );

  }

  addCategory() {
    this.categoryDashboardService.addCatrgory(this.newCategory).subscribe(
      response => {
        window.location.reload();
      }
    );
  }

  onChange(category: any){
    this.newCategory = category.target.value
  }



}

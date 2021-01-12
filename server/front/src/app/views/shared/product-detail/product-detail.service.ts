import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailService {

  GET_ALL_PURCHASE_API = environment.SERVER_URL + '/webapi/purchase/all';

  constructor(private http: HttpClient) { }

  getAllPurchase(): Observable<any> {
    return this.http.get(this.GET_ALL_PURCHASE_API);
  }

}

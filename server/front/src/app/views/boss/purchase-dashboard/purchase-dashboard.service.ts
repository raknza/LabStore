import { User } from '../../../models/user';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { JwtService } from '../../../services/jwt.service';


const options = ({
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
  })
});

@Injectable({
  providedIn: 'root'
})
export class PurchaseDashboardService {

  GET_ALL_PURCHASE = environment.SERVER_URL + '/webapi/purchase/all';
  CREATE_PURCHASE = environment.SERVER_URL + '/webapi/purchase/new?';

  constructor(private http: HttpClient,private jwtService?: JwtService) { }

  getAllPurchase(): Observable<any> {

    return this.http.get(this.GET_ALL_PURCHASE );
  }

  addPurchase(productName: string,cost: string, count: string): Observable<any> {

    const user = new User(this.jwtService);
    let params = new HttpParams()
    .append('cost', cost)
    .append('productName', productName)
    .append('count', count)
    .append('username', user.getUsername());

    return this.http.post<any>(this.CREATE_PURCHASE + params.toString(),{} ,options);
  }


}

import { User } from './../../../models/user';
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
export class ProductDetailService {

  GET_ALL_PURCHASE_API = environment.SERVER_URL + '/webapi/shelfProduct/all';
  BUY_PRODUCT_API = environment.SERVER_URL + '/webapi/transaction/new?';

  constructor(private http: HttpClient,private jwtService?: JwtService) { }

  getAllPurchase(): Observable<any> {
    return this.http.get(this.GET_ALL_PURCHASE_API);
  }

  buyProduct(shelfProductId: number, count: number): Observable<any> {
    /*const params = new FormData();
    const param = new Query
    const user = new User(this.jwtService);
    params.append("shelfProduct", shelfProductId.toString());
    params.append("count", count.toString());
    params.append("username", user.getUsername());*/
    const user = new User(this.jwtService);
    let params = new HttpParams()
    .append('shelfProduct', shelfProductId.toString())
    .append('count', count.toString())
    .append('username', user.getUsername());

    console.log(params.toString());
    return this.http.post<any>(this.BUY_PRODUCT_API + params.toString() , {}, options);
  }

}

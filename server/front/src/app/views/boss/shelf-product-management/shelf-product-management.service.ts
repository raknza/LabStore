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
export class ShelfProductManagementService {

  GET_ALL_SHELF_PRODUCT = environment.SERVER_URL + '/webapi/shelfProduct/all';
  CREATE_SHELF_PRODUCT = environment.SERVER_URL + '/webapi/shelfProduct/new?';

  constructor(private http: HttpClient,private jwtService?: JwtService) { }

  getAllShelfProduct(): Observable<any> {

    return this.http.get(this.GET_ALL_SHELF_PRODUCT );
  }

  addShelfProduct(purchaseId: string,price: string, count: string): Observable<any> {

    const user = new User(this.jwtService);
    let params = new HttpParams()
    .append('purchaseId', purchaseId)
    .append('count', count)
    .append('price', price);

    return this.http.post<any>(this.CREATE_SHELF_PRODUCT + params.toString(),{} ,options);
  }


}

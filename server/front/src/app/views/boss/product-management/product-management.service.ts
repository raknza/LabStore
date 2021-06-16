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
export class ProductManagementService {

  GET_ALL_PRODUCT = environment.SERVER_URL + '/webapi/product/all';
  ADD_PRODUCT = environment.SERVER_URL + '/webapi/product/new?';

  constructor(private http: HttpClient,private jwtService?: JwtService) { }

  getAllProduct(): Observable<any> {

    return this.http.get(this.GET_ALL_PRODUCT );
  }

  addProduct(name: string,category: string, description: string): Observable<any> {

    let params = new HttpParams()
    .append('category', category)
    .append('name', name)
    .append('description', description);

    return this.http.post<any>(this.ADD_PRODUCT + params.toString(),{} ,options);
  }


}

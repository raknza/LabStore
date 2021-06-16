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
export class CategoryDashboardService {

  GET_ALL_CATEGORY = environment.SERVER_URL + '/webapi/category/all';
  ADD_CATEGORY = environment.SERVER_URL + '/webapi/category/new?';

  constructor(private http: HttpClient,private jwtService?: JwtService) { }

  getAllCatrgory(): Observable<any> {

    return this.http.get(this.GET_ALL_CATEGORY );
  }

  addCatrgory(category: string): Observable<any> {

    let params = new HttpParams()
    .append('name', category);

    return this.http.post<any>(this.ADD_CATEGORY + params.toString(),{} ,options);
  }


}

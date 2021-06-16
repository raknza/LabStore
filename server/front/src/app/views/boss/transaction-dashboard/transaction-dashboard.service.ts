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
export class TransactionDashboardService {

  GET_TRANSACTION_BY_BOSS = environment.SERVER_URL + '/webapi/transaction/get/all/boss?';

  constructor(private http: HttpClient,private jwtService?: JwtService) { }

  getAllTransaction(): Observable<any> {

    const user = new User(this.jwtService);
    let params = new HttpParams()
    .append('username', user.getUsername());

    return this.http.get(this.GET_TRANSACTION_BY_BOSS + params.toString());
  }



}

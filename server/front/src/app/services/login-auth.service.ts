import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { JwtService } from './jwt.service';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const options = ({
  headers: new HttpHeaders({
    'Content-Type': 'application/x-www-form-urlencoded',
  })
});
@Injectable({
  providedIn: 'root'
})
export class LoginAuthService {

  LOGIN_URL: string = environment.SERVER_URL + '/LoginAuth';
  AUTH_URL: string = environment.SERVER_URL + '/webapi/auth/checkLogin';
  constructor(private http: HttpClient, private jwtService: JwtService) { }

  public Login(username, password): Observable<any> {
    let params = new HttpParams();
    params = params.append('username', username);
    params = params.append('password', password);
    return this.http.post<any>(this.LOGIN_URL, params, options);
  }

  public isLoginByBoss(): Observable<any> {
    const token = this.jwtService.getToken();
    let params = new HttpParams();
    params = params.append('token', token);
    return this.http.post(this.AUTH_URL, params, options);
  }

  public isLoginByCustomer(): Observable<any> {
    const token = this.jwtService.getToken();
    let params = new HttpParams();
    params = params.append('token', token);
    return this.http.post(this.AUTH_URL, params, options);
  }

  public logout() {
    return this.jwtService.removeToken();
  }

}

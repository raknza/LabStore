import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { LoginAuthService } from './login-auth.service';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class CanActiveCustomerService implements CanActivate {
  constructor(public router: Router, private loginAuth: LoginAuthService, private jwtService: JwtService) { }

  canActivate(): Promise<boolean> | boolean {
    return new Promise((resolve) => {
      this.loginAuth.isLoginByCustomer().subscribe((response) => {
        if (response.isLogin && response.isCustomer) {
          resolve(true);
        } else {
          this.jwtService.removeToken();
          this.router.navigate(['login']);
          resolve(false);
        }
      });
    });
  }
}

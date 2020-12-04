
import { JwtService } from '../services/jwt.service';
export class User {
  public isBoss: boolean = false;
  public isCustomer: boolean = false;
  private username: string;
  private name: string;

  constructor(private jwtService: JwtService) {
    const decodedToken = jwtService.getDecodedToken();
    if (decodedToken.sub === 'BOSS') {
      this.isBoss = true;
    } else if (decodedToken.sub === 'CUSTOMER') {
      this.isCustomer = true;
    }

    this.username = decodedToken.aud;
    this.name = decodedToken.name;
  }

  public getUsername(): string {
    return this.username;
  }

  public getName(): string {
    return this.name;
  }

}

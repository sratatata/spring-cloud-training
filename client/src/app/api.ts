import { environment } from '../environments/environment';

export class Api {

  usersMicroservice = `${environment.baseUrl}/users-microservice`
  oauthServer = `${this.usersMicroservice}/oauth/token`
  users = `${this.usersMicroservice}/users`
  activeUser = `${this.users}/active`

}

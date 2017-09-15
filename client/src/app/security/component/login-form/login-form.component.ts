import {Component} from '@angular/core';
import {SecurityService} from '../../service/security.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {

  username: string
  password: string
  loginError: boolean
  pendingRequest = false

  constructor(private securityService: SecurityService, private router: Router) {
  }

  login() {
    this.pendingRequest = true
    this.securityService.login(this.username, this.password)
      .subscribe(() => {
        this.router.navigateByUrl('delivery')
      }, () => {
        this.loginError = true
        this.pendingRequest = false
      })
  }

}

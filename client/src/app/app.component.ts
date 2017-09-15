import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {SecurityService} from "./security/service/security.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  router: Router

  constructor(private securityService: SecurityService, translate: TranslateService, router: Router) {
    translate.setDefaultLang('en');
    translate.use('en');
    this.router = router
  }

  logout() {
    this.securityService.logout();
    this.router.navigateByUrl("login")
  }

}

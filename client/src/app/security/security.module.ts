import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginFormComponent} from './component/login-form/login-form.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from "@angular/common/http";
import {SecurityService} from './service/security.service';
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    TranslateModule,
    HttpClientModule
  ],
  declarations: [
    LoginFormComponent
  ],
  providers: [
    SecurityService
  ],
  exports: [
    LoginFormComponent
  ]
})
export class SecurityModule {
}

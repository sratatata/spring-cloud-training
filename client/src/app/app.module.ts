import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BaseModule} from "./base/base.module";
import {SecurityModule} from './security/security.module';
import {MainModule} from "./main/main.module";
import {AppComponent} from './app.component';
import {Api} from './api';
import {SecurityGuard} from './security/security.guard';
import {routerModule} from './app.routing';
import {HTTP_INTERCEPTORS, HttpClient} from "@angular/common/http";
import {SecurityInterceptor} from "./security/security.interceptor";
import {TokenInterceptor} from "./security/token.interceptor";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    BaseModule,
    SecurityModule,
    MainModule,
    routerModule
  ],
  providers: [
    Api,
    SecurityGuard,
    {provide: HTTP_INTERCEPTORS, useClass: SecurityInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

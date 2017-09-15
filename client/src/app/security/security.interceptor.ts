import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http'
import {Router} from "@angular/router";
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';

@Injectable()
export class SecurityInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .do(event => {}, error => {
        if (error instanceof HttpErrorResponse && (error.status === 401 || error.status === 403)) {
          this.router.navigateByUrl('login')
        }
      })
  }

}

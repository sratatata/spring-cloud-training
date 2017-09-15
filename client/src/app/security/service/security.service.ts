import {Injectable} from '@angular/core';
import {URLSearchParams} from '@angular/http';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Api} from '../../api';
import {User} from '../model/User';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/mergeMap';

@Injectable()
export class SecurityService {

  user: Observable<User>

  private userKey = 'user'
  private tokenKey = 'token'
  private userSubject = new BehaviorSubject<User>(null)
  private token = ""

  constructor(private http: HttpClient, private api: Api) {
    this.user = this.userSubject.asObservable()
    this.restoreSecurityContext()
  }

  private restoreSecurityContext() {
    let token = sessionStorage.getItem(this.tokenKey)
    if (token) {
      this.setToken(token)
    }
    let user = sessionStorage.getItem(this.userKey)
    if (user) {
      this.userSubject.next(JSON.parse(user))
    }
  }

  getUser(): User {
    return this.userSubject.getValue()
  }

  login(username: string, password: string): Observable<User> {
    let credentials = this.prepareCredentials(username, password)
    return this.retrieveToken(credentials)
      .do(token => this.setToken(token))
      .flatMap(() => this.retrieveUser())
      .do(user => this.setUser(user))
  }

  private prepareCredentials(username: string, password: string): string {
    let payload = new URLSearchParams()
    payload.set('username', username)
    payload.set('password', password)
    payload.set('grant_type', 'password')
    payload.set('client_id', 'training')
    return payload.toString()
  }

  private retrieveToken(payload: string): Observable<string> {
    let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post(this.api.oauthServer, payload, {headers})
      .map(json => json['access_token'])
  }

  private setToken(token: string) {
    sessionStorage.setItem(this.tokenKey, token)
    this.token = token
  }

  private retrieveUser(): Observable<User> {
    return this.http.get(this.api.activeUser)
      .map(json => new User(json))
  }

  private setUser(user: User) {
    sessionStorage.setItem(this.userKey, JSON.stringify(user))
    this.userSubject.next(user)
  }

  logout() {
    this.removeToken()
    this.removeUser()
    this.userSubject.next(null)
  }

  private removeToken() {
    sessionStorage.removeItem(this.tokenKey)
    this.token = ""
  }

  private removeUser() {
    sessionStorage.removeItem(this.userKey)
  }

  public getToken() {
    return this.token
  }

}

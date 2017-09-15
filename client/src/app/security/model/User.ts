export class User {

  name: string
  authorities = []

  constructor(json) {
    this.name = json.login
    json['authorities'].forEach(authority => this.authorities.push(authority.name.replace('ROLE_', '')))
  }

}

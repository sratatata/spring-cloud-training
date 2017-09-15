import {Route, RouterModule} from '@angular/router';
import {LoginFormComponent} from './security/component/login-form/login-form.component'
import {MainPanelComponent} from "./main/component/main-panel/main-panel.component";
import {SecurityGuard} from './security/security.guard';

const routesConfig: [Route] = [
  {
    path: 'login', component: LoginFormComponent
  },
  {
    path: '', canActivate: [SecurityGuard], children: [
    {
      path: 'main', component: MainPanelComponent
    }
  ]
  },
  {
    path: '**', redirectTo: 'main'
  }
]

export const routerModule = RouterModule.forRoot(routesConfig)

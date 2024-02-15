import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponentComponent } from './home-component/home-component.component';
import { LoginComponentComponent } from './login-component/login-component.component';
import { RegisterComponentComponent } from './register-component/register-component.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

const routes: Routes = [
  {path : "login", component : LoginComponentComponent},
  {path : "signup", component : RegisterComponentComponent},
  {path : "reset", component : ResetPasswordComponent},
  {path : "", redirectTo : "login", pathMatch : "full"},
  {path : "home/:customerUsername", component : HomeComponentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

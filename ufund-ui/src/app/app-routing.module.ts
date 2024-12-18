import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NeedsComponent } from './needs/needs.component';
import { LogoutComponent } from './logout/logout.component';
import { FundingbasketComponent } from './fundingbasket/fundingbasket.component';
import { HelperDashboardComponent } from './helper-dashboard/helper-dashboard.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'needs', component: NeedsComponent,},
  { path: 'helper-dashboard', component: HelperDashboardComponent},
  { path: 'admin-dashboard', component: AdminDashboardComponent},
  { path: 'fundingbasket', component: FundingbasketComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

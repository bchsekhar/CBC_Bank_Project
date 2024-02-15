import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerServiceService } from '../service/customer-service.service';
import swal from 'sweetalert';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrl: './login-component.component.css'
})
export class LoginComponentComponent {

  customerUsername!: string;
  customerPassword!: string;
  
  constructor(private customerService : CustomerServiceService, private router : Router) {}

  login() {
    
    console.log(this.customerUsername, this.customerPassword);

    this.customerService.login(this.customerUsername, this.customerPassword).subscribe(data => {
      if (data) {
        this.router.navigate(['home', this.customerUsername]);
      } else {
        //alert('Invalid username or password');
        swal({
          title: 'Invalid username or password',
          icon: 'warning'
        });
      }
    }, error => {
      console.error('Error during login:', error);
    }
    );
   
  }
}

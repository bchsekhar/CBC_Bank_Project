import { CustomerServiceService } from './../service/customer-service.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from '../model/customer';

@Component({
  selector: 'app-register-component',
  templateUrl: './register-component.component.html',
  styleUrl: './register-component.component.css'
})
export class RegisterComponentComponent  implements OnInit {

  customerName?: any
  customerNumber?: any;
  customerUsername?: any ;
  customerPassword?: any ;
  usernameExists?: boolean = false;

  constructor(private customerService: CustomerServiceService, private router: Router) {}
  ngOnInit(): void {
   this.signup
  }

  checkUsername() {
    this.customerService.checkUsername(this.customerUsername).subscribe(
      exists => this.usernameExists = exists,
      error => console.error(error)
    );
  }
  msg?: string;
  signup(cust:Customer) {
    if (this.usernameExists) {
      console.warn('Username already exists. Please choose a different username.');
    } else {
      this.customerService.signup(cust).subscribe( data => {

        this.msg = 'saved customer with Id '+ cust.customerId
        console.log(data);
        console.log(this.msg)
        this.router.navigate(['/login'])
      },
        (error) => {
          this.msg='error with customer'+error
          console.log(error)
        }
      );
    }
  }
}

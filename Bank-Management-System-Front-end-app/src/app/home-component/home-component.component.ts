import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerServiceService } from '../service/customer-service.service';
import { Customer } from '../model/customer';
import { FormControl, NgForm, Validators } from '@angular/forms';
import { Transaction } from '../model/transaction';

@Component({
  selector: 'app-home-component',
  templateUrl: './home-component.component.html',
  styleUrl: './home-component.component.css'
})
export class HomeComponentComponent implements OnInit {

  customerUsername! : string;
  depositAmmount! : string;
  
  withdrawAmmount! : number;
  billAmmount! : number;
  biller!: string;
  theirRef! : string;
  yourRef! : string;
  msg! :string;
  receiverAccountNumber! : number;
  recieverName! : string;
  amount! : number;
  invalidInput: boolean = false;

  @ViewChild('myForm') myForm!: NgForm;
  @ViewChild('DepositFrom') DepositFrom!: NgForm;
  @ViewChild('WithdrawFrom') WithdrawFrom!: NgForm;

  customer : Customer = new Customer();
  transactions! : Transaction[];

  constructor(private customerService : CustomerServiceService, private actRouter : ActivatedRoute, private router : Router) {}
  
  ngOnInit(): void {
    this.customerUsername = this.actRouter.snapshot.params["customerUsername"];
    this.customerService.getEmployee(this.customerUsername).subscribe( data => {
      this.customer = data;
      
    },
    error => {
      console.error('Error fetching customer data:', error);
    }
    
    );
    this.getTransactions();
    this.transactions = this.transactions.reverse();
    
  }

  onDeposit() {
    this.customerService.depositAmmount(this.customerUsername, this.depositAmmount).subscribe(data => {
      //this.router.navigate(['home', this.customerUsername]); 
      this.DepositFrom.resetForm();   
    }
    );
  }

  onWithdraw() {
    this.customerService.withdrawAmmount(this.customerUsername, this.withdrawAmmount).subscribe(data => {
      //this.router.navigate(['home', this.customerUsername]);
      this.WithdrawFrom.resetForm();
    }
    );
  }

  onBillPay() {
    this.customerService.billPaymentAmmount(this.customerUsername, this.billAmmount, this.biller, this.theirRef, this.yourRef).subscribe(data => {
      //this.router.navigate(['home', this.customerUsername]);      
    }
    );
  }

  onTransferFund(){    
    this.customerService.fundTransfer(this.customerUsername,this.receiverAccountNumber,this.amount).subscribe(data=>{
      this.myForm.resetForm();
    },error => {
      console.error('Error on transfering fund:', error);
    }
    );
  }

  getTransactions() {
    this.customerService.getTransactionList(this.customerUsername).subscribe( data => {
      this.transactions = data;
     
    });
  }
  
 
}

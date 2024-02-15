import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../model/customer';
import { Observable } from 'rxjs';
import { Transaction } from '../model/transaction';

@Injectable({
  providedIn: 'root'
})
export class CustomerServiceService {

   url = 'http://localhost:8098/customer';
  

  // allCustomer = 'get';
  // getByUsername = '/get/{username}'
  // geTransaction = '/gettransactions'
  // deleteUsername = '/delete/{username}'
  // transactionByUser = '/transactions/{username}'
  // billPayment= '/billpayment/{username}'

  constructor(private httpClient : HttpClient) { }

  
  private baseURL1 : String = "http://lapitrd144.cbcsl.ad:8099/login";
  private baseURL2 : String = "http://lapitrd144.cbcsl.ad:8099/list";
  private baseURL3 : String = "http://192.168.4.170:8099/deposit";
  private baseURL4 : String = "http://192.168.4.170:8099/withdraw";
  private baseURL5 : String = "http://192.168.4.170:8099/customer/gettransactions"; 
  private baseURL6 : String = "http://192.168.4.170:8099/billpayment";

  signup(customer: Customer): Observable<Customer> {
    const url = `${this.url}/signup`;
    return this.httpClient.post<Customer>(url, customer);
  }

  login(username: string, password: string): Observable<boolean> {
    return this.httpClient.post<boolean>(`${this.url}/login`, { username, password });
  }

  getEmployee(username : string) : Observable<Customer> {
    return this.httpClient.get<Customer>(`${this.url}/get/${username}`);
  }

  depositAmmount(username: string, amount : any) : Observable<String> {
    return this.httpClient.put<String>(`${this.url}/deposit?username=${username}&amount=${amount}`, {});

  } 

  withdrawAmmount(username: string, amount : number) : Observable<String> {
    return this.httpClient.put<String>(`${this.url}/withdraw?username=${username}&amount=${amount}`, {});
  } 

  billPaymentAmmount(username: string, amount : number, biller : string, theirRef : string, yourRef : string) : Observable<String> {
    return this.httpClient.put<String>(`${this.url}/billpayment/${username}?amount=${amount}`, {biller, theirRef, yourRef});
  }

  fundTransfer(senderUsername: string, receiverAccountNo: number, amount: number): Observable<string> {
    return this.httpClient.put<string>(`${this.url}/fundTransfer?senderUsername=${senderUsername}&receiverAccountNo=${receiverAccountNo}&amount=${amount}`, null);
  }

  getTransactionList(username: string) : Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>(`${this.url}/transactions/${username}`);
  }

  
  checkUsername(username: string): Observable<boolean> {
    const url = `${this.url}/${username}`;
    return this.httpClient.get<boolean>(url);
  }
}

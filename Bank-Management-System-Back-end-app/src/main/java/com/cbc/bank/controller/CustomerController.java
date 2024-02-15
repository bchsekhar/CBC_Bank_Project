package com.cbc.bank.controller;

import com.cbc.bank.Service.CustomerService;
import com.cbc.bank.model.BillerWrapper;
import com.cbc.bank.model.Customer;
import com.cbc.bank.model.CustomerWrapper;
import com.cbc.bank.model.Transaction;
import com.cbc.bank.utils.BankUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Customer customer) {
        try {
            customerService.signup(customer);
            return BankUtils.getResponseEntity("Successfully registered", HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.BAD_REQUEST);
    }   

    @PostMapping("/login") //  http://localhost:8099/customer/login
    public boolean login(@RequestBody CustomerWrapper customerWrapper) {
    	boolean a = false;
        try {
            boolean reply = customerService.login(customerWrapper);
            if(reply) {
            	return true;
            }
            //return BankUtils.getResponseEntity("Login Success", HttpStatus.OK);
            else {
            	a = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);
		return a;
    }

    @GetMapping("/get")
    public List<Customer> getAllUsers(){
        try {
            return customerService.getAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/get/{username}")
    public Optional<Customer> getAllUsers(@PathVariable("username") String username){
        try {
            return customerService.getByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
        }
       return Optional.empty();
    }

    @GetMapping("/{username}")
    public boolean checkUsernameExists(@PathVariable("username") String username) {
        return customerService.checkUsernameExists(username);
    }

    @GetMapping("/gettransactions")
    public List<Transaction> getAllTransaction(){
        try {
            return customerService.getAllTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    @GetMapping("/transactions/{username}")
    public List<Transaction> getTransactionsByCustomerId(@PathVariable("username") String username) {
        try {
            return customerService.getTransactionsByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @PostMapping("/transactions/{username}")
    public ResponseEntity<String> saveTransaction(@PathVariable String username, @RequestBody Transaction customerTransaction) {
        try {
            customerService.saveTransaction(username, customerTransaction);
            return BankUtils.getResponseEntity("Successfully saved", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/billpayment/{username}")
    public ResponseEntity<String> withdrawAmount(@PathVariable String username, @RequestParam float amount, @RequestBody BillerWrapper billerWrapper) {
        try {
            customerService.billPayment(username, amount, billerWrapper);
            return BankUtils.getResponseEntity("Payment Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        try {
            customerService.deleteUser(username);
            return BankUtils.getResponseEntity("Successfully deleted" + username, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/deposit")//  http://localhost:8099/customer/deposit?username=nethsara-cbc&amount=3000
    public ResponseEntity<String> deposite(@RequestParam String username, @RequestParam float amount) {
        try {
            customerService.calculateDeposite(username, amount);
            return BankUtils.getResponseEntity("Deposit Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/withdraw")//  http://localhost:8099/customer/withdraw?username=nethsara-cbc&amount=3000
    public ResponseEntity<String> withdraw(@RequestParam String username, @RequestParam float amount) {

        try {
            customerService.calculateWithdraw(username, amount);
            return BankUtils.getResponseEntity("Withdraw Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);

    }
//------------------------------------------------------------
@PutMapping(path="/updateCustomerPassword")
public ResponseEntity<String> updateCustomerPassword(@RequestParam String username, @RequestBody Map<String, String> requestBody) {
    String currentPassword = requestBody.get("currentPassword");
    String newPassword = requestBody.get("newPassword");
    return customerService.updatePassword(username, currentPassword, newPassword);
}

    @PutMapping(path="/fundTransfer")
    public ResponseEntity<String>fundTransfer(@RequestParam String senderUsername, @RequestParam Integer receiverAccountNo,@RequestParam float amount) {
        return customerService.transferFund(senderUsername, receiverAccountNo, amount);
    }
}

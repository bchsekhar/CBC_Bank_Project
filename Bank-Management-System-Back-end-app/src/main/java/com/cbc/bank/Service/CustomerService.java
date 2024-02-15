package com.cbc.bank.Service;

import com.cbc.bank.model.BillerWrapper;
import com.cbc.bank.model.Customer;
import com.cbc.bank.model.CustomerWrapper;
import com.cbc.bank.model.Transaction;
import com.cbc.bank.repository.CustomerRepository;
import com.cbc.bank.repository.TransactionRepository;
import com.cbc.bank.utils.BankUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public void signup(Customer customer) {
        customerRepository.save(customer);
    }

    public boolean login(@RequestBody CustomerWrapper customerWrapper) {

        try {
            String username = customerWrapper.getUsername();
            String password = customerWrapper.getPassword();
            Customer customer = customerRepository.findByCustomerUsername(username)
                    .orElse(null);

            if (customer != null && customer.getCustomerPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteUser(String username) {
        try {
            customerRepository.deleteByCustomerUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactionsByUsername(String username) {
        try {
            return transactionRepository.findByCustomerCustomerUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> saveTransaction(String username, Transaction customerTransaction) {
        try {
            Optional<Customer> customer = customerRepository.findByCustomerUsername(username);
            customerTransaction.setCustomer(customer.get());
            transactionRepository.save(customerTransaction);
            return BankUtils.getResponseEntity("Transaction added", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Enter Valid Data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public List<Customer> getAll() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> getByUsername(String username) {
        try {
            return customerRepository.findByCustomerUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUsernameExists(String username) {
        try {
            return customerRepository.existsByCustomerUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Transaction> getAllTransaction() {
        try {
            return transactionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String billPayment(String username, float amount, BillerWrapper billerWrapper) {
        try {
            Optional<Customer> customer = customerRepository.findByCustomerUsername(username);
            if (customer != null) {

                if (customer.get().getCustomerBalance() >= amount) {
                    float newBalance = customer.get().getCustomerBalance() - amount;
                    customer.get().setCustomerBalance(newBalance);
                    customerRepository.save(customer.get());

                    // Create and save a withdrawal transaction
                    Transaction billPay = new Transaction();
                    billPay.setCustomer(customer.get());
                    billPay.setTransactionType("Bill Payment to "+billerWrapper.getBiller());
                    billPay.setAmount((double) amount);
                    billPay.setTransactionDate(new Date());
                    System.out.println("Ammount :" + amount);
                    System.out.println(billerWrapper.toString());
                    transactionRepository.save(billPay);
                    return "Bill Payment";
                } else {
                    return  "Insufficient balance for paymnet";
                }
            } else {
                return "Customer not found";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //-----------------------------------Chamindu-------
    public boolean validateCustomer(int customerId) {
        try {
            if(customerRepository.findById(customerId).isEmpty()) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateDeposite(@RequestParam String username, float amount) {

        try {
            Optional<Customer> customerOptional = customerRepository.findByCustomerUsername(username);
            Customer customer = customerOptional.get();
            float newBalance = customer.getCustomerBalance() + amount;
            customer.setCustomerBalance(newBalance);
            customerRepository.save(customer);

            // Create and save a withdrawal transaction
            Transaction deposit = new Transaction();
            deposit.setCustomer(customerOptional.get());
            deposit.setTransactionType("Deposit");
            deposit.setAmount((double) amount);
            deposit.setTransactionDate(new Date());

            transactionRepository.save(deposit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateWithdraw(@RequestParam String username, float amount) {

        try {
            Optional<Customer> customerOptional = customerRepository.findByCustomerUsername(username);
            Customer customer = customerOptional.get();
            float newBalance = customer.getCustomerBalance() - amount;
            customer.setCustomerBalance(newBalance);
            customerRepository.save(customer);

            // Create and save a withdrawal transaction
            Transaction withdraw = new Transaction();
            withdraw.setCustomer(customerOptional.get());
            withdraw.setTransactionType("Withdraw");
            withdraw.setAmount((double) amount);
            withdraw.setTransactionDate(new Date());

            transactionRepository.save(withdraw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //-----------------------------Janani--------------

    public ResponseEntity<String> updatePassword(String username,String currentPassword,String newPassword) {

        try {
            Optional<Customer> cust=customerRepository.findByCustomerUsername(username);

            if(cust!=null && cust.get().getCustomerPassword().equals(currentPassword)) {
                cust.get().setCustomerPassword(newPassword);
                customerRepository.save(cust.get());
                return BankUtils.getResponseEntity("Customer Password updated sucessfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("Wrong customer username or Password", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> transferFund( String senderUsername,  Integer receiverAccountNo, float amount) {

        try {
            Optional<Customer> findSenderAccount=customerRepository.findByCustomerUsername(senderUsername);
            Customer findReceiverAccount=customerRepository.findById(receiverAccountNo).orElse(null);

            float senderBalance=findSenderAccount.get().getCustomerBalance()-amount;


            if(findSenderAccount!=null && findReceiverAccount!=null && senderBalance>0) {
                findSenderAccount.get().setCustomerBalance(senderBalance);
                customerRepository.save(findSenderAccount.get());

                float receiverBalance=findReceiverAccount.getCustomerBalance()+amount;
                findReceiverAccount.setCustomerBalance(receiverBalance);
                customerRepository.save(findReceiverAccount);
                return BankUtils.getResponseEntity("Fund was transfered successfully.", HttpStatus.OK);

            }else if(findReceiverAccount==null) {
                return BankUtils.getResponseEntity("Wrong Receiver Account number. Enter correct Account Number.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
//             Create and save a withdrawal transaction
            Transaction deposit = new Transaction();
            deposit.setCustomer(findReceiverAccount);
            deposit.setTransactionType("Fund Transfer");
            deposit.setAmount((double) amount);
            deposit.setTransactionDate(new Date());

            transactionRepository.save(deposit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankUtils.getResponseEntity("You have insufficient balance to transfer fund.", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

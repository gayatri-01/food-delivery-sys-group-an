package com.group.an.customerService.controller;

//import com.group.an.customerService.entity.CartItem;
//import com.group.an.customerService.entity.Customer;
import com.group.an.customerService.service.CustomerService;
import com.group.an.dataService.models.CartItem;
import com.group.an.dataService.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloFromCustomer(){
        return "Hello From Customer";
    }

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> fetchAllCustomers(){
        return customerService.fetchAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Optional<Customer> getCustomerById(@PathVariable("customerId") int customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/customers/{customerId}")
    public Customer updateCustomerById(@PathVariable("customerId") int customerId, @RequestBody Customer customer){
        return customerService.updateCustomerById(customerId, customer);
    }

    @GetMapping("/customers/{customerId}/cart")
    public List<CartItem> viewCustomerCart(@PathVariable("customerId") int customerId){
        return customerService.viewCustomerCart(customerId);
    }

    @PostMapping("/customers/{customerId}/cart")
    public List<CartItem> addCustomerCart(@PathVariable("customerId") int customerId, @RequestBody List<CartItem> cartItems){
        return customerService.addCustomerCart(customerId, cartItems);
    }

    @DeleteMapping("/customers/{customerId}/cart/{cartItemId}")
    public CartItem deleteCartItemById(@PathVariable("customerId") int customerId, @PathVariable("cartItemId") int cartItemId){
        return customerService.deleteCartItemById(customerId, cartItemId);
    }

}

package com.group.an.customerService.service;

//import com.group.an.customerService.entity.CartItem;
import com.group.an.dataService.models.CartItem;
import com.group.an.dataService.models.Customer;
//import com.group.an.customerService.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> fetchAllCustomers();                                         // fetch all customers
    Optional<Customer> getCustomerById(int customerId);                         // get a customer by id
    Customer updateCustomerById(int customerId, Customer customer);             // updating customer by id
    List<CartItem> viewCustomerCart(int customerId);                            // view customer's cart items
    List<CartItem> addCustomerCart(int customerId, List<CartItem> cartItems);   // add cart items to customer's cart
    CartItem deleteCartItemById(int customerId, int cartItemId);                // delete a cart item by id

}

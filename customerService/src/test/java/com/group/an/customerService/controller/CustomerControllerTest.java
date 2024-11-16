package com.group.an.customerService.controller;

//import com.group.an.customerService.service.CustomerService;
import com.group.an.customerService.service.CustomerServiceImpl;
import com.group.an.dataService.models.CartItem;
import com.group.an.dataService.models.Customer;
import com.group.an.dataService.models.PaymentDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerServiceImpl customerServiceImpl;
    Customer customer;
    List<Customer> customers;
    CartItem cartItem;
    List<CartItem> cartItems;
    PaymentDetail paymentDetail;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        when(customerServiceImpl.saveCustomer(any(Customer.class))).thenReturn(customer);
        when(customerServiceImpl.fetchAllCustomers()).thenReturn(customers);
        when(customerServiceImpl.getCustomerById(133)).thenReturn(Optional.ofNullable(customer));
        when(customerServiceImpl.updateCustomerById(133, customer)).thenReturn(customer);
        when(customerServiceImpl.viewCustomerCart(133)).thenReturn(cartItems);
        when(customerServiceImpl.addCustomerCart(133, cartItems)).thenReturn(cartItems);
        when(customerServiceImpl.deleteCartItemById(133, 12)).thenReturn(cartItem);
    }

    @Test
    void testSaveCustomer(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<Customer> response = customerController.saveCustomer(customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testFetchAllCustomers(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<List<Customer>> response = customerController.fetchAllCustomers();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    void testGetCustomerById(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<Optional<Customer>> response = customerController.getCustomerById(123);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    void testUpdateCustomerById(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<Customer> response = customerController.updateCustomerById(123, customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    void testViewCustomerCart(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<List<CartItem>> response = customerController.viewCustomerCart(123);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
    }

    void testAddCustomerCart(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<List<CartItem>> response = customerController.addCustomerCart(123, cartItems);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
    }

    void testDeleteCartItemById(){
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        ResponseEntity<CartItem> response = customerController.deleteCartItemById(123, 12);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItem, response.getBody());
    }
}

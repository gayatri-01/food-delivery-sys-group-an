package com.group.an.customerService.service;

import com.group.an.authService.security.JwtTokenUtil;
import com.group.an.dataService.models.CartItem;
import com.group.an.dataService.models.Customer;
import com.group.an.dataService.models.PaymentDetail;
import com.group.an.dataService.models.Role;
import com.group.an.dataService.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerServiceImpl;
    Customer customer;
    List<Customer> customers;
    CartItem cartItem;
    List<CartItem> cartItems;
    PaymentDetail paymentDetail;

    private MockedStatic<JwtTokenUtil> mockedAuthUtils;

    @AfterEach
    public void tearDown(){
        mockedAuthUtils.close();
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockedAuthUtils = mockStatic(JwtTokenUtil.class);
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.findById(133)).thenReturn(Optional.ofNullable(customer));
    }

    @Test
    void testSaveCustomer(){
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(123);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.CUSTOMER);
        cartItem = new CartItem(29,30,31);
        cartItems = Arrays.asList(cartItem);
        paymentDetail = new PaymentDetail(32, "NVB", "KSA969", LocalDateTime.now(), 89992005L, "Mitesh4567767");
        customer = new Customer(133, "Mitesh", "Mitesh@gmail.com", "Mitesh4567767", 1023456789L, "Malaysia", cartItems, paymentDetail, true);
        customers = Arrays.asList(customer);
        Customer customer1 = customerServiceImpl.saveCustomer(customer);
        assertEquals(customer, customer1);
    }

    @Test
    void testFetchAllCustomers(){
        List<Customer> listOfCustomers = customerServiceImpl.fetchAllCustomers();
        assertEquals(customers, listOfCustomers);
    }

    void testGetCustomerById(){
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(123);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.CUSTOMER);
        Optional<Customer> returnedCustomer = customerServiceImpl.getCustomerById(123);
        assertEquals(customer, returnedCustomer);
    }
}

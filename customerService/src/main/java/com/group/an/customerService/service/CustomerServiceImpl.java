package com.group.an.customerService.service;

//import com.group.an.customerService.entity.CartItem;
//import com.group.an.customerService.entity.Customer;
import com.group.an.authService.security.JwtTokenUtil;
//import com.group.an.customerService.repository.CustomerRepository;
import com.group.an.dataService.repositories.CustomerRepository;
import com.group.an.dataService.models.CartItem;
import com.group.an.dataService.models.Customer;
import com.group.an.dataService.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public Customer saveCustomer(Customer customer) {
        if(Objects.nonNull(customer.getName()) && !"".equalsIgnoreCase(customer.getName()) &&
                Objects.nonNull(customer.getEmail()) && !"".equalsIgnoreCase(customer.getEmail()) &&
                Objects.nonNull(customer.getPasswordHash()) && !"".equalsIgnoreCase(customer.getPasswordHash()) &&
                Objects.nonNull(customer.getContactNumber()) && (customer.getContactNumber().toString().length()==10) &&
                Objects.nonNull(customer.getDeliveryAddress()) && !"".equalsIgnoreCase(customer.getDeliveryAddress())) {
            return customerRepository.save(customer);
        }

        return customerRepository.save(customer);
    }

    public List<Customer> fetchAllCustomers() {
        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if(!role.equals(Role.ADMIN)){
            throw new AccessDeniedException("You are not authorized. Only Admins can perform this.");
        }
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(int customerId) {
        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (!userId.equals(customerId)){
            throw new AccessDeniedException("You are not authorized. You can view details only for your customer id : "+ userId);
        }
        return customerRepository.findById(customerId);
    }

    public Customer updateCustomerById(int customerId, Customer customer) {
        Optional<Customer> customer1 = customerRepository.findById(customerId);
        if(customer1.isPresent()){
            Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
            Role role = JwtTokenUtil.getRoleFromAuthContext();
            if (!userId.equals(customerId)){
                throw new AccessDeniedException("You are not authorized. You can update only your details");
            }
            Customer originalCustomer = customer1.get();
            //if(originalCustomer.getActive()){
                if(Objects.nonNull(customer.getName()) && !"".equalsIgnoreCase(customer.getName())){
                    originalCustomer.setName(customer.getName());
                }
                if(Objects.nonNull(customer.getEmail()) && !"".equalsIgnoreCase(customer.getEmail())){
                    originalCustomer.setEmail(customer.getEmail());
                }
                if(Objects.nonNull(customer.getPasswordHash()) && !"".equalsIgnoreCase(customer.getPasswordHash())){
                    originalCustomer.setPasswordHash(customer.getPasswordHash());
                }
                if(Objects.nonNull(customer.getContactNumber()) && (customer.getContactNumber().toString().length()==10)){
                    originalCustomer.setContactNumber(customer.getContactNumber());
                }
                if(Objects.nonNull(customer.getDeliveryAddress()) && !"".equalsIgnoreCase(customer.getDeliveryAddress())){
                    originalCustomer.setDeliveryAddress(customer.getDeliveryAddress());
                }
                if(Objects.nonNull(customer.getCart())){
                    originalCustomer.setCart(customer.getCart());
                }
                if(Objects.nonNull(customer.getPaymentDetail())){
                    originalCustomer.setPaymentDetail(customer.getPaymentDetail());
                }
                originalCustomer.setActive(customer.isActive());
                return customerRepository.save(originalCustomer);
           // }
        }
        return null;
    }

    public List<CartItem> viewCustomerCart(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
            Role role = JwtTokenUtil.getRoleFromAuthContext();
            if (!userId.equals(customerId)){
                throw new AccessDeniedException("You are not authorized. You can view only your cart");
            }
            Customer targetCustomer = customer.get();
            return targetCustomer.getCart();
        }
        return List.of();
    }

    public List<CartItem> addCustomerCart(int customerId, List<CartItem> cartItems) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
            Role role = JwtTokenUtil.getRoleFromAuthContext();
            if (!userId.equals(customerId)){
                throw new AccessDeniedException("You are not authorized. You can add to only your cart");
            }
            Customer originalCustomer = customer.get();
            List<CartItem> originalCartItems = originalCustomer.getCart();
            //Arrays.stream(originalCustomer.getCart()).toList().add(cartItems.get(i));
            originalCartItems.addAll(cartItems);
            customerRepository.save(originalCustomer);
            return originalCustomer.getCart();
            }
        return List.of();
    }


    //to delete a cart item by customer id and cart id
    public CartItem deleteCartItemById(int customerId, int cartItemId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
            Role role = JwtTokenUtil.getRoleFromAuthContext();
            if (!userId.equals(customerId)){
                throw new AccessDeniedException("You are not authorized. You can delete only from your cart");
            }
            Customer originalCustomer = customer.get();
            List<CartItem> originalCartItems = originalCustomer.getCart();
            CartItem tempCartItem = new CartItem();
            for (int i=0; i<originalCartItems.size(); i++) {
                if(originalCartItems.get(i).getCartItemId() == cartItemId){
                    tempCartItem = originalCartItems.get(i);
                    originalCartItems.remove(i);
                    i--;
                }
            }
            originalCustomer.setCart(originalCartItems);
            customerRepository.save(originalCustomer);
            return tempCartItem;
        }
        return null;
    }
}

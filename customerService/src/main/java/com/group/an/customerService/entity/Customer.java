package com.group.an.customerService.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

    @Id
    private int customerId;
    private String name;
    private String email;
    private String passwordHash;
    private Long contactNumber;
    private String deliveryAddress;
    private CartItem[] cart;
    private PaymentDetail paymentDetail;
    private boolean isActive;

    public Customer(){

    }

    public Customer(int customerId, String name, String email, String passwordHash, Long contactNumber, String deliveryAddress, CartItem[] cart, PaymentDetail paymentDetail, Boolean isActive) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.contactNumber = contactNumber;
        this.deliveryAddress = deliveryAddress;
        this.cart = cart;
        this.paymentDetail = paymentDetail;
        this.isActive = isActive;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public CartItem[] getCart() {
        return cart;
    }

    public void setCart(CartItem[] cart) {
        this.cart = cart;
    }

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

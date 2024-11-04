package com.group.an.customerService.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class PaymentDetail {

    @Id
    private int paymentDetailId;
    private String cardType;
    private String cardNumber;
    private Date cardExpiry;
    private Long upiNumber;
    private String upiId;

    public PaymentDetail(){
    }

    public PaymentDetail(int paymentDetailId, String cardType, String cardNumber, Date cardExpiry, Long upiNumber, String upiId) {
        this.paymentDetailId = paymentDetailId;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.upiNumber = upiNumber;
        this.upiId = upiId;
    }

    public int getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(int paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(Date cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public Long getUpiNumber() {
        return upiNumber;
    }

    public void setUpiNumber(Long upiNumber) {
        this.upiNumber = upiNumber;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}

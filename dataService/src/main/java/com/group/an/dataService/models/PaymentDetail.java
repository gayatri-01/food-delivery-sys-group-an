package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

public class PaymentDetail {
    @Id
    private int paymentId;
    private String cardType;
    private String cardNumber;
    private LocalDateTime cardExpiry;
    private long upiNumber;
    private String upiId;

    public PaymentDetail(int paymentId, String cardType, String cardNumber, LocalDateTime cardExpiry, long upiNumber, String upiId) {
        this.paymentId = paymentId;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.upiNumber = upiNumber;
        this.upiId = upiId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public LocalDateTime getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(LocalDateTime cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public long getUpiNumber() {
        return upiNumber;
    }

    public void setUpiNumber(long upiNumber) {
        this.upiNumber = upiNumber;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}

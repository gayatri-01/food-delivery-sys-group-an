package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "customers")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private int customerId;
    private String name;
    private String email;
    private String passwordHash;
    private long contactNumber;
    private String deliveryAddress;
    private List<CartItem> cart;
    private PaymentDetail paymentDetail;
    private boolean isActive;
}

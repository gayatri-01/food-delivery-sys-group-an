package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Integer orderId;//Set by Service class when order is created
    private Integer customerId; //Initially given by Customer
    private Integer restaurantId; //Initially given by Customer
    private Integer personnelId; //Updated by deliveryPersonnel while accepting the order
    private LocalDateTime orderedAt; //Set by Service class when order is created
    private LocalDateTime deliveredAt;//Set by Service class when deliveryPersonnel updates deliveryStatus to DELIVERED
    private Double price; //Initially given by Customer
    private OrderStatus orderStatus;//Service class initially set to OrderStatus.PLACED, then updated by restaurantOwner
    private DeliveryStatus deliveryStatus;// Service class initially set to DeliveryStatus.PENDING, then updated by deliveryPersonnel
    private List<OrderItem> orderItems; //Initially given by Customer
}



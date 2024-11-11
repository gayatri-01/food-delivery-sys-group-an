package com.group.an.orderService.requestModel;

import com.group.an.dataService.models.OrderItem;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer customerId;
    private Integer restaurantId;
    private Double price;
    private List<OrderItem> orderItems;
}

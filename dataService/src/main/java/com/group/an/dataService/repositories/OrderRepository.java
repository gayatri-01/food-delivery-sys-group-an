package com.group.an.dataService.repositories;

import com.group.an.dataService.models.DeliveryStatus;
import com.group.an.dataService.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository <Order,Integer> {

    List<Order> findByCustomerId(int customerId);

    Order findByOrderId(int orderId);

    List<Order> findByRestaurantId(int restaurantId);

    List<Order> findByDeliveryStatus(DeliveryStatus deliveryStatus);

}

package com.group.an.orderService.controller;

import com.group.an.authService.responseModel.AuthResponse;
import com.group.an.dataService.models.DeliveryStatus;
import com.group.an.dataService.models.Order;
import com.group.an.dataService.models.OrderStatus;
import com.group.an.orderService.exception.ResourceNotFoundException;
import com.group.an.orderService.requestModel.DeliveryStatusUpdateRequest;
import com.group.an.orderService.requestModel.OrderRequest;
import com.group.an.orderService.requestModel.OrderStatusUpdateRequest;
import com.group.an.orderService.responseModel.OrderAndDeliveryStatusResponse;
import com.group.an.orderService.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "jwtAuth")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "APIs for Admin")
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @Tag(name = "APIs for Customer and Admin")
    @Operation(summary = "Get orders by customer ID", description = "Retrieve a list of orders for a specific customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<List<Order>> getOrdersByCustomerId(
            @RequestParam @Parameter(example = "1") int customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @Tag(name = "APIs for Customer and Admin")
    @Operation(summary = "Get order and delivery status", description = "Retrieve the status of an order and its delivery",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved order and delivery status"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<OrderAndDeliveryStatusResponse> getOrderAndDeliveryStatus(
            @RequestParam @Parameter(example = "1") int orderId) {
        OrderAndDeliveryStatusResponse orderAndDeliveryStatusResponse = orderService.getOrderAndDeliveryStatus(orderId);
        return new ResponseEntity<>(orderAndDeliveryStatusResponse, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @Tag(name = "APIs for Customer and Admin")
    @Operation(summary = "Place an order", description = "Place a new order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order request payload",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"customerId\": 1, \"restaurantId\": 2, \"price\": 100, \"orderItems\": [ { \"orderItemId\": 1, \"menuItemId\": 2, \"quantity\": 4, \"itemPrice\": 25 } ] }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order successfully placed"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order placedOrder = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/restaurants")
    @PreAuthorize("hasRole('RESTAURANT_OWNER') or hasRole('ADMIN')")
    @Tag(name = "APIs for Restaurant Owner and Admin")
    @Operation(summary = "Get orders by restaurant ID", description = "Retrieve a list of orders for a specific restaurant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(
            @RequestParam @Parameter(example = "2") int restaurantId) {
        List<Order> orders = orderService.getOrdersByRestaurantId(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/orderStatus")
    @PreAuthorize("hasRole('RESTAURANT_OWNER') or hasRole('ADMIN')")
    @Tag(name = "APIs for Restaurant Owner and Admin")
    @Operation(summary = "Update order status", description = "Update the status of an order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order status update request payload",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderStatusUpdateRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"orderId\": 1, \"orderStatus\": \"DELIVERED\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order status successfully updated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<Order> updateOrderStatus(@RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest) {
        Order updatedOrder = orderService.updateOrderStatus(orderStatusUpdateRequest.getOrderId(), orderStatusUpdateRequest.getOrderStatus());
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @GetMapping("/deliveryStatus")
    @PreAuthorize("hasRole('DELIVERY_PERSONNEL') or hasRole('ADMIN')")
    @Tag(name = "APIs for Delivery Personnel and Admin")
    @Operation(summary = "Get orders by delivery status", description = "Retrieve a list of orders by delivery status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<List<Order>> getOrdersByDeliveryStatus(
            @RequestParam @Parameter(example = "PENDING", schema = @Schema(implementation = DeliveryStatus.class)) DeliveryStatus deliveryStatus) {
        List<Order> orders = orderService.getOrdersByDeliveryStatus(deliveryStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/deliveryStatus")
    @PreAuthorize("hasRole('DELIVERY_PERSONNEL') or hasRole('ADMIN')")
    @Tag(name = "APIs for Delivery Personnel and Admin")
    @Operation(summary = "Update delivery status", description = "Update the delivery status of an order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delivery status update request payload",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryStatusUpdateRequest.class),
                            examples = @ExampleObject(
                                    value = "{ \"orderId\": 1, \"personnelId\": 2, \"deliveryStatus\": \"DELIVERED\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery status successfully updated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
            })
    public ResponseEntity<Order> updateDeliveryStatus(@RequestBody DeliveryStatusUpdateRequest deliveryStatusUpdateRequest) {
        Order updatedOrder = orderService.updateDeliveryStatus(deliveryStatusUpdateRequest.getOrderId(), deliveryStatusUpdateRequest.getPersonnelId(), deliveryStatusUpdateRequest.getDeliveryStatus());
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @Operation(summary = "Handle exceptions", description = "Handle exceptions and return appropriate response",
            responses = {
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
            })
    public ResponseEntity<?> handleException(Exception exception) {
        AuthResponse authResponse = AuthResponse.builder()
                .status(exception instanceof BadCredentialsException  ? HttpStatus.UNAUTHORIZED.value() :
                        (exception instanceof ResourceNotFoundException ? HttpStatus.NOT_FOUND.value() :
                                HttpStatus.INTERNAL_SERVER_ERROR.value())
                )
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
    }
}
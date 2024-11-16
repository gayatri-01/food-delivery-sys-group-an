package com.group.an.customerService.controller;

//import com.group.an.customerService.entity.CartItem;
//import com.group.an.customerService.entity.Customer;
import com.group.an.customerService.service.CustomerService;
import com.group.an.dataService.models.CartItem;
import com.group.an.dataService.models.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/customers")
public class CustomerController {

      @Autowired
    private CustomerService customerService;


    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "API for Admins Only")
    @Operation(summary = "Fetch all customers", description = "Retrieve all customers", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all the customers"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public ResponseEntity<List<Customer>> fetchAllCustomers(){
        List<Customer> customers = customerService.fetchAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for admins and customers")
    @Operation(summary = "Get a customer by Id", description = "Retrieve a particular customer based on Id", responses = {
         @ApiResponse(responseCode = "200", description = "Successfully retrieved customer") ,
         @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
         @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable("customerId") int customerId){
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for customers and admins")
    @Operation(summary = "Update a customer", description = "Update a particular customer by Id", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Update a customer",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                          "customerId": 133,
                                          "name": "Mitesh",
                                          "email": "Mitesh@gmail.com",
                                          "passwordHash": "Mitesh4567767",
                                          "contactNumber": 1023456789,
                                          "deliveryAddress": "Malaysia",
                                          "cart": [
                                              {
                                                  "cartItemId": 23,
                                                  "menuItemId": 24,
                                                  "quantity": 25
                                              },
                                              {
                                                  "cartItemId": 26,
                                                  "menuItemId": 27,
                                                  "quantity": 28
                                              },
                                              {
                                                  "cartItemId": 29,
                                                  "menuItemId": 30,
                                                  "quantity": 31
                                              }
                                          ],
                                          "paymentDetail": {
                                              "paymentDetailId": 32,
                                              "cardType": "NVB",
                                              "cardNumber": "KSA969",
                                              "cardExpiry": "2024-10-28T15:30:00.000",
                                              "upiNumber": 89992005,
                                              "upiId": "Mitesh4567767"
                                          },
                                      	"active":true
                                      }"""
                    )
            )
    ), responses = {
            @ApiResponse(responseCode = "201", description = "Successfully updated the customer details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("customerId") int customerId, @RequestBody Customer customer){
        Customer customer1 = customerService.updateCustomerById(customerId, customer);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @GetMapping("/{customerId}/cart")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for customers and admins")
    @Operation(summary = "View customer's cart", description = "view a particular customer's cart", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched customer's cart details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No data found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public ResponseEntity<List<CartItem>> viewCustomerCart(@PathVariable("customerId") int customerId){
        List<CartItem> cartItems = customerService.viewCustomerCart(customerId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/{customerId}/cart")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for customers and admins")
    @Operation(summary = "Add to customer's cart", description = "", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Add to customer's cart",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class),
                    examples = @ExampleObject(
                            value = """
                                    [
                                           {
                                               "cartItemId": 33,
                                               "menuItemId": 34,
                                               "quantity": 35
                                           },
                                           {
                                               "cartItemId": 36,
                                               "menuItemId": 37,
                                               "quantity": 38
                                           },
                                           {
                                               "cartItemId": 39,
                                               "menuItemId": 40,
                                               "quantity": 41
                                           }
                                       ]"""
                    )
            )
    ), responses = {
            @ApiResponse(responseCode = "201", description = "Added to customer's cart"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No data found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public ResponseEntity<List<CartItem>> addCustomerCart(@PathVariable("customerId") int customerId, @RequestBody List<CartItem> cartItems){
        List<CartItem> cartItems1 = customerService.addCustomerCart(customerId, cartItems);
        return new ResponseEntity<>(cartItems1, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/cart/{cartItemId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for customers and admins")
    @Operation(summary = "Delete customer's cart item", description = "Delete a particular customer's specific cart item by corresponding Ids", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted customer's cart item"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No data found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public ResponseEntity<CartItem> deleteCartItemById(@PathVariable("customerId") int customerId, @PathVariable("cartItemId") int cartItemId){
        CartItem cartItem = customerService.deleteCartItemById(customerId, cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

}

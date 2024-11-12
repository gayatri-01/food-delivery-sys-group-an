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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "jwtAuth")
public class CustomerController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloFromCustomer(){
        return "Hello From Customer";
    }

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "API for Admins Only")
    @Operation(summary = "Adding a customer", description = "Adding a new customer to the list of existing customers",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add a customer payload",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                 "customerId": 133,
                                                 "name": "Priyanka",
                                                 "email": "Priyanka@gmail.com",
                                                 "passwordHash": "sflklll",
                                                 "contactNumber": 1234567890,
                                                 "deliveryAddress": "Nigeria",
                                                 "cart": [
                                                     {
                                                         "cartItemId": 15,
                                                         "menuItemId": 16,
                                                         "quantity": 17
                                                     },
                                                     {
                                                         "cartItemId": 18,
                                                         "menuItemId": 19,
                                                         "quantity": 20
                                                     }
                                                 ],
                                                 "paymentDetail": {
                                                     "paymentDetailId": 2122,
                                                     "cardType": "GKS",
                                                     "cardNumber": "AHY6959",
                                                     "cardExpiry": "2024-10-28T12:30:00.000",
                                                     "upiNumber": 97547,
                                                     "upiId": "Priyanka34678235"
                                                 },
                                             	"active":true
                                             }"""
                            )
            )
            ),
            responses = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "API for Admins Only")
    @Operation(summary = "Fetch all customers", description = "Retrieve all customers", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all the customers"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public List<Customer> fetchAllCustomers(){
        return customerService.fetchAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for admins and customers")
    @Operation(summary = "Get a customer by Id", description = "Retrieve a particular customer based on Id", responses = {
         @ApiResponse(responseCode = "200", description = "Successfully retrieved customer") ,
         @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
         @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public Optional<Customer> getCustomerById(@PathVariable("customerId") int customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/customers/{customerId}")
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
    public Customer updateCustomerById(@PathVariable("customerId") int customerId, @RequestBody Customer customer){
        return customerService.updateCustomerById(customerId, customer);
    }

    @GetMapping("/customers/{customerId}/cart")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for customers and admins")
    @Operation(summary = "View customer's cart", description = "view a particular customer's cart", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched customer's cart details"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No data found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public List<CartItem> viewCustomerCart(@PathVariable("customerId") int customerId){
        return customerService.viewCustomerCart(customerId);
    }

    @PostMapping("/customers/{customerId}/cart")
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
    public List<CartItem> addCustomerCart(@PathVariable("customerId") int customerId, @RequestBody List<CartItem> cartItems){
        return customerService.addCustomerCart(customerId, cartItems);
    }

    @DeleteMapping("/customers/{customerId}/cart/{cartItemId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Tag(name = "API for customers and admins")
    @Operation(summary = "Delete customer's cart item", description = "Delete a particular customer's specific cart item by corresponding Ids", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted customer's cart item"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No data found"),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
    })
    public CartItem deleteCartItemById(@PathVariable("customerId") int customerId, @PathVariable("cartItemId") int cartItemId){
        return customerService.deleteCartItemById(customerId, cartItemId);
    }

}

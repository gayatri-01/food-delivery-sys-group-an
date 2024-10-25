// Java Program to Illustrate DemoController File 

// Importing package in this code module 
package com.group.an.dataService.controllers;
// Importing required classes 
import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Annotation 
@RestController
// Main class 
public class DataController { 

	@RequestMapping("/hello") 
	@ResponseBody

	// Method 
	public String helloFromData() 
	{ 
		return "helloFromData"; 
	} 

	@Autowired 
    private TestPojoRepository testPojoRepo;
    @Autowired
    private AdminsRepository adminRepo;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantOwnerRespository restaurantOwnerRespository;
    
    // Add & Get TestPojo
    @PostMapping("/testPojo")
    public TestPojo addTestPojo(@RequestBody TestPojo testPojo) {
        return testPojoRepo.save(testPojo);
    }

    @GetMapping("/testPojo")
    public List<TestPojo> getAllTestPojo(){
        return testPojoRepo.findAll(); 
    }

    // Add & Get Admin
    @PostMapping("/admin")
    public Admins addAdmin(@RequestBody Admins admin) {
        return adminRepo.save(admin);
    }

    @GetMapping("/admin")
    public List<Admins> getAllAdmins(){
        return adminRepo.findAll();
    }

    // Add & Get Customer
    @PostMapping("/customer")
    public Customers addCustomer(@RequestBody Customers customer) {
        return customersRepository.save(customer);
    }

    @GetMapping("/customer")
    public List<Customers> getAllCustomers(){
        return customersRepository.findAll();
    }

    // Add & Get Delivery Personnel
    @PostMapping("/deliveryPersonnel")
    public DeliveryPersonnels addDeliveryPersonnel(@RequestBody DeliveryPersonnels deliveryPersonnel) {
        return deliveryPersonnelRepository.save(deliveryPersonnel);
    }

    @GetMapping("/deliveryPersonnel")
    public List<DeliveryPersonnels> getAllDeliveryPersonnel(){
        return deliveryPersonnelRepository.findAll();
    }

    // Add & Get Order
    @PostMapping("/order")
    public Orders addOrder(@RequestBody Orders order) {
        return ordersRepository.save(order);
    }

    @GetMapping("/order")
    public List<Orders> getAllOrders(){
        return ordersRepository.findAll();
    }

    // Add & Get Restaurant
    @PostMapping("/restaurant")
    public Restaurants addRestaurant(@RequestBody Restaurants restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @GetMapping("/restaurant")
    public List<Restaurants> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    // Add & Get Restaurant Owners
    @PostMapping("/restaurantOwner")
    public RestaurantOwners addRestaurantOwner(@RequestBody RestaurantOwners restaurantOwner) {
        return restaurantOwnerRespository.save(restaurantOwner);
    }

    @GetMapping("/restaurantOwner")
    public List<RestaurantOwners> getAllRestaurantOwner(){
        return restaurantOwnerRespository.findAll();
    }
}

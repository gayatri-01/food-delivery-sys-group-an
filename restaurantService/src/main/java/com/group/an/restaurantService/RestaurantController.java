// Importing package in this code module 
package com.group.an.restaurantService;
// Importing required classes 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody; 

// Annotation 
@Controller
// Main class 
public class RestaurantController { 

	@RequestMapping("/hello") 
	@ResponseBody

	// Method 
	public String helloFromRestaurant() 
	{ 
		return "helloFromRestaurant"; 
	} 
}

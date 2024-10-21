// Java Program to Illustrate DemoController File 

// Importing package in this code module 
package com.group.an.authService;
// Importing required classes 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody; 

// Annotation 
@Controller
// Main class 
public class AuthController { 

	@RequestMapping("/hello") 
	@ResponseBody

	// Method 
	public String helloFromAuth() 
	{ 
		return "helloFromAuth"; 
	} 
}

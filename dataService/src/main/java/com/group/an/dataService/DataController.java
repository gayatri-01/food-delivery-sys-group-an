// Java Program to Illustrate DemoController File 

// Importing package in this code module 
package com.group.an.dataService;
// Importing required classes 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody; 

// Annotation 
@Controller
// Main class 
public class DataController { 

	@RequestMapping("/hello") 
	@ResponseBody

	// Method 
	public String helloFromData() 
	{ 
		return "helloFromData"; 
	} 
}

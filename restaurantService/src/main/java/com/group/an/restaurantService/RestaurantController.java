// Importing package in this code module 
package com.group.an.restaurantService;
// Importing required classes 
import com.group.an.dataService.models.MenuItem;
import com.group.an.dataService.models.Restaurant;
import com.group.an.dataService.repositories.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

// Annotation
@RestController
@RequestMapping("/restaurants")
@SecurityRequirement(name = "jwtAuth")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping
	@Tag(name = "APIs for All")
	@Operation(summary = "Get all restaurants", description = "Retrieve a list of all restaurants",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully retrieved list of restaurants"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<List<Restaurant>> getAllRestaurants() {
		List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
		return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
	}

	@PostMapping
	@Tag(name = "APIs for Admin Only")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Adding a restaurant", description = "Adding a restaurant to list of supported restaurants",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Add restaurant payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = Restaurant.class),
							examples = @ExampleObject(
									value = """
                                            {
                                              "restaurantId": 1,
                                              "ownerId": 1,
                                              "name": "Shyam Dhaba",
                                              "address": "12, Nagar Highway, Nagpur",
                                              "phoneNumber": 2345678976,
                                              "openingTime": "2024-11-10T17:20:10.688Z",
                                              "closingTime": "2024-11-10T17:20:10.688Z",
                                              "userRatings": 3,
                                              "menus": [
                                                {
                                                  "menuItemId": 2,
                                                  "itemName": "Veg. Kolhapuri",
                                                  "description": "Red Spicy Vegetable curry",
                                                  "price": 180,
                                                  "available": true
                                                }
                                              ]
                                            }"""
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Successfully added restaurant"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant newRestaurant = restaurantService.addRestaurant(restaurant);
		return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
	}

	@GetMapping("/{restaurantId}/menu")
	@Tag(name = "APIs for All")
	@Operation(summary = "Fetch a restaurant's menu", description = "Retrieve menu of given restaurant",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully retrieved menu of given restaurant"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "404", description = "Restaurant not found"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<List<MenuItem>> getAllRestaurant(@PathVariable("restaurantId") @Parameter(example = "1") Integer restaurantId) {
		List<MenuItem> existingMenu = restaurantService.getAllRestaurant(restaurantId);
		return new ResponseEntity<>(existingMenu, HttpStatus.OK);
	}

	@PostMapping("/{restaurantId}/menu")
	@Tag(name = "APIs for Admin and Restaurant Owner Only")
	@PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
	@Operation(summary = "Enhance a restaurant's menu", description = "Add an item to the menu of given restaurant",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Add a menu item payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MenuItem.class),
							examples = @ExampleObject(
									value = """
												{
												  "menuItemId": 3,
												  "itemName": "Veg. Handi",
												  "description": "Brown Medium Spicy Vegetable curry",
												  "price": 150,
												  "available": true
												}"""
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Successfully updated menu of given restaurant"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "404", description = "Restaurant not found"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<Restaurant> addItemToMenuOfRestaurant(@RequestBody MenuItem item, @PathVariable("restaurantId") @Parameter(example = "1") Integer restaurantId) {
		Restaurant restaurant = restaurantService.addItemToMenuOfRestaurant(item, restaurantId);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@PostMapping("/{restaurantId}/menu/{menuItemId}")
	@Tag(name = "APIs for Admin and Restaurant Owner Only")
	@PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
	@Operation(summary = "Update a restaurant's menu item", description = "Update a particular item from menu of given restaurant",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Update a menu item payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = MenuItem.class),
							examples = @ExampleObject(
									value = """
												{
												  "menuItemId": 3,
												  "itemName": "Veg. Handi",
												  "description": "Brown Medium Vegetable curry",
												  "price": 165,
												  "available": true
												}"""
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Successfully updated menu item of given restaurant"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "404", description = "Restaurant or menu item not found"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<Restaurant> updateMenuItemOfRestaurant(@RequestBody MenuItem item,
																  @PathVariable("restaurantId") @Parameter(example = "1") Integer restaurantId,
																  @PathVariable("menuItemId") @Parameter(example = "3") Integer menuItemId) {
		Restaurant restaurant = restaurantService.updateMenuItemOfRestaurant(item, restaurantId, menuItemId);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@DeleteMapping("/{restaurantId}/menu/{menuItemId}")
	@Tag(name = "APIs for Admin and Restaurant Owner Only")
	@PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
	@Operation(summary = "Delete a restaurant's menu item", description = "Update a particular item from menu of given restaurant",
			responses = {
					@ApiResponse(responseCode = "204", description = "Successfully deleted menu item of given restaurant"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "404", description = "Restaurant or menu item not found"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<Restaurant> deleteMenuItemOfRestaurant(@PathVariable("restaurantId") @Parameter(example = "1") Integer restaurantId,
																  @PathVariable("menuItemId") @Parameter(example = "1") Integer menuItemId) {
		Restaurant restaurant = restaurantService.deleteMenuItemOfRestaurant(restaurantId, menuItemId);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}
}
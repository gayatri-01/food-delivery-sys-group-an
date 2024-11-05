package com.group.an.authService.service;


import com.group.an.authService.requestModel.*;
import com.group.an.authService.responseModel.AuthResponse;
import com.group.an.authService.security.JwtTokenUtil;
import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.AdminRepository;
import com.group.an.dataService.repositories.CustomerRepository;
import com.group.an.dataService.repositories.DeliveryPersonnelRepository;
import com.group.an.dataService.repositories.RestaurantOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    AdminRepository adminRepository;


    public AuthResponse loginUser(LoginRequest loginRequest, Role role) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());
        authenticationToken.setDetails(role);
        Authentication auth = authenticationManager.authenticate(authenticationToken);

        if (auth.isAuthenticated()) {
            String token = jwtTokenUtil.generateToken(loginRequest.getUserId(), role);
            return AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Logged In Successfully as " + role)
                    .token(token)
                    .build();
        } else {
            return AuthResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid Credentials")
                    .build();
        }
    }

    public AuthResponse registerCustomer(CustomerRegisterRequest customerRegisterRequest) {
        Customer customer = new Customer();

        //Get from Request
        customer.setName(customerRegisterRequest.getName());
        customer.setEmail(customerRegisterRequest.getEmail());
        customer.setPasswordHash(new BCryptPasswordEncoder().encode(customerRegisterRequest.getPassword()));
        customer.setContactNumber(customerRegisterRequest.getContactNumber());
        customer.setDeliveryAddress(customerRegisterRequest.getDeliveryAddress());
        customer.setPaymentDetail(customerRegisterRequest.getPaymentDetail());

        //Default
        customer.setCustomerId(generateCustomerId());
        customer.setActive(true);

        //Save to Database
        customerRepository.save(customer);

        //JWT Token Generation
        String token = jwtTokenUtil.generateToken(customer.getCustomerId(), Role.CUSTOMER);

        //Return Response
        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Customer Account created successfully with customerId: " + customer.getCustomerId())
                .token(token)
                .build();
    }


    public AuthResponse registerRestaurantOwner(RestaurantOwnerRegisterRequest restaurantOwnerRegisterRequest) {
        RestaurantOwner restaurantOwner = new RestaurantOwner();

        //Get from Request
        restaurantOwner.setName(restaurantOwnerRegisterRequest.getName());
        restaurantOwner.setEmail(restaurantOwnerRegisterRequest.getEmail());
        restaurantOwner.setPasswordHash(new BCryptPasswordEncoder().encode(restaurantOwnerRegisterRequest.getPassword()));
        restaurantOwner.setContactNumber(restaurantOwnerRegisterRequest.getContactNumber());
        restaurantOwner.setRestaurantId(restaurantOwnerRegisterRequest.getRestaurantId());
        restaurantOwner.setActive(true);

        //Default
        restaurantOwner.setOwnerId(generateRestaurantOwnerId());
        restaurantOwner.setActive(true);

        //Save to Database
        restaurantOwnerRepository.save(restaurantOwner);

        //JWT Token Generation
        String token = jwtTokenUtil.generateToken(restaurantOwner.getOwnerId(), Role.RESTAURANT_OWNER);

        //Return Response
        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message("RestaurantOwner Account created successfully with ownerId: " + restaurantOwner.getOwnerId())
                .token(token)
                .build();
    }

    public AuthResponse registerDeliveryPersonnel(DeliveryPersonnelRegisterRequest deliveryPersonnelRegisterRequest) {
        DeliveryPersonnel deliveryPersonnel = new DeliveryPersonnel();

        //Get from Request
        deliveryPersonnel.setName(deliveryPersonnelRegisterRequest.getName());
        deliveryPersonnel.setEmail(deliveryPersonnelRegisterRequest.getEmail());
        deliveryPersonnel.setPasswordHash(new BCryptPasswordEncoder().encode(deliveryPersonnelRegisterRequest.getPassword()));
        deliveryPersonnel.setContactNumber(deliveryPersonnelRegisterRequest.getContactNumber());
        deliveryPersonnel.setVehicleType(deliveryPersonnelRegisterRequest.getVehicleType());

        //Default
        deliveryPersonnel.setPersonnelId(generateDeliveryPersonnelId());
        deliveryPersonnel.setCurrentStatus(DeliveryPersonnelStatus.AVAILABLE);
        deliveryPersonnel.setActive(true);

        //Save to Database
        deliveryPersonnelRepository.save(deliveryPersonnel);

        //JWT Token Generation
        String token = jwtTokenUtil.generateToken(deliveryPersonnel.getPersonnelId(), Role.DELIVERY_PERSONNEL);

        //Return Response
        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message("DeliveryPersonnel Account created successfully with personnelId: " + deliveryPersonnel.getPersonnelId())
                .token(token)
                .build();
    }

    public AuthResponse registerAdmin(AdminRegisterRequest adminRegisterRequest) {
        Admin admin = new Admin();

        //Get from Request
        admin.setName(adminRegisterRequest.getName());
        admin.setEmail(adminRegisterRequest.getEmail());
        admin.setPasswordHash(new BCryptPasswordEncoder().encode(adminRegisterRequest.getPassword()));
        admin.setContactNumber(adminRegisterRequest.getContactNumber());

        //Default
        admin.setAdminId(generateAdminId());

        //Save to Database
        adminRepository.save(admin);

        //JWT Token Generation
        String token = jwtTokenUtil.generateToken(admin.getAdminId(), Role.ADMIN);

        //Return Response
        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Admin Account created successfully with adminId: " + admin.getAdminId())
                .token(token)
                .build();
    }

    public AuthResponse updateActivationStatus(ActivationRequest activationRequest, boolean activationStatus) {
        Role role = activationRequest.getRole();
        int userId = activationRequest.getUserId();

        return switch (role) {
            case CUSTOMER ->
                    updateStatus(customerRepository.findByCustomerId(userId), userId, activationStatus, role.name());
            case RESTAURANT_OWNER ->
                    updateStatus(restaurantOwnerRepository.findByOwnerId(userId), userId, activationStatus, role.name());
            case DELIVERY_PERSONNEL ->
                    updateStatus(deliveryPersonnelRepository.findByPersonnelId(userId), userId, activationStatus, role.name());
            default -> AuthResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Not a valid Role")
                    .build();
        };
    }

    private <T> AuthResponse updateStatus(T user, int userId, boolean activationStatus, String userType) {
        if (user == null) {
            return AuthResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(userType + " with ID: " + userId + " not found")
                    .build();
        }

        if (user instanceof Customer) {
            ((Customer) user).setActive(activationStatus);
            customerRepository.save((Customer) user);
        } else if (user instanceof RestaurantOwner) {
            ((RestaurantOwner) user).setActive(activationStatus);
            restaurantOwnerRepository.save((RestaurantOwner) user);
        } else if (user instanceof DeliveryPersonnel) {
            ((DeliveryPersonnel) user).setActive(activationStatus);
            deliveryPersonnelRepository.save((DeliveryPersonnel) user);
        }

        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message(userType + " Account with ID: " + userId + " is now " + (activationStatus ? "Activated" : "Deactivated"))
                .build();
    }


    private int generateCustomerId() {
        int randomId = generateId(1001, 2000);
        if (customerRepository.findByCustomerId(randomId) != null) {
            return generateCustomerId();
        }
        return randomId;
    }

    private int generateRestaurantOwnerId() {
        int randomId = generateId(2001, 3000);
        if (restaurantOwnerRepository.findByOwnerId(randomId) != null) {
            return generateRestaurantOwnerId();
        }
        return randomId;
    }

    private int generateDeliveryPersonnelId() {
        int randomId = generateId(3001, 4000);
        if (deliveryPersonnelRepository.findByPersonnelId(randomId) != null) {
            return generateDeliveryPersonnelId();
        }
        return randomId;
    }

    private int generateAdminId() {
        int randomId = generateId(4001, 5000);
        if (adminRepository.findByAdminId(randomId) != null) {
            return generateAdminId();
        }
        return randomId;
    }

    // Generate a random number between min and max (inclusive)
    private int generateId(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }


}

package com.group.an.authService.service;

import com.group.an.authService.requestModel.CustomerRegisterRequest;
import com.group.an.authService.requestModel.LoginRequest;
import com.group.an.authService.responseModel.AuthResponse;
import com.group.an.authService.security.JwtTokenUtil;
import com.group.an.dataService.models.Customer;
import com.group.an.dataService.models.Role;
import com.group.an.dataService.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        customerRepository = mock(CustomerRepository.class);
        authService = new AuthService();
        authService.authenticationManager = authenticationManager;
        authService.jwtTokenUtil = jwtTokenUtil;
        authService.customerRepository = customerRepository;
    }

    @Test
    void testLoginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId(1);
        loginRequest.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtTokenUtil.generateToken(1, Role.CUSTOMER)).thenReturn("token");

        AuthResponse authResponse = authService.loginUser(loginRequest, Role.CUSTOMER);

        assertNotNull(authResponse);
        assertEquals("token", authResponse.getToken());
        assertEquals(HttpStatus.OK.value(), authResponse.getStatus());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtil).generateToken(1, Role.CUSTOMER);
    }

    @Test
    void testRegisterCustomer() {
        CustomerRegisterRequest customerRegisterRequest = CustomerRegisterRequest.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .contactNumber(1234567890)
                .deliveryAddress("123 Main St")
                .build();

        Customer customer = new Customer();
        customer.setCustomerId(1001);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
        customer.setContactNumber(1234567890L);
        customer.setDeliveryAddress("123 Main St");
        customer.setActive(true);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(jwtTokenUtil.generateToken(anyInt(), any(Role.class))).thenReturn("token");

        AuthResponse authResponse = authService.registerCustomer(customerRegisterRequest);

        assertNotNull(authResponse);
        assertEquals("token", authResponse.getToken());
        assertEquals(HttpStatus.OK.value(), authResponse.getStatus());
        verify(customerRepository).save(any(Customer.class));
    }
}
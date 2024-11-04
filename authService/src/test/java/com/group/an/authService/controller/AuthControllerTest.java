package com.group.an.authService.controller;

import com.group.an.authService.requestModel.CustomerRegisterRequest;
import com.group.an.authService.service.AuthService;
import com.group.an.dataService.models.Role;
import com.group.an.authService.requestModel.LoginRequest;
import com.group.an.authService.responseModel.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

class AuthControllerTest {

    private AuthController authController;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController();
        authController.authService = authService;
        AuthResponse authResponse = AuthResponse.builder().token("token").status(OK.value()).build();
        when(authService.loginUser(any(LoginRequest.class), any(Role.class))).thenReturn(authResponse);
        when(authService.registerCustomer(any(CustomerRegisterRequest.class))).thenReturn(authResponse);
    }

    @Test
    void testLoginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId(1);
        loginRequest.setPassword("password");

        ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);

        assertEquals(OK, response.getStatusCode());
        assertEquals("token", response.getBody().getToken());
        verify(authService).loginUser(loginRequest, Role.CUSTOMER);
    }

    @Test
    void testRegisterCustomer() {
        CustomerRegisterRequest registrationRequest = CustomerRegisterRequest.builder().name("abc").password("abc").build();

        ResponseEntity<AuthResponse> response = authController.registerCustomer(registrationRequest);

        assertEquals(OK, response.getStatusCode());
        assertEquals("token", response.getBody().getToken());
        verify(authService).registerCustomer(registrationRequest);
    }

}
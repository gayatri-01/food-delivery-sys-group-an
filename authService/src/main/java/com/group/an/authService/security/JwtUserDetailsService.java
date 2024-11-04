package com.group.an.authService.security;

import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.AdminRepository;
import com.group.an.dataService.repositories.CustomerRepository;
import com.group.an.dataService.repositories.DeliveryPersonnelRepository;
import com.group.an.dataService.repositories.RestaurantOwnerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService{

    final CustomerRepository customerRepository;
    final RestaurantOwnerRepository restaurantOwnerRepository;
    final DeliveryPersonnelRepository deliveryPersonnelRepository;
    final AdminRepository adminRepository;
    final JwtTokenUtil jwtTokenUtil;

    public JwtUserDetailsService(CustomerRepository customerRepository, RestaurantOwnerRepository restaurantOwnerRepository, DeliveryPersonnelRepository deliveryPersonnelRepository, AdminRepository adminRepository, JwtTokenUtil jwtTokenUtil) {
        this.customerRepository = customerRepository;
        this.restaurantOwnerRepository = restaurantOwnerRepository;
        this.deliveryPersonnelRepository = deliveryPersonnelRepository;
        this.adminRepository = adminRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserDetails loadUserByUserIdAndRole(Integer userId, Role role) throws UsernameNotFoundException {
        Integer userIdFromRepository = null;
        String passwordFromRepository = null;

        //Get the user details based on the role from the Database
        switch (role) {
            case CUSTOMER -> {
                Customer customer = customerRepository.findByCustomerId(userId);
                if (customer != null) {
                    userIdFromRepository = customer.getCustomerId();
                    passwordFromRepository = customer.getPasswordHash();
                }
            }
            case RESTAURANT_OWNER -> {
                RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByOwnerId(userId);
                if (restaurantOwner != null) {
                    userIdFromRepository = restaurantOwner.getOwnerId();
                    passwordFromRepository = restaurantOwner.getPasswordHash();
                }
            }
            case DELIVERY_PERSONNEL -> {
                DeliveryPersonnel deliveryPersonnel = deliveryPersonnelRepository.findByPersonnelId(userId);
                if (deliveryPersonnel != null) {
                    userIdFromRepository = deliveryPersonnel.getPersonnelId();
                    passwordFromRepository = deliveryPersonnel.getPasswordHash();
                }
            }
            case ADMIN -> {
                Admin admin = adminRepository.findByAdminId(userId);
                if (admin != null) {
                    userIdFromRepository = admin.getAdminId();
                    passwordFromRepository = admin.getPasswordHash();
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + role);
        }

        // Add Applicable role to the user
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+role.name()));

        //Return UserDetails if found else return NULL
        return userIdFromRepository != null ?  new User(userIdFromRepository.toString(), passwordFromRepository, authorityList) : null;
    }

    @Deprecated
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

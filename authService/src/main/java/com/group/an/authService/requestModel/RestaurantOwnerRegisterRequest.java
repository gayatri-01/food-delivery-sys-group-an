package com.group.an.authService.requestModel;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerRegisterRequest {
    private String name;
    private String email;
    private String password;
    private long contactNumber;
    private int restaurantId;
}

package com.group.an.authService.requestModel;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonnelRegisterRequest {
    private String name;
    private String email;
    private String password;
    private long contactNumber;
    private String vehicleType;
}

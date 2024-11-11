package com.group.an.authService.requestModel;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterRequest {
    private String name;
    private String email;
    private String password;
    private long contactNumber;
}

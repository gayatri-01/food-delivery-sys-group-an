package com.group.an.authService.requestModel;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private Integer userId;
    private String password;
}

package com.group.an.authService.requestModel;

import com.group.an.dataService.models.Role;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivationRequest {
    private Role role;
    private Integer userId;
}

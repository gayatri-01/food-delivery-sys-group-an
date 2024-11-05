package com.group.an.authService.requestModel;

import com.group.an.dataService.models.PaymentDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomerRegisterRequest {
    private String name;
    private String email;
    private String password;
    private long contactNumber;
    private String deliveryAddress;
    private PaymentDetail paymentDetail;
}

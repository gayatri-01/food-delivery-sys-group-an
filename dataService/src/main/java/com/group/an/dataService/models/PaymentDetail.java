package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    private String cardType;
    private String cardNumber;
    private LocalDateTime cardExpiry;
    private long upiNumber;
    private String upiId;
}

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
    @Id
    private int paymentDetailId;
    private String cardType;
    private String cardNumber;
    private LocalDateTime cardExpiry;
    private Long upiNumber;
    private String upiId;
}

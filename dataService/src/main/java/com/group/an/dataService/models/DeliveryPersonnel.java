package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "deliveryPersonnels")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonnel {
    @Id
    private int personnelId;
    private String name;
    private String email;
    private String passwordHash;
    private long contactNumber;
    private String vehicleType;
    private DeliveryPersonnelStatus currentStatus;
    private boolean isActive;
}

package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restaurantOwners")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwner {
    @Id
    private int ownerId;
    private String name;
    private String email;
    private String passwordHash;
    private long contactNumber;
    private int restaurantId;
    private boolean isActive;
}

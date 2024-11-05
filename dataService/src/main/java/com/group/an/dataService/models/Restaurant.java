package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "restaurants")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    private int restaurantId;
    private int ownerId;
    private String name;
    private String address;
    private long phoneNumber;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private int userRatings;
    private List<MenuItem> menus;
}

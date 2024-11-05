package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    private int cartItemId;
    private int menuItemId;
    private int quantity;
}

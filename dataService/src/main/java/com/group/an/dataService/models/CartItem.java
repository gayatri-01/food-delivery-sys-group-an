package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;

public class CartItem {
    @Id
    private int cartItemId;
    private int menuItemId;
    private int quantity;

    public CartItem(int cartItemId, int menuItemId, int quantity) {
        this.cartItemId = cartItemId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

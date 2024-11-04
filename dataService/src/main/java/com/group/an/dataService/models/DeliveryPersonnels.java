package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DeliveryPersonnels {
    @Id
    private int personnelId;
    private String name;
    private String email;
    private String passwordHash;
    private long contactNumber;
    private String vehicleType;
    private enum currentStatus { AVAILABLE, ON_DELIVERY, UNAVAILABLE};
    private boolean isActive;

    public DeliveryPersonnels(int personnelId, String name, String email, String passwordHash, long contactNumber, String vehicleType, boolean isActive) {
        this.personnelId = personnelId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.contactNumber = contactNumber;
        this.vehicleType = vehicleType;
        this.isActive = isActive;
    }

    public int getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

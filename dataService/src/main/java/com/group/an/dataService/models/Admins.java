package com.group.an.dataService.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admins {
    @Id
    private int adminId;
    private String name;
    private String email;
    private String passwordHash;
    private long contactNumber;

    public Admins(int adminId, String name, String email, String passwordHash, long contactNumber) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.contactNumber = contactNumber;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
}

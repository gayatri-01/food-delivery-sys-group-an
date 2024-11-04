package com.group.an.dataService.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private int adminId;
    private String name;
    private String email;
    private String passwordHash;
    private long contactNumber;
}

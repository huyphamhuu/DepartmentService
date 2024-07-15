package com.example.department.dtos;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // Getters and Setters
}

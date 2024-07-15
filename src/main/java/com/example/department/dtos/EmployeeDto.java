package com.example.department.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private LocalDate dateOfBirth;
    private String imageUrl;
    private String employeeCode;
    private Boolean locked;
    private Boolean enabled;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String departmentName;
    private List<String> roles;
}

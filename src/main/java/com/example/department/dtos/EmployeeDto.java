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
    private String departmentName;

}

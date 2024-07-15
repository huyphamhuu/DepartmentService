package com.example.department.clients;

import com.example.department.dtos.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceClient {

    @Value("${application.service.url}")
    private String employeeServiceUrl;

    private final RestTemplate restTemplate;

    public EmployeeServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmployeeDto getEmployeeByEmail(String email) {
        String url = employeeServiceUrl + "/employees/find/" + email;
        return restTemplate.getForObject(url, EmployeeDto.class);
    }

    public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
        String url = employeeServiceUrl + "/employees/department/" + departmentId;
        EmployeeDto[] employees = restTemplate.getForObject(url, EmployeeDto[].class);
        return Arrays.asList(employees);
    }
}

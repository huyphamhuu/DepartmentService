package com.example.department.clients;

import com.example.department.dtos.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceClient {

    @Value("${application.service.employee.url}")
    private String employeeServiceUrl;

    private final RestTemplate restTemplate;

    public EmployeeServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmployeeDto getEmployeeByEmail(String email) {
        String url = employeeServiceUrl + "/employees/" + email;
        System.out.println("before restapi" + url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJmdWxsbmFtZSI6Imh1eWh1eSBkZGQiLCJzdWIiOiJodXkwNXB5QGdtYWlsLmNvbSIsImlhdCI6MTcyMTI2ODkwMSwiZXhwIjoxNzIxMzU1MzAxLCJhdXRob3JpdGllcyI6WyJVU0VSIl19.ajqC2_Qw_iSMOkd73X3Os8hN7igwmCqMclmreJH5Dlc"); // Thay thế "your_jwt_token" bằng token thực tế
        HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println("before restapi" + url+ entity+EmployeeDto.class);
        ResponseEntity<EmployeeDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, EmployeeDto.class);
        System.out.println("after restapi" + url);
        return response.getBody();

    }
    public EmployeeDto getEmployeeById(Long Id) {
        String url = employeeServiceUrl + "/employees/find/" + Id;

        return restTemplate.getForObject(url, EmployeeDto.class);
    }

    public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
        String url = employeeServiceUrl + "/employees/department/" + departmentId;
        EmployeeDto[] employees = restTemplate.getForObject(url, EmployeeDto[].class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJmdWxsbmFtZSI6Imh1eWh1eSBkZGQiLCJzdWIiOiJodXkwNXB5QGdtYWlsLmNvbSIsImlhdCI6MTcyMTI2ODkwMSwiZXhwIjoxNzIxMzU1MzAxLCJhdXRob3JpdGllcyI6WyJVU0VSIl19.ajqC2_Qw_iSMOkd73X3Os8hN7igwmCqMclmreJH5Dlc"); // Thay thế "your_jwt_token" bằng token thực tế
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<EmployeeDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, EmployeeDto.class);

        return Arrays.asList(employees);
    }
}

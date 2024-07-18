package com.example.department.clients;

import com.example.department.dtos.EmployeeDto;
import com.example.department.dtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import com.example.department.handler.UserNotFoundException;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceClient {

    @Value("${application.service.user.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public UserDto getUserByEmail(String email) {
        String url = userServiceUrl + "/email?email=" + email;
        //System.out.println("tag3 "+url);
        try {
            ResponseEntity<UserDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    UserDto.class
            );
            System.out.println("tag4");
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("Wrong URL: "+ url);
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error: " + e.getStatusCode());
            throw e; // Hoặc xử lý theo cách bạn muốn
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }



}

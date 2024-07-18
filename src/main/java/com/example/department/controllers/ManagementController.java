package com.example.department.controllers;

import com.example.department.dtos.EmployeeDto;
import com.example.department.models.Department;
import com.example.department.services.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@Tag(name = "DepartmentManagement")
@RequiredArgsConstructor
public class ManagementController {

    private final DepartmentService departmentService;



    @GetMapping("/{departmentId}/employee")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentId(
            @PathVariable("departmentId") Long departmentId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        List<EmployeeDto> employees = departmentService.getEmployeesByDepartmentId(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @GetMapping("/employee/{email}")
    public ResponseEntity<EmployeeDto> getEmployeeByEmail(
            @PathVariable("email") String email,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        EmployeeDto employee = departmentService.getEmployeeByEmail(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> addEmployeeToDepartment(
            @RequestParam("departmentName") String departmentName,
            @RequestParam("mail") String mail,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        //System.out.println("get in API");
        EmployeeDto updatedEmployee = departmentService.addEmployeeToDepartment(departmentName, mail);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
    @DeleteMapping("/employee")
    public ResponseEntity<?> deleteEmployeeToDepartment(
            @RequestParam("departmentName") String departmentName,
            @RequestParam("mail") String mail,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        //System.out.println("get in API");
        EmployeeDto deletedEmployee = departmentService.deleteEmployeeToDepartment(departmentName, mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

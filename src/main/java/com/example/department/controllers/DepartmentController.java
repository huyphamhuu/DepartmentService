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
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<List<Department>> getAllDepartments(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        List<Department> departments = departmentService.findAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(
            @PathVariable("id") Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Department department = departmentService.findDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Department> addDepartment(
            @RequestBody Department department,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Department newDepartment = departmentService.addDepartment(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @RequestBody Department department,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Department updateDepartment = departmentService.updateDepartment(department);
        return new ResponseEntity<>(updateDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(
            @PathVariable("id") Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

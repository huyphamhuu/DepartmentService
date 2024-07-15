package com.example.department.controllers;

import com.example.department.dtos.EmployeeDto;
import com.example.department.models.Department;
import com.example.department.services.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.findAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        Department department = departmentService.findDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.addDepartment(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        Department updateDepartment = departmentService.updateDepartment(department);
        return new ResponseEntity<>(updateDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employees/{departmentId}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentId(@PathVariable("departmentId") Long departmentId) {
        List<EmployeeDto> employees = departmentService.getEmployeesByDepartmentId(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employee/{email}")
    public ResponseEntity<EmployeeDto> getEmployeeByEmail(@PathVariable("email") String email) {
        EmployeeDto employee = departmentService.getEmployeeByEmail(email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}

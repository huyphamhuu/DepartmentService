package com.example.department.services;

import com.example.department.dtos.EmployeeDto;
import com.example.department.models.Department;
import com.example.department.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.example.department.clients.EmployeeServiceClient;
import com.example.department.handler.*;
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
        Department department = findDepartmentById(departmentId);
        if (department == null || department.getEmployeeEmails() == null) {
            return new ArrayList<>();
        }
        List<String> employeeEmails = department.getEmployeeEmails();
        return employeeEmails.stream()
                .map(employeeServiceClient::getEmployeeByEmail)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeByEmail(String email) {
        return employeeServiceClient.getEmployeeByEmail(email);
    }
    public EmployeeDto addEmployeeToDepartment(String name, String employeeEmail){
        EmployeeDto employeeDto = getEmployeeByEmail(employeeEmail);
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        if (department.getEmployeeEmails() == null) {
            department.setEmployeeEmails(new ArrayList<>());
        }
        employeeDto.setDepartmentName(department.getName());
        department.getEmployeeEmails().add(employeeDto.getEmail());
        departmentRepository.save(department);
        return employeeDto;

    }
    public void addEmployeeToDepartment(Long departmentId, String employeeEmail) {
        EmployeeDto employeeDto = getEmployeeByEmail(employeeEmail);
        Department department = findDepartmentById(departmentId);
        if (department.getEmployeeEmails() == null) {
            department.setEmployeeEmails(new ArrayList<>());
        }
        department.getEmployeeEmails().add(employeeEmail);
        departmentRepository.save(department);
    }
    public EmployeeDto deleteEmployeeToDepartment(String departmentName, String employeeEmail){
        Department department = departmentRepository.findByName(departmentName).orElse(null);
        if (department == null) {
            throw new ResourceNotFoundException("Department not found");
        }
        List<String> allEmails = department.getEmployeeEmails();
        if (allEmails == null || !allEmails.contains(employeeEmail)) {
            throw new ResourceNotFoundException("Employee email not found in the department");
        }
        allEmails.remove(employeeEmail);
        departmentRepository.save(department);
        EmployeeDto employeeDto = getEmployeeByEmail(employeeEmail);
        return employeeDto;

    }
    }


package com.example.department.services;

import com.example.department.dtos.EmployeeDto;
import com.example.department.models.Department;
import com.example.department.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.department.clients.EmployeeServiceClient;
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
        return employeeServiceClient.getEmployeesByDepartmentId(departmentId);
    }

    public EmployeeDto getEmployeeByEmail(String email) {
        return employeeServiceClient.getEmployeeByEmail(email);
    }
}

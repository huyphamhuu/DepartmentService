
package com.example.department.repositories;

import com.example.department.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    void deleteDepartmentById(Long id);

    Optional<Department> findByName(String name);
}

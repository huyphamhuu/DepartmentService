package com.example.department.configs;

import com.example.department.models.Department;
import com.example.department.repositories.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialDataLoader {

    @Bean
    CommandLineRunner init(DepartmentRepository departmentRepository) {

        return args -> {
            if (departmentRepository.findByName("HR").isEmpty()) {
                Department hr = new Department();
                hr.setName("HR");
                //System.out.println("create table hr");
                hr.setDescription("Handles recruitment, training, and employee welfare.");
                departmentRepository.save(hr);
            }
            if (departmentRepository.findByName("Finance").isEmpty()) {
                Department finance = new Department();
                finance.setName("Finance");
                finance.setDescription("Manages the company's finances, including planning, organizing, auditing, accounting, and controlling the company's finances.");
                departmentRepository.save(finance);
            }
            if (departmentRepository.findByName("IT").isEmpty()) {
                Department it = new Department();
                it.setName("IT");
                it.setDescription("Responsible for the implementation, monitoring, and maintenance of IT systems.");
                departmentRepository.save(it);
            }
        };
    }
}

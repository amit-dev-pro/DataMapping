package com.amit.DataMapping.services;

import com.amit.DataMapping.entities.EmployeeEntity;
import com.amit.DataMapping.repositories.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeesRepository employeesRepository;

    public EmployeeEntity getEmployeeById(Long employeeId) {
        return employeesRepository.findById(employeeId).get();
    }

    public EmployeeEntity createNewEmployee(EmployeeEntity employeeEntity) {
        return employeesRepository.save(employeeEntity);
    }
}

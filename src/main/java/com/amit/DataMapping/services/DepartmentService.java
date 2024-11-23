package com.amit.DataMapping.services;

import com.amit.DataMapping.entities.DepartmentEntity;
import com.amit.DataMapping.entities.EmployeeEntity;
import com.amit.DataMapping.repositories.DepartmentRepository;
import com.amit.DataMapping.repositories.EmployeesRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private final EmployeesRepository employeesRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeesRepository employeesRepository) {
        this.departmentRepository = departmentRepository;
        this.employeesRepository = employeesRepository;
    }

    public DepartmentEntity getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).get();
    }

    public DepartmentEntity createNewDepartment(DepartmentEntity departmentEntity) {
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity assignManagerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity=employeesRepository.findById(employeeId);

        return departmentEntity.flatMap(department->employeeEntity.map(employee->{
            department.setManager(employee);
            return departmentRepository.save(department);
        })).orElse(null);
    }

    public DepartmentEntity getAssignedDepartmentOfManager(Long employeeId) {
        EmployeeEntity employeeEntity=EmployeeEntity.builder().id(employeeId).build();
        return departmentRepository.findByManager(employeeEntity);
    }

    public DepartmentEntity assignWorkerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity=employeesRepository.findById(employeeId);

        return departmentEntity.flatMap(department->employeeEntity.map(employee->{
            employee.setWorkerDepartment(department);
            employeesRepository.save(employee);

            department.getWorkers().add(employee);
            return department;
        })).orElse(null);
    }

    public DepartmentEntity assignFreelancerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity=employeesRepository.findById(employeeId);

        return departmentEntity.flatMap(department->employeeEntity.map(employee->{
            employee.getFreelanceDepartments().add(department);
            employeesRepository.save(employee);

            department.getFreelancers().add(employee);
            return department;
        })).orElse(null);
    }
}

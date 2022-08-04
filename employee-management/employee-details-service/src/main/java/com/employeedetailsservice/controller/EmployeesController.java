package com.employeedetailsservice.controller;

import com.employee_management.api.EmployeesApi;
import com.employee_management.model.Employee;
import com.employeedetailsservice.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeesController implements EmployeesApi {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @CrossOrigin(value = "*")
    @RolesAllowed("admin")
    public ResponseEntity<List<Employee>> listEmployees(Integer limit) {
        List<com.employeedetailsservice.entity.Employee> employeeList=employeeRepository.findAll();
        List<Employee> employees=new ArrayList<>();
        for (com.employeedetailsservice.entity.Employee employee: employeeList)
        {
            Employee employee1=new Employee();
            employee1.setId(employee.getId());
            employee1.setName(employee.getName());
            employee1.setDepartment(employee.getDepartment());
            employees.add(employee1);
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    @CrossOrigin(value = "*")
    @RolesAllowed({"user", "admin"})
    public ResponseEntity<Employee> createEmployee(Employee employee) {
        com.employeedetailsservice.entity.Employee employee1=new com.employeedetailsservice.entity.Employee();
        employee1.setDepartment(employee.getDepartment());
        employee1.setName(employee.getName());
        employee1.setId(employee.getId());
        employee1=employeeRepository.save(employee1);
        employee.setId(employee1.getId());
        return ResponseEntity.ok(employee);
    }
}

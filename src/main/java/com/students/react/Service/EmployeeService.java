package com.students.react.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.students.react.Controller.EmployeeController;
import com.students.react.Model.Employee;
import com.students.react.Repository.EmployeeRepository;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return empRepo.findById(id)
                             .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee) {
        return empRepo.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = empRepo.findById(id)
                                          .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        validateEmployee(existingEmployee);

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        return empRepo.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        empRepo.deleteById(id);
    }

    private void validateEmployee(Employee employee) {
        // Perform validation logic
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Employee's first name cannot be empty");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Employee's last name cannot be empty");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Employee's email cannot be empty");
        }

        // Add more validation rules if needed
    }
}

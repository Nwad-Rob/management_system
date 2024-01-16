package com.students.react.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.react.Model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public List<Employee> findByEmail(String email);
    public List<Employee> findByFirstName(String firstName);
    public List<Employee> findByLastName(String lastName);
}

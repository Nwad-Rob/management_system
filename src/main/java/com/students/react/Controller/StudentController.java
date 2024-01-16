 package com.students.react.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.students.react.Model.Employee;
import com.students.react.Repository.EmployeeRepository;
import com.students.react.SpringException.ResourceNotFoundException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
    @Autowired
     EmployeeRepository empRepo;
     
// Get all employees

@GetMapping("/employees")
public List<Employee> getDetails(){
     return empRepo.findAll();
}

//Create Employees
@PostMapping("/employees")
public Employee createEmployee(@RequestBody Employee e) {
    return empRepo.save(e);
}

//Delete Employees
@DeleteMapping("{id}")
public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
     Employee e = empRepo.findById(id).
     orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));

     empRepo.delete(e);
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

//Update Employees

@PutMapping("employees/{id}")
public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
     Employee e = empRepo.findById(id)
     .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with Id" + id));

     e.setfirstName(employeeDetails.getfirstName());
     e.setLastName(employeeDetails.getLastName());
     e.setEmail(employeeDetails.getEmail());

     Employee updatedEmployee = empRepo.save(e);
     return ResponseEntity.ok(updatedEmployee);
}





@GetMapping("/employees/{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
     Employee e = empRepo.findById(id)
     .orElseThrow(() -> new ResourceNotFoundException("Empoyee not exist with id : " + id ));
     
     return ResponseEntity.ok(e);
}



//Get Employees by email
@GetMapping("/employees/{email}") 
public List<Employee> getByEmpEmail (@PathVariable("email") String email ){
     List<Employee> e = empRepo.findByEmail(email);
     return e;
     
}
//Get Employees by firstname
@GetMapping("employees/{firstName}")
public List<Employee> getByEmpFirstName(@PathVariable("firstName") String firstName){
     List<Employee> e = empRepo.findByFirstName(firstName);
     return e;
}

//Get Employees by lastname
@GetMapping("employees/{lastName}")
public List<Employee> getByEmpLastName(@PathVariable("lastName") String lastName){
     List<Employee> e = empRepo.findByLastName(lastName);
     return e;
}





}
package com.students.react.Repository;

import java.util.Optional;

// import org.hibernate.sql.results.LoadingLogger_.logger;
// import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.react.Model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    // List<Employee> empList = new ArrayList<>();

    // private static final Logger log = LoggerFactory.getLogger(EmployeeRepository.class);

    // List<Employee>findAll(){
    //     log.info("EmployeeRepository.findAll() called...")
    //     return empList;
    // }
   
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByFirstName(String firstName);
    Optional<Employee> findByLastName(String lastName);
    Optional <Employee>  findById(long id);
    
   
 
}

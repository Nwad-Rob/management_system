package com.students.react.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.react.Controller.EmployeeController;
import com.students.react.Model.Employee;
import com.students.react.Repository.EmployeeRepository;


import static org.mockito.Mockito.when;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Profile("test")
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest { 

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeRepository empRepo;

    @Autowired
    ObjectMapper objMap;

    List<Employee> empList = new ArrayList<>();

    

    @BeforeEach
    public void setUp(){
        empList = List.of(new Employee(1,"johnDoe123@gmail.com","John","Doe"),
                     new Employee(2,"robbyN@live.com","Rob","Nwad"));
    }

    @Test
    public void getAllEmployees() throws Exception{
        Mockito.when(empRepo.findAll()).thenReturn(empList);
        mockMvc.perform(get("/api/v1/employees")).
                andExpect(status().isOk())
            .andExpect(jsonPath("$[*].id").exists())
            .andExpect(jsonPath("$[*].email").exists())
            .andExpect(jsonPath("$[*].firstName").exists())
            .andExpect(jsonPath("$[*].lastName").exists());;
    }

    @Test
    public void testGetEmployees_EmptyList() throws Exception {
        // Mock the behavior of the repository to return an empty list when findAll() is called
        Mockito.when(empRepo.findAll()).thenReturn(Collections.emptyList());

        // Perform the GET request to "/api/v1"
        mockMvc.perform(get("/api/v1"))
               .andExpect(status().isNotFound()); // Expecting a 404 Not Found response
    }
    
    @Test
    public void isValid() throws Exception{
        Mockito.when(empRepo.findById(1)).thenReturn(Optional.of(empList.get(0)));

        
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotFindPostOnInvalidId(){
        
    }

}

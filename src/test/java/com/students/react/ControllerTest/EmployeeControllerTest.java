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
import com.students.react.SpringException.ResourceNotFoundException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        when(empRepo.findAll()).thenReturn(empList);
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
        when(empRepo.findAll()).thenReturn(Collections.emptyList());

        // Perform the GET request to "/api/v1"
        mockMvc.perform(get("/api/v1"))
               .andExpect(status().isNotFound()); // Expecting a 404 Not Found response
    }
    //tested
    @Test
    public void isValid() throws Exception{
       when(empRepo.findById(1)).thenReturn(Optional.of(empList.get(0)));

        
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotFindPostOnInvalidId() throws Exception{
        when(empRepo.findById(999)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/api/v1/employees/999"))
                .andExpect(status().isNotFound());
    }

    @SuppressWarnings("null")
    @Test
    public void shouldCreateNewPostIfValid() throws Exception{
        var emp = new Employee(3,"jully@hotmail.com","One","Piece");
        when(empRepo.save(emp)).thenReturn(emp);

        String json = """
                {
                    "id": %d,
                    "email": "%s",
                    "firstName": "%s",
                    "lastName": "%s"
                }
            """.formatted(emp.getId(), emp.getEmail(), emp.getFirstName(), emp.getLastName());


        mockMvc.perform(post("/api/v1/employees")
                .contentType("application/json")
                .content(json)).andExpect(status().isCreated());
    }

    // @Test
    // public void shouldCreateNewPostIsInValid() throws Exception{
    //# This is used when using @NotEmpty on firstName and lastName in the models (springboot validation)
    //     var emp = new Employee(3,"jully@hotmail.com","","");
    //     Mockito.when(empRepo.save(emp)).thenReturn(emp);

    //     String json = """
    //             {
    //                 "id": %d,
    //                 "email": "%s",
    //                 "firstName": "%s",
    //                 "lastName": "%s"
    //             }
    //         """.formatted(emp.getId(), emp.getEmail(), emp.getFirstName(), emp.getLastName());


    //     mockMvc.perform(post("/api/v1/employees")
    //             .contentType("application/json")
    //             .content(json)).andExpect(status().isBadRequest());
    // }

    @SuppressWarnings("null")
    @Test
    public void shouldUpdate() throws Exception{
        Employee updated = new Employee(1,"lol@home.com","just","jaded");
        when(empRepo.findById(1)).thenReturn(Optional.of(updated));
        when(empRepo.save(updated)).thenReturn(updated);
          
        String requestBody = """
                        {
                            "id": %d,
                            "email": "%s",
                            "firstName": "%s",
                            "lastName": "%s"
                        }
                    """.formatted(updated.getId(), updated.getEmail(), updated.getFirstName(), updated.getLastName());

                mockMvc.perform(put("/api/v1/employees/1")
               .contentType("application/json")
               .content(requestBody))
               .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployee() throws Exception{
        doNothing().when(empRepo).deleteById((long) 1);

        mockMvc.perform(delete("/api/v1/1"))
               .andExpect(status().isNoContent());

        verify(empRepo,times(1)).deleteById((long) 1);
    }



}

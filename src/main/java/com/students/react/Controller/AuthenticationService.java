package com.students.react.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.students.react.Config.JwtService;
import com.students.react.Model.Employee;
import com.students.react.Model.Role;
import com.students.react.Repository.EmployeeRepository;
import com.students.react.SpringException.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final EmployeeRepository empRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

   
    public AuthenticationResponse register(RegisterRequest request){
        var employee = Employee.builder()
                               .firstName(request.getFirstName())
                               .lastName(request.getLastName())
                               .email(request.getEmail())
                               .password(passwordEncoder.encode(request.getPassword()))
                               .role(Role.EMPLOYEE)
                               .build();

                               empRepo.save(employee);
                               var jwToken = jwtService.generateToken(employee);
                               
        return AuthenticationResponse.builder()
                                     .token(jwToken)
                                     .build();
      }

      public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
// This point means the username and password have been authenticated
        var employee = empRepo.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Employee email not found"));
        var jwToken = jwtService.generateToken(employee);
                               
        return AuthenticationResponse.builder()
                                     .token(jwToken)
                                     .build();
      }
    }

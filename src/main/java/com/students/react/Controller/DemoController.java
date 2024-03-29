package com.students.react.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth/v1/demo-controller")
public class DemoController {
    @GetMapping("/demo")
    public ResponseEntity <String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
    
}

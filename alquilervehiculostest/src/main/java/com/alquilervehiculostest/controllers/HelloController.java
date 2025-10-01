package com.alquilervehiculostest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String greet(){
        String greeting = "Hello World!";

        return greeting;
    }
}

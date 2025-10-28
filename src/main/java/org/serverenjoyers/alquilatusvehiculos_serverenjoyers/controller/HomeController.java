package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // <- IMPORTANTE: usar @Controller, no @RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";  // Thymeleaf buscará templates/index.html
    }
}

package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String soon(){
        return "login.html";
    }
}

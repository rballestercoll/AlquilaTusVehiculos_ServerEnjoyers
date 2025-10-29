package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroViewController {

    @GetMapping("/registro")
    public String soon(){
        return "registro";
    }
}

package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VehiculoViewController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("vehiculos")
    public String listarVehiculos(Model model){
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        return "vehiculos";
    }
}

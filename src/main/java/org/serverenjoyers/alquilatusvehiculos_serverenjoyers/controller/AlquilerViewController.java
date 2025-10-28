package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlquilerViewController {

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    // Listado de alquileres
    @GetMapping("/alquileres")
    public String listarAlquileres(Model model) {
        List<Alquiler> alquileres = alquilerService.getAll();
        model.addAttribute("alquileres", alquileres);
        return "alquileres"; // templates/alquileres.html
    }

    // Formulario para crear nuevo alquiler
    @GetMapping("/alquileres/nuevo")
    public String nuevoAlquiler(Model model) {
        Alquiler alquiler = new Alquiler();
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteService.getAll());
        model.addAttribute("vehiculos", vehiculoService.getAll());
        model.addAttribute("actionUrl", "/alquileres"); // POST para crear
        return "alquilerForm";
    }

    // Formulario para editar alquiler existente
    @GetMapping("/alquileres/editar/{id}")
    public String editarAlquiler(@PathVariable Long id, Model model) {
        Alquiler alquiler = alquilerService.getById(id);
        if (alquiler == null) {
            return "redirect:/alquileres";
        }
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteService.getAll());
        model.addAttribute("vehiculos", vehiculoService.getAll());
        model.addAttribute("actionUrl", "/alquileres"); // POST para actualizar
        return "alquilerForm";
    }

    // Borrar un alquiler
    @GetMapping("/alquileres/borrar/{id}")
    public String borrarAlquiler(@PathVariable Long id) {
        alquilerService.delete(id);
        return "redirect:/alquileres";
    }

    // Guardar o actualizar alquiler (POST)
    @PostMapping("/alquileres")
    public String guardarAlquiler(@ModelAttribute Alquiler alquiler) {
        alquilerService.save(alquiler);
        return "redirect:/alquileres";
    }

}

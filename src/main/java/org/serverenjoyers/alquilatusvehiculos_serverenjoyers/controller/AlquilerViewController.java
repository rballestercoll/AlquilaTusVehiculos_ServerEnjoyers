package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AlquilerViewController {

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/alquileres")
    public String listarAlquileres(Model model){
        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        model.addAttribute("alquileres", alquilerService.getAlquileres());
        if (!model.containsAttribute("alquiler")){
            model.addAttribute("alquiler", new Alquiler());
        }
        return "alquileres";
    }

    @PostMapping("/alquileres/nuevo")
    public String nuevoAlquiler(@ModelAttribute Alquiler alquiler, Model model, RedirectAttributes redirectAttributes){
        try {
            alquilerService.addAlquiler(alquiler.getCliente().getId(), alquiler.getVehiculo().getId(), alquiler);
            redirectAttributes.addFlashAttribute("successMessage", "Alquiler se ha registrado correctamente");
            return "redirect:/alquileres";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("alquileres", alquilerService.getAlquileres());
            model.addAttribute("clientes", clienteService.getClientes());
            model.addAttribute("vehiculos", vehiculoService.getVehiculos());
            model.addAttribute("alquiler", alquiler);
            return "alquileres";
        }
    }

    @PostMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            alquilerService.deleteAlquiler(id);
            redirectAttributes.addFlashAttribute("successMessage", "Alquiler eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/alquileres";
    }

    @GetMapping("/alquileres/editar/{id}")
    public String mostrarEditarAlquiler(@PathVariable Long id, Model model){
        Alquiler alquiler = alquilerService.getAlquiler(id);
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("alquileres", alquilerService.getAlquileres());
        model.addAttribute("openEditModal", true);
        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        return "alquileres";
    }

    @PostMapping("/alquileres/editar")
    public String editarAlquiler(@ModelAttribute Alquiler alquiler, Model model, RedirectAttributes redirectAttributes){
        try {
            alquilerService.updateAlquiler(alquiler.getId(), alquiler.getCliente().getId(), alquiler.getVehiculo().getId(), alquiler);
            redirectAttributes.addFlashAttribute("successMessage", "Alquiler actualizado correctamente");
            return "redirect:/alquileres";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "No se pudo actualizar: " + e.getMessage());
            model.addAttribute("alquileres", alquilerService.getAlquileres());
            model.addAttribute("openEditModal", true);
            model.addAttribute("alquiler", alquiler);
            return "alquileres";
        }
    }

































}

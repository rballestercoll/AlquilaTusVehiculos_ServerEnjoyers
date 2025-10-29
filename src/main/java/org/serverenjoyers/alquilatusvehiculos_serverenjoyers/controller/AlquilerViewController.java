package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String listarAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerService.getAlquileres());
        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());

        if (!model.containsAttribute("alquilerForm")) {
            model.addAttribute("alquilerForm", new Alquiler());
        }
        return "alquileres";
    }

    @PostMapping("/alquileres/nuevo")
    public String nuevoAlquiler(@RequestParam Long clienteId,
                                @RequestParam Long vehiculoId,
                                @ModelAttribute("alquilerForm") Alquiler alquiler,
                                RedirectAttributes redirectAttributes) {
        try {
            alquilerService.addAlquiler(clienteId, vehiculoId, alquiler);
            redirectAttributes.addFlashAttribute("successMessage", "Alquiler creado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("alquilerForm", alquiler);
            redirectAttributes.addFlashAttribute("showNuevoModal", true);
            redirectAttributes.addFlashAttribute("clienteSeleccionado", clienteId);
            redirectAttributes.addFlashAttribute("vehiculoSeleccionado", vehiculoId);
        }
        return "redirect:/alquileres";
    }

    @PostMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alquilerService.deleteAlquiler(id);
            redirectAttributes.addFlashAttribute("successMessage", "Alquiler eliminado correctamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/alquileres";
    }
}

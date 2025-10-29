package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateMatriculaException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VehiculoViewController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model){
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        if (!model.containsAttribute("vehiculo")){
            model.addAttribute("vehiculo", new Vehiculo());
        }
        return "vehiculos";
    }

    @PostMapping("/vehiculos/nuevo")
    public String nuevoVehiculo(@ModelAttribute Vehiculo vehiculo, Model model, RedirectAttributes redirectAttributes){
        try {
            vehiculoService.addVehiculo(vehiculo);
            redirectAttributes.addFlashAttribute("successMessage", "Vehículo se ha registrado correctamente");
            return "redirect:/vehiculos";
        } catch (DuplicateMatriculaException e){
            model.addAttribute("errorMessageNuevo", e.getMessage());
            model.addAttribute("vehiculos", vehiculoService.getVehiculos());
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos";
        }
    }

    @PostMapping("/vehiculos/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            vehiculoService.deleteVehiculo(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vehículo eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el vehículo");
        }
        return "redirect:/vehiculos";
    }

    @GetMapping("/vehiculos/editar/{id}")
    public String mostrarEditarVehiculo(@PathVariable Long id, Model model){
        Vehiculo vehiculo = vehiculoService.getVehiculo(id);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        model.addAttribute("openEditModal", true);
        return "vehiculos";
    }

    @PostMapping("/vehiculos/editar")
    public String editarVehiculo(@ModelAttribute Vehiculo vehiculo, Model model, RedirectAttributes redirectAttributes){
        try {
            vehiculoService.updateVehiculo(vehiculo);
            redirectAttributes.addFlashAttribute("successMessage", "Vehículo actualizado correctamente");
            return "redirect:/vehiculos";
        } catch (DuplicateMatriculaException e){
            model.addAttribute("errorMessageEdit", e.getMessage());
            model.addAttribute("vehiculos", vehiculoService.getVehiculos());
            model.addAttribute("openEditModal", true);
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos";
        } catch (Exception e){
            model.addAttribute("errorMessage", "No se pudo actualizar: " + e.getMessage());
            model.addAttribute("vehiculos", vehiculoService.getVehiculos());
            model.addAttribute("openEditModal", true);
            model.addAttribute("vehiculo", vehiculo);
            return "vehiculos";
        }
    }
}

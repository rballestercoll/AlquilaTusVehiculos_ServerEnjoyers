package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.AdminRegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Administrador;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public String verAdmin(Model model) {

        model.addAttribute("administradores", administradorService.getAdministradores());
        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        model.addAttribute("alquileres", alquilerService.getAlquileres());

        model.addAttribute("nuevoAdministrador", new Administrador());

        return "admin";
    }

    @PostMapping("/nuevo")
    public String crearAdministrador(@ModelAttribute AdminRegisterForm adminRegisterForm, Model model, RedirectAttributes redirectAttributes) {

        try {
            administradorService.addAdministrador(adminRegisterForm);
            redirectAttributes.addFlashAttribute("successAdmin", "Administrador registrado correctamente");
            return "redirect:/admin";
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorAdminModal", e.getMessage());

            // mantener los valores para rellenar el modal
            model.addAttribute("nombre", adminRegisterForm.getNombre());
            model.addAttribute("apellidos", adminRegisterForm.getApellidos());
            model.addAttribute("email", adminRegisterForm.getEmail());
            model.addAttribute("telefono", adminRegisterForm.getTelefono());

            // renderizamos la vista directamente
            model.addAttribute("administradores", administradorService.getAdministradores());
            return "admin";
        }
    }


    @GetMapping("/{id}/editar")
    public String mostrarEditarAdministrador(@PathVariable Long id, Model model) {

        model.addAttribute("administrador", administradorService.getAdministrador(id));
        return "admin-edit";
    }

    @PostMapping("/{id}/editar")
    public String editarAdministrador(@PathVariable Long id,
                                      @ModelAttribute Administrador administrador,
                                      RedirectAttributes redirectAttributes) {

        try {
            administrador.setId(id);
            administradorService.updateAdministrador(administrador);
            redirectAttributes.addFlashAttribute("successAdmin", "Administrador actualizado correctamente");
        } catch (DuplicateEmailException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error al actualizar administrador: " + e.getMessage());
        }

        return "redirect:/admin";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarAdministrador(@PathVariable Long id,
                                        RedirectAttributes redirectAttributes) {

        try {
            administradorService.deleteAdministrador(id);
            redirectAttributes.addFlashAttribute("successAdmin", "Administrador eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error al eliminar administrador: " + e.getMessage());
        }

        return "redirect:/admin";
    }
}

package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.enums.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.UsuarioService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class AlquilerViewController {

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/alquileres")
    public String listarAlquileres(Model model) {
        // Obtener usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // username logueado
        Usuario usuario = usuarioService.getUsuarioPorUsername(username).orElse(null);

        if (usuario != null && usuario.getRol() == Rol.USER) {
            // Traer el cliente correspondiente a este usuario
            clienteService.getClientePorEmail(username).ifPresent(cliente -> {
                model.addAttribute("clienteAutenticado", cliente);
            });
        }

        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        model.addAttribute("alquileres", alquilerService.getAlquileres());

        if (!model.containsAttribute("alquiler")) {
            model.addAttribute("alquiler", new Alquiler());
        }

        return "alquileres";
    }

    @PostMapping("/alquileres/nuevo")
    public String nuevoAlquiler(@ModelAttribute Alquiler alquiler,
                                @AuthenticationPrincipal Usuario usuario,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            if (usuario.getRol() == Rol.USER) {
                Cliente cliente = clienteService.getClientePorEmail(usuario.getUsername())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                alquiler.setCliente(cliente);
            }

            alquilerService.addAlquiler(alquiler.getCliente().getId(),
                    alquiler.getVehiculo().getId(),
                    alquiler);

            redirectAttributes.addFlashAttribute("successMessage", "Alquiler registrado correctamente");
            return "redirect:/alquileres";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("alquileres", alquilerService.getAlquileres());
            model.addAttribute("vehiculos", vehiculoService.getVehiculos());

            if (usuario.getRol() == Rol.ADMIN) {
                model.addAttribute("clientes", clienteService.getClientes());
            } else {
                Cliente cliente = clienteService.getClientePorEmail(usuario.getUsername())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                model.addAttribute("clienteAutenticado", cliente);
            }

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
    public String mostrarEditarAlquiler(@PathVariable Long id, Model model, @AuthenticationPrincipal Usuario usuario) {
        Alquiler alquiler = alquilerService.getAlquiler(id);
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("alquileres", alquilerService.getAlquileres());
        model.addAttribute("openEditModal", true);
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());

        if (usuario.getRol() == Rol.ADMIN) {
            model.addAttribute("clientes", clienteService.getClientes());
        } else {
            Cliente cliente = clienteService.getClientePorEmail(usuario.getUsername())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            model.addAttribute("clienteAutenticado", cliente);
        }

        return "alquileres";
    }

    @PostMapping("/alquileres/editar")
    public String editarAlquiler(@ModelAttribute Alquiler alquiler,
                                 @AuthenticationPrincipal Usuario usuario,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (usuario.getRol() == Rol.USER) {
                Cliente cliente = clienteService.getClientePorEmail(usuario.getUsername())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                alquiler.setCliente(cliente);
            }

            alquilerService.updateAlquiler(alquiler.getId(),
                    alquiler.getCliente().getId(),
                    alquiler.getVehiculo().getId(),
                    alquiler);

            redirectAttributes.addFlashAttribute("successMessage", "Alquiler actualizado correctamente");
            return "redirect:/alquileres";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "No se pudo actualizar: " + e.getMessage());
            model.addAttribute("alquileres", alquilerService.getAlquileres());
            model.addAttribute("openEditModal", true);
            model.addAttribute("alquiler", alquiler);
            model.addAttribute("vehiculos", vehiculoService.getVehiculos());

            if (usuario.getRol() == Rol.ADMIN) {
                model.addAttribute("clientes", clienteService.getClientes());
            } else {
                Cliente cliente = clienteService.getClientePorEmail(usuario.getUsername())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                model.addAttribute("clienteAutenticado", cliente);
            }

            return "alquileres";
        }
    }
}

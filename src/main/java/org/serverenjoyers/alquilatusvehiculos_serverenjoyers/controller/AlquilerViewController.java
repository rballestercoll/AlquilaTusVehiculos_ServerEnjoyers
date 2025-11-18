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
import org.springframework.security.core.Authentication;
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

    @Autowired
    private UsuarioService usuarioService;

    /** ---------------------------------------------------
     *   LISTAR
     *  --------------------------------------------------- */
    @GetMapping("/alquileres")
    public String listarAlquileres(Model model, Authentication authentication) {

        String username = authentication.getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Cargar alquileres según rol
        model.addAttribute("alquileres", alquilerService.getAlquileresVisiblesPara(username));

        // Admin → lista completa de clientes
        if (usuario.getRol() == Rol.ADMIN) {
            model.addAttribute("clientes", clienteService.getClientes());
        } else {
            // User → solo sus datos
            Cliente cliente = clienteService.getClientePorEmail(username)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            model.addAttribute("clienteAutenticado", cliente);
        }

        model.addAttribute("vehiculos", vehiculoService.getVehiculos());

        if (!model.containsAttribute("alquiler")) {
            model.addAttribute("alquiler", new Alquiler());
        }

        return "alquileres";
    }

    /** ---------------------------------------------------
     *   NUEVO ALQUILER
     *  --------------------------------------------------- */
    @PostMapping("/alquileres/nuevo")
    public String nuevoAlquiler(@ModelAttribute Alquiler alquiler,
                                Authentication authentication,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        try {
            String username = authentication.getName();
            Usuario usuario = usuarioService.getUsuarioPorUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (usuario.getRol() == Rol.USER) {
                // Asociar automáticamente al cliente logueado
                Cliente cliente = clienteService.getClientePorEmail(username)
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                alquiler.setCliente(cliente);
            }

            alquilerService.addAlquiler(
                    alquiler.getCliente().getId(),
                    alquiler.getVehiculo().getId(),
                    alquiler
            );

            redirectAttributes.addFlashAttribute("successMessage", "Alquiler registrado correctamente");
            return "redirect:/alquileres";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            cargarDatosParaFormulario(model, authentication.getName());
            model.addAttribute("alquiler", alquiler);
            return "alquileres";
        }
    }

    /** ---------------------------------------------------
     *   ELIMINAR
     *  --------------------------------------------------- */
    @PostMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alquilerService.deleteAlquiler(id);
            redirectAttributes.addFlashAttribute("successMessage", "Alquiler eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/alquileres";
    }

    /** ---------------------------------------------------
     *   MOSTRAR EDITAR
     *  --------------------------------------------------- */
    @GetMapping("/alquileres/editar/{id}")
    public String mostrarEditarAlquiler(@PathVariable Long id,
                                        Model model,
                                        Authentication authentication) {

        String username = authentication.getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alquiler alquiler = alquilerService.getAlquiler(id);

        model.addAttribute("alquiler", alquiler);
        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        model.addAttribute("openEditModal", true);

        if (usuario.getRol() == Rol.ADMIN) {
            model.addAttribute("clientes", clienteService.getClientes());
        } else {
            Cliente cliente = clienteService.getClientePorEmail(username)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            model.addAttribute("clienteAutenticado", cliente);
        }

        return "alquileres";
    }

    /** ---------------------------------------------------
     *   EDITAR
     *  --------------------------------------------------- */
    @PostMapping("/alquileres/editar")
    public String editarAlquiler(@ModelAttribute Alquiler alquiler,
                                 Authentication authentication,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            Usuario usuario = usuarioService.getUsuarioPorUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (usuario.getRol() == Rol.USER) {
                Cliente cliente = clienteService.getClientePorEmail(username)
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                alquiler.setCliente(cliente);
            }

            alquilerService.updateAlquiler(
                    alquiler.getId(),
                    alquiler.getCliente().getId(),
                    alquiler.getVehiculo().getId(),
                    alquiler
            );

            redirectAttributes.addFlashAttribute("successMessage", "Alquiler actualizado correctamente");
            return "redirect:/alquileres";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "No se pudo actualizar: " + e.getMessage());
            cargarDatosParaFormulario(model, authentication.getName());
            model.addAttribute("alquiler", alquiler);
            model.addAttribute("openEditModal", true);
            return "alquileres";
        }
    }

    /** ---------------------------------------------------
     *   MÉTODO DE APOYO (evita duplicar código)
     *  --------------------------------------------------- */
    private void cargarDatosParaFormulario(Model model, String username) {
        Usuario usuario = usuarioService.getUsuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("vehiculos", vehiculoService.getVehiculos());
        model.addAttribute("alquileres", alquilerService.getAlquileresVisiblesPara(username));

        if (usuario.getRol() == Rol.ADMIN) {
            model.addAttribute("clientes", clienteService.getClientes());
        } else {
            Cliente cliente = clienteService.getClientePorEmail(username)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            model.addAttribute("clienteAutenticado", cliente);
        }
    }
}

package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

// ... (imports)
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario; // <-- NUEVO
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService; // <-- NUEVO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @Autowired
    private ClienteService clienteService; // <-- AHORA SÍ LO NECESITAMOS

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/perfil")
    public String verPerfil(Model model,
                            @AuthenticationPrincipal Usuario usuarioLogueado) { // <-- CAMBIO: Es 'Usuario'

        try {
            // 1. Usamos el email del usuario logueado para buscar sus datos de Cliente
            // (Necesitarás un método findByEmail en tu ClienteService/Repository)
            Cliente cliente = clienteService.getClientePorEmail(usuarioLogueado.getEmail()); // <-- MÉTODO NUEVO

            // 2. Pasamos el Cliente (con nombre, etc.) a la vista
            model.addAttribute("cliente", cliente);

            // 3. Pasamos los alquileres de ESE cliente
            model.addAttribute("alquileres", alquilerService.getAlquileresPorCliente(cliente.getId()));

        } catch (RuntimeException e){
            return "redirect:/login?error=Cliente no encontrado";
        }

        return "perfil";
    }
}
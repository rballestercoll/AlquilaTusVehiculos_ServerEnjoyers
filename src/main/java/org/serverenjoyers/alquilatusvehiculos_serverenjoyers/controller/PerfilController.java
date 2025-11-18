package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public String verPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            // Teóricamente esto no ocurre si /perfil está protegido con .authenticated()
            return "redirect:/login?error";
        }

        // Buscamos el Usuario logueado
        Usuario usuario = usuarioService.getUsuarioPorUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscamos el Cliente asociado
        Cliente cliente = clienteService.getClientePorEmail(usuario.getUsername())
                .orElse(null);

        model.addAttribute("cliente", cliente);
        if (cliente != null) {
            model.addAttribute("alquileres", alquilerService.getAlquileresPorCliente(cliente.getId()));
        }

        return "perfil";
    }
}

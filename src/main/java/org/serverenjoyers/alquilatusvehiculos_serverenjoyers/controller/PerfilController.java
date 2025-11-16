package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/perfil")
    public String verPerfil(Model model,
                            @AuthenticationPrincipal Usuario usuarioLogueado) {

        try {
            // Usamos el email del usuario logueado para buscar sus datos de Cliente
            Cliente cliente = clienteService.getClientePorEmail(usuarioLogueado.getEmail());

            model.addAttribute("cliente", cliente);
            model.addAttribute("alquileres", alquilerService.getAlquileresPorCliente(cliente.getId()));

        } catch (RuntimeException e){
            // Si el cliente no se encuentra (raro, pero posible), volvemos al login
            return "redirect:/login?error=Datos de cliente no encontrados";
        }

        return "perfil";
    }
}
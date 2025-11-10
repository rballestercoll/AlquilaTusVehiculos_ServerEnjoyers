package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import jakarta.servlet.http.HttpSession;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
public class PerfilController {


    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/perfil")
    public String verPerfil(Model model, @AuthenticationPrincipal Cliente clienteLogueado) {
        try {
            // 5. Usamos directamente el objeto 'clienteLogueado'
            //    Spring Security nos lo da ya cargado de la BBDD.
            model.addAttribute("cliente", clienteLogueado);
            model.addAttribute("alquileres", alquilerService.getAlquileresPorCliente(clienteLogueado.getId()));

        } catch (RuntimeException e){
            // Si algo falla, lo mejor es redirigir al login
            return "redirect:/login?error=" + e.getMessage();
        }

        // 6. Devolvemos la vista de perfil
        return "perfil";
    }
}

package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import jakarta.servlet.http.HttpSession;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PerfilController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/perfil")
    public String verPerfil(Model model, HttpSession session, RedirectAttributes redirectAttributes){
        Long clienteId = (Long) session.getAttribute("clienteAutenticadoId");
        if (clienteId == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Debes iniciar sesión para acceder a tu perfil.");
            return "redirect:/login";
        }

        try {
            Cliente cliente = clienteService.getCliente(clienteId);
            model.addAttribute("cliente", cliente);
            model.addAttribute("alquileres", alquilerService.getAlquileresPorCliente(clienteId));
        } catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            session.invalidate();
            return "redirect:/login";
        }
        return "perfil";
    }
}

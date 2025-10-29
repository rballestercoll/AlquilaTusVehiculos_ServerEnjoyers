package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.LoginForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/login")
    public String mostrarLogin(Model model, HttpSession session) {
        if (session.getAttribute("clienteAutenticadoId") != null) {
            return "redirect:/perfil";
        }
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("loginForm") LoginForm loginForm,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            Cliente clienteAutenticado = clienteService.authenticateCliente(loginForm.getEmail(), loginForm.getTelefono());
            session.setAttribute("clienteAutenticadoId", clienteAutenticado.getId());
            session.setAttribute("clienteAutenticadoNombre", clienteAutenticado.getNombre());
            redirectAttributes.addFlashAttribute("successMessage", "Bienvenido, " + clienteAutenticado.getNombre() + "!");
            return "redirect:/perfil";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("loginForm", loginForm);
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Has cerrado sesión correctamente.");
        return "redirect:/login";
    }
}

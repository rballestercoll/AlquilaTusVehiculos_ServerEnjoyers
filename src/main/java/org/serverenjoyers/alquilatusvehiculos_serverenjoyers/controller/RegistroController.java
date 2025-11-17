package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute RegisterForm registerForm, RedirectAttributes redirectAttributes){
        try {
            registroService.registrarUsuario(registerForm);
            redirectAttributes.addFlashAttribute("successMessage", "Registro completado correctamente");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/registro";
        }
    }
}

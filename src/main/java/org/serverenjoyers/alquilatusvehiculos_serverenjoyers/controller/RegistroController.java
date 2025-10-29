package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
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
    private ClienteService clienteService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute("cliente") Cliente cliente,
                                   RedirectAttributes redirectAttributes) {
        try {
            clienteService.addCliente(cliente);
            redirectAttributes.addFlashAttribute("successMessage",
                    "¡Registro completado! Ya puedes acceder con tus credenciales.");
            return "redirect:/";
        } catch (DuplicateEmailException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("cliente", cliente);
            return "redirect:/registro";
        }
    }
}

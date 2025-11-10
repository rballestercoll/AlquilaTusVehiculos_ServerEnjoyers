package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder; // inyectamos el cifrado de configuracion

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
            String passwordCifrada = passwordEncoder.encode(cliente.getPassword());
            cliente.setPassword(passwordCifrada);
            // 4. ASIGNAMOS EL ROL POR DEFECTO
            cliente.setRol("ROLE_USER"); // Todos los nuevos registros serán usuarios normales

            // Ahora el service guardará el cliente con la pass cifrada y el rol
            clienteService.addCliente(cliente);

            redirectAttributes.addFlashAttribute("successMessage",
                    "¡Registro completado! Ya puedes acceder con tus credenciales.");

            // 5. REDIRIGIMOS AL LOGIN, NO A LA HOME
            // Es mejor que el usuario inicie sesión justo después de registrarse.
            return "redirect:/login";
        } catch (DuplicateEmailException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("cliente", cliente);
            return "redirect:/registro";
        }
    }
}

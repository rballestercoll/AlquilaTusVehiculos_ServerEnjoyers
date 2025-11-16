package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.RolRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "registro";
    }

    @PostMapping("/registro")
    @Transactional
    public String registrarCliente(@ModelAttribute("cliente") Cliente cliente,
                                   RedirectAttributes redirectAttributes) {

        try {
            // 1. Guardamos el Cliente (nombre, apellidos, email, telefono)
            //    Gracias al campo @Transient, el 'cliente' también trae la contraseña.
            Cliente clienteGuardado = clienteService.addCliente(cliente);

            // 2. Buscamos el Rol "USER" (ID 2)
            Rol rolUsuario = rolRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Error: Rol ID 2 (USER) no encontrado."));

            // 3. Creamos el nuevo Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(clienteGuardado.getEmail());
            nuevoUsuario.setPassword(passwordEncoder.encode(cliente.getPassword())); // Ciframos
            nuevoUsuario.setRol(rolUsuario);

            // 4. Guardamos el Usuario
            usuarioRepository.save(nuevoUsuario);

            redirectAttributes.addFlashAttribute("successMessage",
                    "¡Registro completado! Ya puedes acceder con tus credenciales.");
            return "redirect:/login";

        } catch (DuplicateEmailException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("cliente", cliente);
            return "redirect:/registro";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado: " + e.getMessage());
            redirectAttributes.addFlashAttribute("cliente", cliente);
            return "redirect:/registro";
        }
    }
}
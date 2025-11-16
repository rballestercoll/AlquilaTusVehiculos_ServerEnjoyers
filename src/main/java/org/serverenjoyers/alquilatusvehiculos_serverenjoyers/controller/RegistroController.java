package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

// ... (imports)
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
    private UsuarioRepository usuarioRepository; // <-- NUEVO

    @Autowired
    private RolRepository rolRepository; // <-- NUEVO

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "registro";
    }

    /**
     * Usamos @Transactional para que si falla el guardado del Usuario,
     * se deshaga (rollback) el guardado del Cliente.
     */
    @PostMapping("/registro")
    @Transactional
    public String registrarCliente(@ModelAttribute("cliente") Cliente cliente,
                                   RedirectAttributes redirectAttributes) {

        // El @ModelAttribute("cliente") tiene el email, nombre, apellidos, telefono
        // Y también hemos sido listos y hemos usado el campo 'password' en el HTML.
        // Spring lo capturará aunque 'Cliente' no tenga ese campo.
        // Pero para ser más limpios, deberíamos usar un DTO (un objeto de formulario).
        // Por ahora, lo recuperamos del objeto cliente aunque no sea un campo de entidad.

        // PERO, tu registro.html usa th:field="*{password}".
        // Esto NO funcionará si Cliente no tiene un campo password.

        // --- SOLUCIÓN RÁPIDA ---
        // 1. Añade `private String password;` (SIN @Column) a tu `Cliente.java`.
        // 2. Y su getter/setter `getPassword() / setPassword(String password)`
        // Así el formulario funciona, pero no se guarda en la BBDD de clientes.
        // --- ASUMAMOS QUE HEMOS HECHO LA SOLUCIÓN RÁPIDA ---

        try {
            // 1. Guardamos el Cliente (nombre, apellidos, email, telefono)
            Cliente clienteGuardado = clienteService.addCliente(cliente);

            // 2. Buscamos el Rol "USER" (ID 2, como pediste)
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
            redirectAttributes.addFlashAttribute("cliente", cliente); // Devuelve el cliente al formulario
            return "redirect:/registro";
        } catch (Exception e) {
            // Capturamos cualquier otro error (ej. Rol no encontrado)
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado durante el registro.");
            redirectAttributes.addFlashAttribute("cliente", cliente);
            return "redirect:/registro";
        }
    }
}
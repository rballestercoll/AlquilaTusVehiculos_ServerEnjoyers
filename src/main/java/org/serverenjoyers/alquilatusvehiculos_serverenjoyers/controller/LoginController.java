package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import jakarta.servlet.http.HttpSession;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("successMessage", "Sesión cerrada correctamente.");
        }
        return "login";
    }

//    @PostMapping("/login")
//    public String procesarLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
//        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
//
//        // usuarioOptional no existe
//        if (usuarioOptional.isEmpty()) {
//            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
//            return "login";
//        }
//
//        // contraseña incorrecta
//        if (!passwordEncoder.matches(password, usuarioOptional.get().getPassword())) {
//            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
//            return "login";
//        }
//
//        Usuario usuario = usuarioOptional.get();
//
//        // Guardamos usuario en sesión
//        session.setAttribute("usuarioAutenticadoId", usuario.getId());
//
//        // Guardamos cliente en sessión
//        Optional<Cliente> cliente = clienteRepository.findByEmail(usuario.getUsername());
//        cliente.ifPresent(value -> session.setAttribute("clienteAutenticadoId", value.getId())
//        );
//
//        return "redirect:/";
//    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}

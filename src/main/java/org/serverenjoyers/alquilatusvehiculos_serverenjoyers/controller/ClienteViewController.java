package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteViewController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/admin/clientes")
    public String listarClientes(Model model){
        model.addAttribute("clientes", clienteService.getClientes());
        if (!model.containsAttribute("cliente")){
            model.addAttribute("cliente", new Cliente());
        }
        return "clientes";
    }

    @PostMapping("/clientes/nuevo")
    public String nuevoCliente(@ModelAttribute RegisterForm registerForm, Model model, RedirectAttributes redirectAttributes) {
        try {
            clienteService.addCliente(registerForm);
            redirectAttributes.addFlashAttribute("successMessage", "Cliente se ha registado correctamente!");
            return "redirect:/clientes";
        } catch (DuplicateEmailException e){
            model.addAttribute("errorMessageNuevo", e.getMessage());
            model.addAttribute("clientes", clienteService.getClientes());
            model.addAttribute("cliente", registerForm);
            return "clientes";
        }
    }

    // Utilizaremos POST ya que HTML solo soporta GET y POST
    @PostMapping("/admin/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            clienteService.deleteCliente(id);
            redirectAttributes.addFlashAttribute("successMessage", "Cliente eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }

    // Para hacer UPDATE (PUT) obtenemos los datos con un GET y los actualizamos con un POST
    @GetMapping("/admin/clientes/editar/{id}")
    public String mostrarEditarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.getCliente(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("openEditModal", true);
        return "clientes";
    }

    @PostMapping("/admin/clientes/editar")
    public String editarCliente(@ModelAttribute Cliente cliente, Model model, RedirectAttributes redirectAttributes) {
        try {
            clienteService.updateCliente(cliente);
            redirectAttributes.addFlashAttribute("successMessage", "Cliente actualizado correctamente");
            return "redirect:/clientes";
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorMessageEdit", e.getMessage());
            model.addAttribute("clientes", clienteService.getClientes());
            model.addAttribute("openEditModal", true);
            model.addAttribute("cliente", cliente);
            return "clientes";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "No se pudo actualizar: " + e.getMessage());
            model.addAttribute("clientes", clienteService.getClientes());
            model.addAttribute("openEditModal", true);
            model.addAttribute("cliente", cliente);
            return "clientes";
        }
    }
}

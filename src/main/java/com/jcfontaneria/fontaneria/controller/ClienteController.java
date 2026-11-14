package com.jcfontaneria.fontaneria.controller;

import com.jcfontaneria.fontaneria.entity.Cliente;
import com.jcfontaneria.fontaneria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar todos los clientes
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes/lista-clientes"; // Apunta a la vista HTML en templates/clientes/lista-clientes.html
    }

    // Mostrar formulario para registrar un nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form-cliente"; // Apunta a la vista HTML en templates/clientes/form-cliente.html
    }

    // Guardar el cliente (con validación de Jakarta Bean Validation)
    @PostMapping("/guardar")
    public String guardarCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result) {

        // Buscar si ya existe un cliente con ese NIF en la base de datos
        Optional<Cliente> clienteExistente = clienteService.buscarPorNif(cliente.getNif());

        if (clienteExistente.isPresent()) {
            // Si el NIF existe, verificamos si pertenece a OTRO id diferente (evita falso positivo al editarte a ti mismo)
            boolean esNuevo = (cliente.getId() == null);
            boolean perteneceAOtroCliente = !clienteExistente.get().getId().equals(cliente.getId());

            if (esNuevo || perteneceAOtroCliente) {
                result.rejectValue("nif", "error.nif.duplicado", "El DNI/NIF ya se encuentra registrado por otro cliente");
            }
        }

        if (result.hasErrors()) {
            return "clientes/form-cliente";
        }

        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    // Eliminar un cliente por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
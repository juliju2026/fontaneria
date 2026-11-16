package com.jcfontaneria.fontaneria.controller;

import com.jcfontaneria.fontaneria.entity.Cliente;
import com.jcfontaneria.fontaneria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar todos los clientes
    @GetMapping
    public String listarClientes(@RequestParam(required = false) String filtro, Model model) {
        List<Cliente> clientes = clienteService.listarClientes(filtro);
        model.addAttribute("clientes", clientes);
        model.addAttribute("filtro", filtro); // Para mantener el texto escrito en la caja de búsqueda
        return "clientes/lista-clientes"; // Apunta a la vista HTML en templates/clientes/lista-clientes.html
    }

    // Mostrar formulario para registrar un nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form-cliente"; // Apunta a la vista HTML en templates/clientes/form-cliente.html
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> obtenerClienteJson(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    // Mostrar formulario precargado para editar un cliente existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de cliente no válido: " + id));

        model.addAttribute("cliente", cliente);
        return "clientes/form-cliente";
    }

    // Eliminar un cliente por su ID verifica que no tenga averias asociadas, de lo contrario no deja borrarlo
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminarCliente(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar este cliente porque tiene averías asociadas en el historial.");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/averias")
    public String verHistoricoAverias(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorIdConAverias(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de cliente no válido: " + id));

        model.addAttribute("cliente", cliente);
        model.addAttribute("averias", cliente.getAverias());
        return "clientes/historico-averias";
    }
}
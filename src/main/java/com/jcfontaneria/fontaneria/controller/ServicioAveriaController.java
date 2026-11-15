package com.jcfontaneria.fontaneria.controller;

import com.jcfontaneria.fontaneria.entity.ServicioAveria;
import com.jcfontaneria.fontaneria.service.CategoriaAveriaService; // Asumiendo que crearas este servicio
import com.jcfontaneria.fontaneria.service.ClienteService;
import com.jcfontaneria.fontaneria.service.ServicioAveriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/averias")
public class ServicioAveriaController {

    @Autowired
    private ServicioAveriaService averiaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CategoriaAveriaService categoriaAveriaService; // Para poblar el multiselect de categorías

    @GetMapping
    public String listarAverias(Model model) {
        model.addAttribute("averias", averiaService.listarTodas());
        return "averias/lista-averias";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaAveria(Model model) {
        model.addAttribute("servicioAveria", new ServicioAveria());
        model.addAttribute("clientes", clienteService.listarClientes(null)); // Para asignar un cliente
        model.addAttribute("categorias", categoriaAveriaService.listarTodas()); // Para las etiquetas ManyToMany
        return "averias/form-averia";
    }

    @PostMapping("/guardar")
    public String guardarAveria(@Valid @ModelAttribute("servicioAveria") ServicioAveria averia, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarClientes(null));
            model.addAttribute("categorias", categoriaAveriaService.listarTodas());
            return "averias/form-averia";
        }
        averiaService.guardarAveria(averia);
        return "redirect:/averias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        ServicioAveria averia = averiaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de avería no válido: " + id));
        model.addAttribute("servicioAveria", averia);
        model.addAttribute("clientes", clienteService.listarClientes(null));
        model.addAttribute("categorias", categoriaAveriaService.listarTodas());
        return "averias/form-averia";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAveria(@PathVariable Long id) {
        averiaService.eliminarAveria(id);
        return "redirect:/averias";
    }
}
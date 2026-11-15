package com.jcfontaneria.fontaneria.controller;

import com.jcfontaneria.fontaneria.entity.CategoriaAveria;
import com.jcfontaneria.fontaneria.service.CategoriaAveriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaAveriaController {

    @Autowired
    private CategoriaAveriaService categoriaAveriaService;

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaAveriaService.listarTodas());
        return "categorias/lista-categorias";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("categoriaAveria", new CategoriaAveria());
        return "categorias/form-categoria";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute("categoriaAveria") CategoriaAveria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "categorias/form-categoria";
        }
        categoriaAveriaService.guardarCategoria(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        CategoriaAveria categoria = categoriaAveriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de categoría no válido: " + id));
        model.addAttribute("categoriaAveria", categoria);
        return "categorias/form-categoria";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaAveriaService.eliminarCategoria(id);
        return "redirect:/categorias";
    }
}
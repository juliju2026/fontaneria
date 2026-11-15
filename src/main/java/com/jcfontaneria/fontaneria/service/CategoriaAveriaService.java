package com.jcfontaneria.fontaneria.service;

import com.jcfontaneria.fontaneria.entity.CategoriaAveria;
import com.jcfontaneria.fontaneria.repository.CategoriaAveriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaAveriaService {

    @Autowired
    private CategoriaAveriaRepository categoriaAveriaRepository;

    public List<CategoriaAveria> listarTodas() {
        return categoriaAveriaRepository.findAll();
    }

    public Optional<CategoriaAveria> buscarPorId(Long id) {
        return categoriaAveriaRepository.findById(id);
    }

    public void guardarCategoria(CategoriaAveria categoria) {
        categoriaAveriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaAveriaRepository.deleteById(id);
    }
}
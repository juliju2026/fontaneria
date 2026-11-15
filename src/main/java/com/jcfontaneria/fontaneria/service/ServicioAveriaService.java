package com.jcfontaneria.fontaneria.service;

import com.jcfontaneria.fontaneria.entity.ServicioAveria;
import com.jcfontaneria.fontaneria.repository.ServicioAveriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioAveriaService {

    @Autowired
    private ServicioAveriaRepository averiaRepository;

    public List<ServicioAveria> listarTodas() {
        return averiaRepository.findAllConCliente();
    }

    public List<ServicioAveria> listarPorCliente(Long clienteId) {
        return averiaRepository.findByClienteId(clienteId);
    }

    public Optional<ServicioAveria> buscarPorId(Long id) {
        return averiaRepository.findById(id);
    }

    public void guardarAveria(ServicioAveria averia) {
        averiaRepository.save(averia);
    }

    public void eliminarAveria(Long id) {
        averiaRepository.deleteById(id);
    }

}
package com.jcfontaneria.fontaneria.service;

import com.jcfontaneria.fontaneria.entity.Cliente;
import com.jcfontaneria.fontaneria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes(String filtro) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            return clienteRepository.findByNombreCompletoContainingIgnoreCase(filtro);
        }
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorNif(String nif) { return clienteRepository.findByNif(nif);
    }
    public List<Cliente> buscarClientes(String filtro) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            return clienteRepository.findByNombreCompletoContainingIgnoreCase(filtro);
        }
        return clienteRepository.findAll(); // Si está vacío, devuelve el listado completo
    }
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
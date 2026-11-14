package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado útil para buscar por NIF (aprovechando la restricción unique)
    Optional<Cliente> findByNif(String nif);

    // Busca clientes cuyo nombreCompleto contenga la cadena introducida (ignora mayúsculas/minúsculas con Containing)
    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}


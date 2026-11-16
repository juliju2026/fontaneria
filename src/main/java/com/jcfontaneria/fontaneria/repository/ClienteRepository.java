package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado útil para buscar por NIF (aprovechando la restricción unique)
    Optional<Cliente> findByNif(String nif);

    // Busca clientes cuyo nombreCompleto contenga la cadena introducida (ignora mayúsculas/minúsculas con Containing)
    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    // Trae los clientes y sus averías asociadas de forma ansiosa para evitar errores en las vistas
    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.averias")
    List<Cliente> findAllConAverias();

    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.averias WHERE c.nombreCompleto LIKE %?1%")
    List<Cliente> findByFiltroConAverias(String filtro);
}


package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.ServicioAveria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioAveriaRepository extends JpaRepository<ServicioAveria, Long> {

    @Query("SELECT a FROM ServicioAveria a LEFT JOIN FETCH a.cliente")
    List<ServicioAveria> findAllConCliente();

    // Permite listar todas las averías asociadas a un cliente específico (CRM puro)
    List<ServicioAveria> findByClienteId(Long clienteId);
}
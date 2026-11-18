package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.LogAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogAccesoRepository extends JpaRepository<LogAcceso, Long> {
    List<LogAcceso> findTop100ByOrderByFechaDesc(); //Metodo para buscar los ultimos 100 registros y los ordena de forma descendiente
}
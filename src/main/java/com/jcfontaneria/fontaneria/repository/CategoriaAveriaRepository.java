package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.CategoriaAveria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaAveriaRepository extends JpaRepository<CategoriaAveria, Long> {
    // Permite buscar por nombre si se requiere validar duplicados en el futuro
}
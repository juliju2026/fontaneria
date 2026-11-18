package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre); //Busca registro de rol por nombre
}
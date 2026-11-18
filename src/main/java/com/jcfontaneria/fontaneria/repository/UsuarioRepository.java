package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username); //busca un usuario en la tabla 'usuarios' por el parametro string 'username'
    boolean existsByUsername(String username); // TRUE cuando el string 'username' es existente, False cuando aun no existe
}
package com.jcfontaneria.fontaneria.service;

import com.jcfontaneria.fontaneria.entity.Usuario;
import com.jcfontaneria.fontaneria.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario, String claveSinCifrar) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        usuario.setPassword(passwordEncoder.encode(claveSinCifrar));
        return usuarioRepository.save(usuario);
    }
}

package com.jcfontaneria.fontaneria.service;

import com.jcfontaneria.fontaneria.entity.Usuario;
import com.jcfontaneria.fontaneria.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Spring interpreta hasRole("ADMINISTRADOR") como ROLE_ADMINISTRADOR.
        authorities.add(new SimpleGrantedAuthority(
                "ROLE_" + usuario.getRol().getNombre()));

        // Los permisos se incorporan como authorities independientes.
        usuario.getRol().getPermisos().forEach(p ->
                authorities.add(new SimpleGrantedAuthority(p.getNombre())));

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(authorities)
                .disabled(!usuario.isActivo())
                .build();
    }
}


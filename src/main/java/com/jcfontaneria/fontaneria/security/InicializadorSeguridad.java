package com.jcfontaneria.fontaneria.security;

import com.jcfontaneria.fontaneria.entity.Rol;
import com.jcfontaneria.fontaneria.entity.Usuario;
import com.jcfontaneria.fontaneria.repository.RolRepository;
import com.jcfontaneria.fontaneria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InicializadorSeguridad {

    @Bean
    CommandLineRunner cargarRolesYAdmin(
            RolRepository rolRepo,
            UsuarioRepository usuarioRepo,
            PasswordEncoder encoder,
            @Value("${app.admin.username}") String adminUsername,
            @Value("${app.admin.password:}") String adminPassword) {

        return args -> {
            crearRolSiNoExiste(rolRepo, "ADMINISTRADOR", "Acceso total");
            crearRolSiNoExiste(rolRepo, "COMERCIAL", "Gestión de clientes, presupuestos y facturas");
            crearRolSiNoExiste(rolRepo, "TÉCNICO", "Gestión de Averías y categorias");
            crearRolSiNoExiste(rolRepo, "CONSULTA", "Solo lectura");

            if (!usuarioRepo.existsByUsername(adminUsername)) {
                if (adminPassword == null || adminPassword.length() < 6) {
                    throw new IllegalStateException(
                            "Define APP_ADMIN_PASSWORD con al menos 6 caracteres");
                }
                Usuario admin = new Usuario();
                admin.setUsername(adminUsername);
                admin.setPassword(encoder.encode(adminPassword));
                admin.setNombre("Administrador");
                admin.setApellidos("Sistema");
                admin.setActivo(true);
                admin.setRol(rolRepo.findByNombre("ADMINISTRADOR").orElseThrow());
                usuarioRepo.save(admin);
            }
        };
    }

    private void crearRolSiNoExiste(RolRepository repo,
                                    String nombre, String descripcion) {
        if (repo.findByNombre(nombre).isEmpty()) {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            rol.setDescripcion(descripcion);
            repo.save(rol);
        }
    }
}

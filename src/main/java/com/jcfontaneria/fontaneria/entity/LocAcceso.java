package com.jcfontaneria.fontaneria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_accesos")
@Getter @Setter @NoArgsConstructor
public class LogAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String username;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String resultado; // CORRECTO o FALLIDO

    @Column(length = 45)
    private String direccionIp;
}

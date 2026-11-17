package com.jcfontaneria.fontaneria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permisos")
@Getter @Setter @NoArgsConstructor
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String nombre; // Ej.: PACIENTES_VER

    @Column(length = 200)
    private String descripcion;

    @Column(length = 40)
    private String modulo; // PACIENTES, CITAS, TRATAMIENTOS
}


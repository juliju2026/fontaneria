package com.jcfontaneria.fontaneria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Entity
@Table(name = "categorias_averia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaAveria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Column(unique = true, nullable = false)
    private String nombre; // Ej: "Fuga", "Atasco", "Calefacción", "Instalación Calentador", "Identificacion de fuga", "Acceso a fuga", "Reparacion fuga", Pueden ser tambien los conceptos para la facturación

    private String descripcion;

    // Relación: Una categoría puede estar en muchas averías
    @ToString.Exclude //para evitar que Lombok lea la colección y rompa la sesión
    @ManyToMany(mappedBy = "categorias")
    private List<ServicioAveria> averias;
}
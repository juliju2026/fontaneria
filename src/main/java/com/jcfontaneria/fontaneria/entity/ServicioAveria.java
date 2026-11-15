package com.jcfontaneria.fontaneria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "averias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioAveria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del problema es obligatoria")
    private String descripcionProblema;

    @NotBlank(message = "La direccion del servicio es obligatoria")
    private String direccionServicio;

    @NotBlank(message = "El nombre de la persona de contacto es obligatoria")
    private String nombreContacto;

    @NotBlank(message = "El teléfono de la persona de contacto es obligatoria")
    private String telefonoContacto;

    //TODO a futuro agregar un campo para adjuntar imagenes o vídeos de la avería

    // Relación con el tipo de avería (categoria). Un servicio puede tener varios tipos de averias. La relacion es N M
    @NotEmpty(message = "Debe seleccionar al menos una categoría para la avería")
    @ManyToMany
    @JoinTable(
            name= "averia_categoria",
            joinColumns = @JoinColumn(name = "averia_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<CategoriaAveria> categorias;

    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como texto en MySQL (ej: "PENDIENTE_PAGO")
    private EstadoOperativo estado = EstadoOperativo.PENDIENTE_ASIGNACION; // Valor por defecto al crearla

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE_PRESUPUESTO;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
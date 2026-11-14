package com.jcfontaneria.fontaneria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
//formulario rellenable por el mismo cliente en la web/whatsapp
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min=2, max = 100, message="El nombre debe tener entre 2 y 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @NotBlank(message = "El NIF/DNI/NIE es obligatorio") // podría ser opcional en un principio si el cliente no quiere dejar informacion
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]|[XYZ][0-9]{7}[A-Za-z]$", message = "El formato del NIF/DNI no es válido (Ej: 12345678A o X1234567Z)")
    @Column(unique = true, nullable = false, length = 10)
    private String nif; // Nuevo campo para el NIF/DNI con restricción de unicidad en base de datos

    @NotBlank(message = "El teléfono es obligatorio") //importante!
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "El formato del teléfono no es válido, minimo 7 digitos") //[0-9. ()-] = permite números del 0 al 9 puntos parentesis y guiones
    private String telefono; // Vital para la futura integración con WhatsApp

    @Email(message = "Debe ser un correo electrónico válido")
    private String correo;

    @NotBlank(message = "La dirección es obligatoria y no puede superar los 150 caracteres")
    @Column(nullable=false, length=150)
    private String direccion;

    // Relación para mantener el historial de averías del cliente (CRM puro)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioAveria> averias;
}

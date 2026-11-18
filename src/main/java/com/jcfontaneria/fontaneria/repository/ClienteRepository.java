package com.jcfontaneria.fontaneria.repository;

import com.jcfontaneria.fontaneria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado útil para buscar por NIF (aprovechando la restricción unique)
    Optional<Cliente> findByNif(String nif);

    // Busca clientes cuyo nombreCompleto contenga la cadena introducida (ignora mayúsculas/minúsculas con Containing) pero causa problemas al no traer las averias consigo
    //el containing actua como el comodin % ?1 %
    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombreCompleto); // busca en tabla de clientes, el fragmento de texto entrante como parametro dentro de los nombres

    // Trae los clientes y sus averías asociadas de forma ansiosa para evitar errores en las vistas
    //DISTINCT: Evita que se dupliquen filas en la lista de resultados de Java cuando un cliente tiene múltiples averías registradas.
    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.averias")
    List<Cliente> findAllConAverias();

    // Trae los usuarios filtrados por el nombreCompleto
    // Permite buscar clientes de forma parcial o por coincidencia en su nombre completo
    // (gracias a los comodines %) mientras mantiene la carga anticipada (JOIN FETCH) de las averías.
    // De este modo, si el usuario realiza una búsqueda en el buscador del CRM, los resultados devueltos también vienen con sus averías preparadas en la misma sesión, evitando cualquier excepción al renderizar la vista de listado.
    //el parametro filtro se traduce en la consulta al % (comomdin, no importa lo que venga antes) ?1 %(comodin no importa lo que va despues)
    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.averias WHERE c.nombreCompleto LIKE %?1%")
    List<Cliente> findByFiltroConAverias(String filtro);

    // Consulta que fuerza la carga de las averías filtrando por el ID del cliente
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.averias WHERE c.id = :id")
    Optional<Cliente> findByIdConAverias(@Param("id") Long id);
}


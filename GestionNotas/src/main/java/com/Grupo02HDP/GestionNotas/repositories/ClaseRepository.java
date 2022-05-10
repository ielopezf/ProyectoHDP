package com.Grupo02HDP.GestionNotas.repositories;

import com.Grupo02HDP.GestionNotas.models.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository <Clase, Long> {

    @Query(value = "Select c.id, m.codigo as codigoMateria, g.codigo as codigoGrupo, c.horario, c.ciclo, c.anio " +
            "from clases c, materia m, grupo g  where c.grupo= g.id AND g.docente_id = :idDocente", nativeQuery = true)
    List<Object> clases(Long idDocente);
}

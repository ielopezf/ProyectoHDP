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

    @Query(value = "Select c , u.carnet, u.nombre from Clase c, Usuario u where c.docente = u.id")
    List<Object> clasexDocente();

    @Query(value = "Select c , u.carnet, u.nombre from Clase c, Usuario u where u.id = :id_docente and c.docente = u.id")
    List<Object> clasexDocenteId(Long id_docente);


}

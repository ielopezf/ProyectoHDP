package com.Grupo02HDP.GestionNotas.repositories;

import com.Grupo02HDP.GestionNotas.models.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRespository extends JpaRepository<Calificacion, Long> {


    @Query(value = "Select c , u.carnet, u.nombre from Calificacion c, Usuario u where c.alumno = u.id ")
    List<Object> getCalificacionXAlumno();

    @Query(value ="Select a.carnet, c.nota ,t.descripcion , m.codigo, m.nombre as nombreMateria from usuarios a, calificaciones c, tipo_nota t, materia m where c.materia = m.id AND c.materia = :id AND c.tipo_nota = t.id AND c.alumno = a.id ", nativeQuery = true)
    List<Object> getCalificacionXMateria(int id);

}

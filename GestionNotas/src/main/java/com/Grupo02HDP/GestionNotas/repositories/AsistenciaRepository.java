package com.Grupo02HDP.GestionNotas.repositories;

import com.Grupo02HDP.GestionNotas.models.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository  extends JpaRepository<Asistencia, Long> {

    @Query(value = "Select a from Asistencia a, Materia m where a.alumno = :idAlumno And a.materia = m.id")
    List<Object> AsistenciaXAlumnoGrupo(int idAlumno);

    @Query(value = "Select m.codigo, g.codigo, u.carnet, h.hora from Horario h, Usuario u, Grupo g, Asistencia a, Materia m where a.materia = :materia" +
            " and a.materia = m.id and a.grupo = g.id and a.alumno = u.id and a.horario = h.id ")
    List<Object> AsistenciaXIdMateria(int materia);
}

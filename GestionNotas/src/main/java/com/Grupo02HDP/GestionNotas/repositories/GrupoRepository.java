package com.Grupo02HDP.GestionNotas.repositories;

import com.Grupo02HDP.GestionNotas.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Query(value = "Select g.codigo from grupo g where g.materia_id = :materia_id", nativeQuery = true)
    List<Grupo> gruposXMateria(Long materia_id);


    @Query(value = "Select g , u.carnet, u.nombre from Grupo g, Usuario u where g.docente = u.id")
    List<Object> grupoxDocente();

    @Query(value = "Select g , u.carnet, u.nombre from Grupo g, Usuario u where u.id = :id_docente and g.docente = u.id")
    List<Object> grupoxDocenteId(Long id_docente);
}

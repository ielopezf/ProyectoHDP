package com.Grupo02HDP.GestionNotas.repositories;

//import com.Grupo02HDP.GestionNotas.models.Grupo;
import com.Grupo02HDP.GestionNotas.models.AsistenciaDTO;
import com.Grupo02HDP.GestionNotas.models.Grupo;
import com.Grupo02HDP.GestionNotas.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {


    @Query(value = "Select g.codigo from grupo g where g.materia_id = :materia_id", nativeQuery = true)
    List<Grupo> gruposXMateria(Long materia_id);

    @Query(value = "Select m.codigo, m.nombre, u.carnet, u.nombre from Materia m, Usuario u where m.docente = u.id")
    List<Object> materiaxDocente();

    @Query(value = "select m.* from materia m ", nativeQuery = true)
   List<Object> getMaterias();

    @Query(value = "Select m.codigo as codigo, m.nombre as nombre, u.carnet as carnet , u.nombre as nombre_alumn h.id as horario, g.id as grupo from Horario h, Grupo g, Materia m, Usuario u where u.id = :id_docente and m.docente = u.id")
    List<AsistenciaDTO> materiaxDocenteId(Long id_docente);

    @Query(value = "Select id from Materia where codigo = :codigo")
    int idMateria (String codigo);




}

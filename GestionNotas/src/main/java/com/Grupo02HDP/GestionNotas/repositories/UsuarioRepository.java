package com.Grupo02HDP.GestionNotas.repositories;

import com.Grupo02HDP.GestionNotas.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    @Query(value = "SELECT u FROM Usuario u WHERE u.carnet LIKE %:carnet% ")
    Usuario getCredentials(@Param("carnet") String carnet);

    @Query(value = "select u from Usuario u where u.carnet LIKE %:filtro% or u.id LIKE %:filtro%")
    Usuario searchUser (@Param("filtro") String filtros);

    @Query(value = "select u from Usuario u where u.rol = 2")
    List<Object> docentes ();

}

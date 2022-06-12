package com.Grupo02HDP.GestionNotas.controllers;


import com.Grupo02HDP.GestionNotas.models.*;
import com.Grupo02HDP.GestionNotas.repositories.AsistenciaRepository;
import com.Grupo02HDP.GestionNotas.repositories.CalificacionRespository;
import com.Grupo02HDP.GestionNotas.repositories.MateriaRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/apis/asistenciaController")
public class AsistenciaController   {

    private Response response;
    @Autowired
    private ValidateToken validateToken;

    @Autowired
    private AsistenciaRepository asistenciaRepository;
    @Autowired
    private MateriaRepository materiaRepository;


    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();


    @PostMapping("/AgregarCalificacion")
    public Response AgregarCalificacion(@RequestHeader(value = "Authorization") String token, @RequestParam(value = "alumno") int id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario userLoggin = validateToken.userDB();
           List<AsistenciaDTO> materiaxDocente = materiaRepository.materiaxDocenteId(userLoggin.getId());
            List<AsistenciaDTO> materiaxAlumno = materiaRepository.materiaxDocenteId((long) id);

            for(int i = 0; i < materiaxAlumno.size(); i++){
                if(materiaxAlumno.get(i).getCodigo() == materiaxDocente.get(i).getCodigo()){
                    Asistencia asistencia = new Asistencia();
                    asistencia.setMateria(materiaRepository.idMateria(materiaxAlumno.get(i).getCodigo()));
                    //asistencia.setGrupo();
                }
            }


            response.setStatus(true);
            response.setMessage("Calificacion guardada correctamente!");
        }
        return response;
    }

    public void initializeResponse() {
        this.response = new Response();
    }

}

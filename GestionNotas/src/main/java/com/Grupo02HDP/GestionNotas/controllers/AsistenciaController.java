package com.Grupo02HDP.GestionNotas.controllers;


import com.Grupo02HDP.GestionNotas.models.*;
import com.Grupo02HDP.GestionNotas.repositories.AsistenciaRepository;
import com.Grupo02HDP.GestionNotas.repositories.CalificacionRespository;
import com.Grupo02HDP.GestionNotas.repositories.GrupoRepository;
import com.Grupo02HDP.GestionNotas.repositories.MateriaRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @Autowired
    private GrupoRepository grupoRepository;


    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();


    @PostMapping("/AgregarAsistencia")
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
                    asistencia.setGrupo(grupoRepository.getId(materiaxAlumno.get(i).getGrupo()));
                    asistencia.setAlumno(materiaxAlumno.get(i).getCarnet());
                    asistencia.setHorario(materiaxAlumno.get(i).getHorario());
                }
            }


            response.setStatus(true);
            response.setMessage("Calificacion guardada correctamente!");
        }
        return response;
    }

    @GetMapping("/getAsistencia")
    public Response getAsistencia(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(asistenciaRepository.findById(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/AsistenciaXAlumnoGrupo")
    public Response AsistenciaXAlumnoGrupo(@RequestHeader(value = "Authorization") String token, @RequestParam(value="id") int id ) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(asistenciaRepository.AsistenciaXAlumnoGrupo(id)));
            response.setStatus(true);
        }
        return response;
    }


    public void initializeResponse() {
        this.response = new Response();
    }

}

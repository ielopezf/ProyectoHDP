package com.Grupo02HDP.GestionNotas.controllers;


import com.Grupo02HDP.GestionNotas.models.Clase;
import com.Grupo02HDP.GestionNotas.models.Grupo;
import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.repositories.ClaseRepository;
import com.Grupo02HDP.GestionNotas.repositories.GrupoRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/apis/GrupoController")
public class GrupoController {

    private Response response;
    @Autowired
    private ValidateToken validateToken;
    @Autowired
    private GrupoRepository grupoRepository;

    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();

    @PostMapping("/AgregarGrupo")
    public Response AgregarGrupo(@RequestHeader(value = "Authorization") String token, @RequestBody Grupo grupo) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            grupoRepository.save(grupo);
            response.setStatus(true);
            response.setMessage("CLase guardada correctaente!");
        }
        return response;
    }

    @GetMapping("/getGrupo")
    public Response getGrupo(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(grupoRepository.findById(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getGrupoxDocente")
    public Response getGrupoxDocente(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(grupoRepository.grupoxDocente()));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getGruposxDocenteId")
    public Response getGruposxDocenteId(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(grupoRepository.grupoxDocenteId(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getGrupos")
    public Response getGrupos(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(grupoRepository.findAll()));
            response.setStatus(true);
        }
        return response;
    }

    @DeleteMapping("/eliminarGrupo")
    public Response eliminarGrupo(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (grupoRepository.findById(id).get() != null) {
                try {
                    grupoRepository.deleteById(id);
                    response.setStatus(true);
                    response.setMessage("Grupo eliminada correctamente!");
                } catch (DataAccessException ex) {
                    response.setException("Error! " + ex.getMessage());
                }
            } else {
                response.setException("El grupo no existe.");
            }
        }
        return response;
    }


    public void initializeResponse() {
        this.response = new Response();
    }


}

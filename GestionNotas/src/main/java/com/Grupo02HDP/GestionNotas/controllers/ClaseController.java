package com.Grupo02HDP.GestionNotas.controllers;


import com.Grupo02HDP.GestionNotas.models.Clase;
import com.Grupo02HDP.GestionNotas.models.Materia;
import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.repositories.ClaseRepository;
import com.Grupo02HDP.GestionNotas.repositories.MateriaRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/apis/ClaseController")
public class ClaseController {

    private Response response;
    @Autowired
    private ValidateToken validateToken;
    @Autowired
    private ClaseRepository claseRepository;

    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();

    @PostMapping("/AgregarClase")
    public Response AgregarClase(@RequestHeader(value = "Authorization") String token, @RequestBody Clase clase) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            claseRepository.save(clase);
            response.setStatus(true);
            response.setMessage("CLase guardada correctaente!");
        }
        return response;
    }

    @GetMapping("/getCLase")
    public Response getCLase(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(claseRepository.findById(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getClasexDocente")
    public Response getClasexDocente(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(claseRepository.clasexDocente()));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getClasesxDocenteId")
    public Response getClasesxDocenteId(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(claseRepository.clasexDocenteId(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getClases")
    public Response getClases(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(claseRepository.findAll()));
            response.setStatus(true);
        }
        return response;
    }

    @DeleteMapping("/eliminaClase")
    public Response eliminaClase(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (claseRepository.findById(id).get() != null) {
                try {
                    claseRepository.deleteById(id);
                    response.setStatus(true);
                    response.setMessage("CLase eliminada correctamente!");
                } catch (DataAccessException ex) {
                    response.setException("Error! " + ex.getMessage());
                }
            } else {
                response.setException("La Clase no existe.");
            }
        }
        return response;
    }


    public void initializeResponse() {
        this.response = new Response();
    }

}

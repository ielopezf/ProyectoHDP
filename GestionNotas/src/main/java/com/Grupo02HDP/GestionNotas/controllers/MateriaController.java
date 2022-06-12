package com.Grupo02HDP.GestionNotas.controllers;

import com.Grupo02HDP.GestionNotas.models.Materia;
import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.repositories.MateriaRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/apis/MateriaController")
public class MateriaController {

    private Response response;
    @Autowired
    private ValidateToken validateToken;
    @Autowired
    private MateriaRepository materiaRepository;

    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();


    @PostMapping("/AgregarMateria")
    public Response AgregarMateria(@RequestHeader(value = "Authorization") String token, @RequestBody Materia materia) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {

            if (stringValidation.validateAlphanumeric(materia.getCodigo(), 40)) {
                if (stringValidation.validateAlphabetic(materia.getNombre(), 40)) {
                    if (numberValidation.validateInteger(String.valueOf(materia.getMatricula()))) {
                        if (numberValidation.validateInteger(String.valueOf(materia.getEstado()))) {

                            materiaRepository.save(materia);
                            response.setStatus(true);
                            response.setMessage("Materia guardada correctaente!");
                        } else {
                            response.setException("Estado invalido");
                        }
                    } else {
                        response.setException("Matricula invalida");
                    }
                } else {
                    response.setException("nombre invalido");
                }
            } else {
                response.setException("codigo invalido");
            }
        }
        return response;
    }

    @GetMapping("/getMateria")
    public Response getMateria(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(materiaRepository.findById(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getMateriaxDocente")
    public Response getMateriaxDocente(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(materiaRepository.materiaxDocente()));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getMateriaxDocenteId")
    public Response getMateriaxDocente(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(materiaRepository.materiaxDocenteId(id)));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getMaterias")
    public Response getMaterias(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(materiaRepository.getMaterias()));
            response.setStatus(true);
        }
        return response;
    }

    @DeleteMapping("/eliminaMateria")
    public Response eliminaMateria(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (materiaRepository.findById(id).get() != null) {
                try {
                    materiaRepository.deleteById(id);
                    response.setStatus(true);
                    response.setMessage("Materia eliminada correctamente!");
                } catch (DataAccessException ex) {
                    response.setException("Error! " + ex.getMessage());
                }
            } else {
                response.setException("La Materia no existe.");
            }
        }
        return response;
    }


    public void initializeResponse() {
        this.response = new Response();
    }

}

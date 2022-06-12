package com.Grupo02HDP.GestionNotas.controllers;

import com.Grupo02HDP.GestionNotas.models.Calificacion;
import com.Grupo02HDP.GestionNotas.models.Clase;
import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.repositories.CalificacionRespository;
import com.Grupo02HDP.GestionNotas.repositories.ClaseRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/apis/ClaseController")
public class CalificacionController {

        private Response response;
        @Autowired
        private ValidateToken validateToken;
        @Autowired
        private CalificacionRespository calificacionRespository;

        private StringValidation stringValidation = new StringValidation();
        private NumberValidation numberValidation = new NumberValidation();

        @PostMapping("/AgregarCalificacion")
        public Response AgregarCalificacion(@RequestHeader(value = "Authorization") String token, @RequestBody Calificacion calificacion) {
            initializeResponse();
            if (!validateToken.validateToken(token)) {
                response.setException("Unauthorized access.");
            } else {
                calificacionRespository.save(calificacion);
                response.setStatus(true);
                response.setMessage("Calificacion guardada correctamente!");
            }
            return response;
        }

        @GetMapping("/getCalificacion")
        public Response getCalificacion(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
            initializeResponse();
            if (!validateToken.validateToken(token)) {
                response.setException("Unauthorized access.");
            } else {
                response.setDataset(Collections.singletonList(calificacionRespository.findById(id)));
                response.setStatus(true);
            }
            return response;
        }

        @GetMapping("/getCalificacionXAlumno")
        public Response getCalificacionXAlumno(@RequestHeader(value = "Authorization") String token) {
            initializeResponse();
            if (!validateToken.validateToken(token)) {
                response.setException("Unauthorized access.");
            } else {
                response.setDataset(Collections.singletonList(calificacionRespository.getCalificacionXAlumno()));
                response.setStatus(true);
            }
            return response;
        }


        @GetMapping("/getCalificaciones")
        public Response getCalificaciones(@RequestHeader(value = "Authorization") String token) {
            initializeResponse();
            if (!validateToken.validateToken(token)) {
                response.setException("Unauthorized access.");
            } else {
                response.setDataset(Collections.singletonList(calificacionRespository.findAll()));
                response.setStatus(true);
            }
            return response;
        }

        @DeleteMapping("/eliminarCalificacion")
        public Response eliminarCalificacion(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
            initializeResponse();
            if (!validateToken.validateToken(token)) {
                response.setException("Unauthorized access.");
            } else {
                if (calificacionRespository.findById(id).get() != null) {
                    try {
                        calificacionRespository.deleteById(id);
                        response.setStatus(true);
                        response.setMessage("Calificacion eliminada correctamente!");
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









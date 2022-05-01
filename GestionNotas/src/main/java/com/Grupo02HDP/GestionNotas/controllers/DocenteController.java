package com.Grupo02HDP.GestionNotas.controllers;

import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.models.Usuario;
import com.Grupo02HDP.GestionNotas.repositories.UsuarioRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/apis/DocenteController")
public class DocenteController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ValidateToken validateToken;

    private Response response;
    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();


    @GetMapping("/buscarDocentePorIdOCarnet")
    public Response buscarDocentePorIdOCarnet(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "filtros") String filtros) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario Docente = usuarioRepository.buscarDocentePorIdOCarnet (filtros);
            if (Docente != null) {
                response.getDataset().add(Docente);
                response.setStatus(true);
                String mensaje = "Docente " + Docente.getCarnet() + " encontrado exitosamente.";
                response.setMessage(mensaje);
            } else {
                response.setStatus(false);
                response.setMessage("Docente no encontrado verifique informacion!");
            }
        }
        return response;
    }

    @GetMapping("/getDocentes")
    public Response getDocentes(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(usuarioRepository.docentes());
            response.setStatus(true);
        }
        return response;
    }

    public void initializeResponse() {
        this.response = new Response();
    }


}

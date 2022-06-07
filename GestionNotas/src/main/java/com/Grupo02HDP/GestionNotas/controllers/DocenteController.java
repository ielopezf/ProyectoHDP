package com.Grupo02HDP.GestionNotas.controllers;

import com.Grupo02HDP.GestionNotas.models.*;
import com.Grupo02HDP.GestionNotas.repositories.ClaseRepository;
import com.Grupo02HDP.GestionNotas.repositories.GrupoRepository;
import com.Grupo02HDP.GestionNotas.repositories.MateriaRepository;
import com.Grupo02HDP.GestionNotas.repositories.UsuarioRepository;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.SQLException;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import com.Grupo02HDP.GestionNotas.utils.ValidateToken;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping(value = "/apis/DocenteController")
public class DocenteController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private ClaseRepository claseRepository;
    @Autowired
    private ValidateToken validateToken;

    private Response response;
    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();

    /////VISTA 1 DOCENTES
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


    /////////////VISTA AGREGAR DOCENTES
    @PostMapping("/AgregarDocente")
    public Response AgregarDocente(@RequestHeader(value = "Authorization") String token, @RequestBody Usuario user) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (stringValidation.validateAlphabetic(user.getNombre(), 40)) {
                if (stringValidation.validateAlphanumeric(user.getCarnet(), 40)) {
                    if (numberValidation.validatePhone(user.getTelefono())) {
                        if (stringValidation.validateEmail(user.getEmail())) {
                            if (stringValidation.validatePassword(user.getContrasena())) {
                                if (numberValidation.validateInteger(String.valueOf(user.getRol()))) {
                                    try {
                                        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                                        String hash = argon2.hash(1, 1024, 1, user.getContrasena());
                                        user.setContrasena(hash);
                                        usuarioRepository.save(user);
                                        response.setStatus(true);
                                        response.setMessage("Guardado correctaente!");
                                    } catch (DataAccessException ex) {
                                        response.setException(SQLException.getException(String.valueOf(ex.getCause())));
                                    }
                                } else {
                                    response.setException("Rol invalido");
                                }
                            } else {
                                response.setException("Contraeña invalida. verifica los requerimientos");
                            }
                        } else {
                            response.setException("correo electronico invalido");
                        }
                    } else {
                        response.setException("numero de telefono incorrecto");
                    }
                } else {
                    response.setException("Carnet invalido");
                }
            } else {
                response.setException("Nombre de usuario invalido");
            }
        }
        return response;
    }

    @GetMapping("/getMaterias")
    public Response getMaterias(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(materiaRepository.findAll()));
            response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/getGrupos")
    public Response getGrupos(@RequestHeader(value = "Authorization") String token, @RequestParam Long materia_id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(grupoRepository.gruposXMateria(materia_id)));
            response.setStatus(true);
        }
        return response;
    }

    @PostMapping("/crearClaseX")
    public Response crearClases(@RequestHeader(value = "Authorization") String token, @RequestBody Clase clase) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {


           claseRepository.save(clase);
           response.setStatus(true);
        }
        return response;
    }

    @GetMapping("/tablaClases")
    public Response tablaClases(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario docente = validateToken.userDB();
            response.setDataset(claseRepository.clases(docente.getId()));
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
                    response.setMessage("Clase eliminada correctamente!");
                } catch (DataAccessException ex) {
                    response.setException("Error! " + ex.getMessage());
                }
            } else {
                response.setException("La clase no existe.");
            }
        }
        return response;
    }


    //////////////////////////VISTA VER PERFIL DOCENTE////////////
    @GetMapping("/getDocente")
    public Response getDocente(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            response.setDataset(Collections.singletonList(usuarioRepository.findById(id)));
            response.setStatus(true);
        }
        return response;
    }

    @PostMapping("/actualizarContrasena")
    public Response actualizarContrasena(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "contrasena1") String contrasena1, @RequestParam(name = "contrasena2") String contrasena2, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario userDB = usuarioRepository.getById(id);
            if (stringValidation.validatePassword(contrasena1)) {
                if (stringValidation.validatePassword(contrasena2)) {
                    if (userDB.getContrasena().equals(contrasena1)) {
                        try {
                            userDB.setContrasena(contrasena2);
                            usuarioRepository.save(userDB);
                            response.setStatus(true);
                            response.setMessage("Contraseña actualizada exitosamente!");
                        } catch (DataAccessException ex) {
                            response.setException(SQLException.getException(String.valueOf(ex.getCause())));
                        }
                    } else {
                        response.setException("Contraseñas no son identicas");
                    }
                } else {
                    response.setMessage("Antigua contraseña invalida");
                }
            } else {
                response.setMessage("Nueva contraseña invalida");
            }
        }
        return response;
    }


    public void initializeResponse() {
        this.response = new Response();
    }


}

package com.Grupo02HDP.GestionNotas.controllers;


import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.models.Rol;
import com.Grupo02HDP.GestionNotas.models.Usuario;
import com.Grupo02HDP.GestionNotas.repositories.RolRepository;
import com.Grupo02HDP.GestionNotas.repositories.UsuarioRepository;
import com.Grupo02HDP.GestionNotas.utils.JWTUtil;
import com.Grupo02HDP.GestionNotas.utils.NumberValidation;
import com.Grupo02HDP.GestionNotas.utils.SQLException;
import com.Grupo02HDP.GestionNotas.utils.StringValidation;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class AuthController {

    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RolRepository rol;


    private Response response;
    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();


    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public Response login(@RequestBody Usuario user) {
        initializeResponse();
        if (!Objects.equals(user.getCarnet(), "") || !Objects.equals(user.getContrasena(), "")) {
            if (stringValidation.validatePassword(user.getContrasena())) {
                Usuario userDB = userRepository.getCredentials(user.getCarnet());
                if (!Objects.equals(userDB, null)) {
                    String passHash = userDB.getContrasena();
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    if (argon2.verify(passHash, user.getContrasena())) {
                        String tokenJwt = jwtUtil.create((userDB.getId()), userDB.getEmail());
                        Usuario returnUser = new Usuario();
                        returnUser.setRol(userDB.getRol());
                        returnUser.setCarnet(userDB.getCarnet());
                        response.setStatus(true);
                        response.setToken(tokenJwt);
                        response.getDataset().add(returnUser);
                        response.setMessage("Session created successfully!");
                    } else {
                        response.setException("Incorrect password.");
                    }

                } else {
                    response.setException("Sorry! Looks like this email is not associated to an account.");
                }
            } else {
                response.setException("Sorry! Looks like your password is invalid.");
            }

        } else {
            response.setException("Please fill all the fields.");
        }
        return response;
    }

    @PostMapping(value = "api/register")
    public Response registerUser(@RequestBody Usuario user) {
        initializeResponse();
        if (stringValidation.validateAlphabetic(user.getNombre(), 40)) {
            if (stringValidation.validateAlphanumeric(user.getCarnet(), 40)) {
                if (stringValidation.validateEmail(user.getEmail())) {
                    if (stringValidation.validatePassword(user.getContrasena())) {
                        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                        String hash = argon2.hash(1, 1024, 1, user.getContrasena());
                        user.setContrasena(hash);
                        try {
                            userRepository.save(user);
                            response.setStatus(true);
                            response.setMessage("Registered successfully");
                            response.setStatus(true);
                        } catch (DataAccessException ex) {
                            response.setException(SQLException.getException(String.valueOf(ex.getCause())));
                        }
                    } else {
                        response.setException("Invalid password. Please check that your password satisfies all the requirements.");
                    }
                } else {
                    response.setException("Invalid email.");
                }
            } else {
                response.setException("Invalid username.");
            }
        } else {
            response.setException("Invalid name.");
        }

        return response;
    }






    public void initializeResponse() {
        this.response = new Response();
    }





}



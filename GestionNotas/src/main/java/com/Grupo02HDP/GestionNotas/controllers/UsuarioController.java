package com.Grupo02HDP.GestionNotas.controllers;

import com.Grupo02HDP.GestionNotas.models.Response;
import com.Grupo02HDP.GestionNotas.models.Usuario;
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

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ValidateToken validateToken;

    private Response response;
    private StringValidation stringValidation = new StringValidation();
    private NumberValidation numberValidation = new NumberValidation();


    ///////////////////////////////////////////////////ESPECIFICOS///////////////////////////////////
    @GetMapping("/getUsuarioLogeado")
    public Response usuarioLoggin(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario userLoggin = validateToken.userDB();
            Usuario user = new Usuario();
            user.setId(userLoggin.getId());
            user.setNombre(userLoggin.getNombre());
            user.setCarnet(userLoggin.getCarnet());
            user.setRol(userLoggin.getRol());
            user.setTelefono(userLoggin.getTelefono());
            user.setEmail(userLoggin.getEmail());
            user.setDireccion(userLoggin.getDireccion());
            response.getDataset().add(user);
            response.setStatus(true);

        }
        return response;
    }

    @GetMapping("/buscarPorIdOCarnet")
    public Response buscar(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "filtros") String filtros) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario usuario = usuarioRepository.searchUser(filtros);
            if (usuario != null) {
                response.getDataset().add(usuario);
                response.setStatus(true);
                String mensaje = "Usuario " + usuario.getCarnet() + " encontrado exitosamente.";
                response.setMessage(mensaje);
            } else {
                response.setStatus(false);
                response.setMessage("Usuario no encontrado verifique informacion!");
            }
        }
        return response;
    }



    ////////////////////////////////////////////////////GENERALES////////////////////////////////////
    @GetMapping("/getUsuario")
    public Response getUsuario(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Acceso no autorizado");
        } else {
            if (usuarioRepository.findById(id) != null) {
                Usuario user = usuarioRepository.findById(id).get();
                response.getDataset().add(user);
                response.setStatus(true);
            } else {
                response.setException("El usuario no existe");
            }
        }
        return response;
    }

    @PostMapping("/crearUsuario")
    public Response crearUsuario(@RequestHeader(value = "Authorization") String token, @RequestBody Usuario user) {
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

    @GetMapping("/getUsuarios")
    public Response getUsuarios(@RequestHeader(value = "Authorization") String token) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Acceso no autorizado");
        } else {
            response.getDataset().add(usuarioRepository.findAll());
        }
        return response;
    }

    @DeleteMapping("/eliminarUsuario")
    public Response eliminarUsuario(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "id") Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (usuarioRepository.findById(id).get() != null) {
                try {
                    usuarioRepository.deleteById(id);
                    response.setStatus(true);
                    response.setMessage("Usuario eliminado correctamente!");
                } catch (DataAccessException ex) {
                    response.setException("Error! " + ex.getMessage());
                }
            } else {
                response.setException("El usuario no existe.");
            }
        }
        return response;
    }

    @PostMapping("/actualizarUsuario")
    public Response actualizarUsuario(@RequestHeader(value = "Authorization") String token, @RequestBody Usuario user) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (stringValidation.validateAlphabetic(user.getNombre(), 40)) {
                if (stringValidation.validateAlphanumeric(user.getCarnet(), 40)) {
                    if (numberValidation.validatePhone(user.getTelefono())) {
                        if (stringValidation.validateEmail(user.getEmail())) {
                            try {
                                Usuario userDB = usuarioRepository.findById(user.getId()).get();
                                userDB.setNombre(user.getNombre());
                                userDB.setCarnet(user.getCarnet());
                                userDB.setTelefono(user.getTelefono());
                                userDB.setEmail(user.getEmail());
                                userDB.setRol(user.getRol());
                                usuarioRepository.save(userDB);
                                response.setMessage("Actualizado correctamente.");
                                response.setStatus(true);
                            } catch (DataAccessException ex) {
                                response.setException(SQLException.getException(String.valueOf(ex.getCause())));
                            }
                        } else {
                            response.setException("Correo electronico invalido");
                        }
                    } else {
                        response.setException("Numero de telefono invalido.");
                    }
                } else {
                    response.setException("Carnet ha sido ingresado invalido.");
                }
            } else {
                response.setException("Nombre de usuario invalido.");
            }
        }
        return response;
    }

    @PostMapping("/actualizarContrasena")
    public Response actualizarContrasena(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "contrasena1") String contrasena1, @RequestParam(name = "contrasena2") String contrasena2) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            Usuario userDB = validateToken.userDB();
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

    @GetMapping("resetearContrasena")
    public Response resetearContrasena(@RequestHeader(value = "Authorization") String token, @RequestParam Long id) {
        initializeResponse();
        if (!validateToken.validateToken(token)) {
            response.setException("Unauthorized access.");
        } else {
            if (validateToken.userDB().getRol().getId() == 1) {
                if (usuarioRepository.existsById(id)) {
                    Usuario user = usuarioRepository.findById(id).get();
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String hash = argon2.hash(1, 1024, 1, "Pass123@");
                    user.setContrasena(hash);
                    usuarioRepository.save(user);
                    response.setStatus(true);
                    response.setMessage("La contraseña ha sido cambiada a Pass123@, cambia tu contraseña al hacer login.");
                } else {
                    response.setException("El usuario no existe.");
                }
            } else {
                response.setException("Tu no eres un adimnistrador");
            }
        }
        return response;
    }


    public void initializeResponse() {
        this.response = new Response();
    }

}

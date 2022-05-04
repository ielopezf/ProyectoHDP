package com.Grupo02HDP.GestionNotas.utils;




import com.Grupo02HDP.GestionNotas.models.Usuario;
import com.Grupo02HDP.GestionNotas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateToken {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UsuarioRepository userRepository;
    private Long userId;

    public boolean validateToken(String token) {
        try {
            userId = Long.valueOf(jwtUtil.getKey(token));
            return userId != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario userDB() {
        return userRepository.getById(userId);
    }

}

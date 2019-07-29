package javatp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javatp.repositories.UserRepository;
import javatp.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// La API utiliza JSON Web Tokens para autenticación.
// Esta clase se encarga de generar y autenticar los tokens.

@Component
public class AuthenticationManager {
    // Para logging en consola
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

    // Para hashing de contraseñas
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Key para encriptación de tokens
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de expiración
    // public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public User authenticateToken(String token) throws Exception {
        String username;
        try {
            // Devuelve el nombre de usuario encriptado en el token y lo valida
            username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException expiredException) {
            // El token expiró
            throw new Exception("Expired token");
        } catch (Exception e) {
            // El token es inválido
            throw new Exception("Bad token");
        }
        User user = UserRepository.get().getOne(username);
        if (user == null)
            // El usuario no existe
            throw new Exception("User not found");
        return user;
    }

    public String generateToken(User user) throws Exception {
        // Busca el usuario con ese nombre
        User savedUser = UserRepository.get().getOne(user.getUsername());

        if (savedUser == null) {
            // Si el usuario no existe, lanza una excepción
            throw new Exception("Username not found");
        } else if (encoder.matches(user.getPassword(), savedUser.getPassword())) {
            // Si la contraseña coincide, se genera el token

            // Claims son las propiedades que se encriptarán en el token
            // Se pueden agregar propiedades con el método put(String,Object)
            Claims claims = Jwts.claims();

            // Se debe setear el usuario como sujeto del token
            claims.setSubject(user.getUsername());

            // Se puede setear un tiempo de expiración para el token con este fragmento:
            // claims.setIssuedAt(new Date(System.currentTimeMillis())) .setExpiration(new
            // Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))

            // Se genera el token, se le setean las propiedades, y se lo encripta
            return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        } else {
            // La contraseña es inválida
            throw new Exception("Wrong password");
        }
    }
}
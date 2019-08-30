package javatp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javatp.domain.User;
import javatp.exception.AuthenticationException;
import javatp.service.UserService;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

// La API utiliza JSON Web Tokens para autenticación.
// Esta clase se encarga de generar y autenticar los tokens.

@Component
public class AuthenticationProvider {
    @Autowired
    private UserService users;
     
    // Key para encriptación de tokens
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de expiración
    // public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public User authenticateToken(String token) throws AuthenticationException {
        String username;
        try {
            // Devuelve el nombre de usuario encriptado en el token y lo valida
            username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException expiredException) {
            // El token expiró
            throw new AuthenticationException("Expired token");
        } catch (Exception e) {
            // El token es inválido
            throw new AuthenticationException("Bad token");
        }
        User user = users.findUser(username);
        if (user == null)
            // El usuario no existe
            throw new AuthenticationException("User not found");
        return user;
    }

    public String generateToken(User user) throws AuthenticationException {
        if(users.authenticateUser(user)) {
            // Claims son las propiedades que se encriptarán en el token
            // Se pueden agregar propiedades con el método put(String,Object)
            Claims claims = Jwts.claims();

            claims.put("id", user.getId());

            // Se debe setear el usuario como sujeto del token
            claims.setSubject(user.getUsername());

            // Se puede setear un tiempo de expiración para el token con este fragmento:
            // claims.setIssuedAt(new Date(System.currentTimeMillis())) .setExpiration(new
            // Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))

            // Se genera el token, se le setean las propiedades, y se lo encripta
            return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        } else {
            // No se autenticó correctamente
            throw new AuthenticationException("Wrong username or password");
        }
    }
}
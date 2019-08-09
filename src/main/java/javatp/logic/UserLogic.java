package javatp.logic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import javatp.entities.User;
import javatp.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserLogic{
    @Autowired
    UserRepository userRepo;
    // Para hashing de contraseñas
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User getOne(Long id){
        User user = userRepo.getOne(id);
        user.setPassword(null);
        return user;
    }
    public User findByUsername(String username){
        User user = userRepo.findByUsername(username);
        user.setPassword(null);
        return user;
    }
    public User save(User user){
        // Hashear la contraseña
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        // Borrar la contraseña de las instancias
        savedUser.setPassword(null);
        user.setPassword(null);
        return savedUser;
    }
    public List<User> findAll(){
        List<User> users = userRepo.findAll();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }
    public boolean authenticate(User user){
        User savedUser = userRepo.findByUsername(user.getUsername());
        boolean auth = false;
        if (savedUser != null && encoder.matches(user.getPassword(), savedUser.getPassword())){
            auth = true;
        }
        user.setPassword(null);
        return auth;
    }

}
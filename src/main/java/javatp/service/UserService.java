package javatp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javatp.exception.EntityContentRepeatedException;
import javatp.exception.UsernameTakenException;
import java.util.List;
import javatp.domain.User;
import javatp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    UserRepository userRepo;
    
    // Para hashing de contraseñas
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User getUser(Long id){
        User user = userRepo.getOne(id);
        user.setPassword(null);
        return user;
    }
    public User findUser(String username){
        User user = userRepo.findByUsername(username);
        user.setPassword(null);
        return user;
    }
    public User createUser(User user) {
        if(user.getUsername().equals("")&&user.getUsername()!=null){
            // Checkeo que el nombre no exista
            if (!userRepo.existsByUsername(user.getUsername())) {
                // Hashear la contraseña
                user.setPassword(encoder.encode(user.getPassword()));
                User savedUser = userRepo.save(user);
                // Borrar la contraseña de las instancias
                savedUser.setPassword(null);
                user.setPassword(null);
                return savedUser;
            } else throw new UsernameTakenException("Username already in use, select another");
        } else throw new EntityContentRepeatedException("Username can't be empty");
    }
    public List<User> getAllUsers(){
        List<User> users = userRepo.findAll();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }
    public boolean authenticateUser(User user){
        User savedUser = userRepo.findByUsername(user.getUsername());
        boolean auth = false;
        if (savedUser != null && encoder.matches(user.getPassword(), savedUser.getPassword())){
            auth = true;
        }
        user.setPassword(null);
        return auth;
    }

}
package javatp.repositories;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javatp.entities.User;

public class UserRepository {
    private static UserRepository instance;
    private HashMap<String, User> users = new HashMap<String, User>();

    private UserRepository() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.users.put("fede", new User("fede", encoder.encode("fedefede")));
    }

    public static UserRepository get() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User getOne(String username) {
        return users.get(username);
    }
    public Collection<User> getAll(){
        return users.values();
    }
    public void save(User user) throws Exception{
        if(user == null || user.getUsername() == null) throw new Exception("Object can't be null");
        if(users.containsKey(user.getUsername())) throw new Exception("Object already defined");
        users.put(user.getUsername(),user);
    }
}
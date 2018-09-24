package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Boolean createUser(User user) {
        if (userRepository.getByUsername(user.getUsername()) != null) {
            return false;
        }
        encryptPassword(user);
        userRepository.save(user);
        return true;
    }

    public User getUserByName(String username) {
        return userRepository.getByUsername(username);
    }

    private void encryptPassword(User user){
        String password = user.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        user.setPassword(password);
    }


}

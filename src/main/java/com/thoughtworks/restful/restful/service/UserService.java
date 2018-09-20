package com.thoughtworks.restful.restful.service;

import com.thoughtworks.restful.restful.model.User;
import com.thoughtworks.restful.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Boolean createUser(User user) {
        if (userRepository.getByUsername(user.getUsername()) != null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public User getUserByName(String username) {
        return userRepository.getByUsername(username);
    }


}

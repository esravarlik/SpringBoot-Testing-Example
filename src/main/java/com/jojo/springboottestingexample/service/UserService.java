package com.jojo.springboottestingexample.service;

import com.jojo.springboottestingexample.exception.UserApplicationException;
import com.jojo.springboottestingexample.model.User;
import com.jojo.springboottestingexample.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUser(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserApplicationException("Exception occurred while get demo by id"));
    }
    public User createUser(String userValue) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setValue(userValue);

        return userRepository.save(user);
    }

    public List<User> getUsers(String value) {
        return userRepository.findByValue(value);
    }

    public List<User> getUsersByData(String value) {
        return userRepository.findByValueLikeIgnoreCase(value);
    }
}

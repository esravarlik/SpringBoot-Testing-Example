package com.jojo.springboottestingexample.controller;

import com.jojo.springboottestingexample.model.User;
import com.jojo.springboottestingexample.request.UserRequest;
import com.jojo.springboottestingexample.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<User>> getUsersByValue(@RequestParam String value){
        return new ResponseEntity<>(
                userService.getUsers(value), HttpStatus.OK

        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(
                userService.getUser(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/data")
    public ResponseEntity<List<User>> getUsersByData(@RequestParam String value){
        return new ResponseEntity<>(
                userService.getUsersByData(value), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(
                userService.createUser(userRequest.getValue()), HttpStatus.CREATED
        );
    }


}

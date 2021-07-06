package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserDAO userDAO = new UserDAO();

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userDAO.selectAll());
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        return ResponseEntity.ok(userDAO.selectUserByID(id));
    }
    @PostMapping(value = "/user")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userDAO.insertUser(user));
    }



}

package com.example.authorization.controllers;

import com.example.authorization.dtos.LogInRequestDto;
import com.example.authorization.dtos.SignUpRequestDto;
import com.example.authorization.exceptions.InvalidTokenException;
import com.example.authorization.exceptions.InvalidUserException;
import com.example.authorization.exceptions.MultipleLoginException;
import com.example.authorization.models.Token;
import com.example.authorization.models.User;
import com.example.authorization.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto requestDto){
        return userService.signUp(requestDto.getEmail(),
                requestDto.getPassword(), requestDto.getName());
    }

    @PostMapping("/login")
    public Token logIn(@RequestBody LogInRequestDto requestDto){
        try {
            return  userService.logIn(requestDto.getEmail(), requestDto.getPassword());
        } catch (InvalidUserException e) {
            throw new RuntimeException("Invalid email or Password Exception");
        } catch (MultipleLoginException e) {
            throw new RuntimeException("Multiple user login Exception");
        }
    }

    @PostMapping("/logout/{token}")
    public void logOut(@PathVariable("token") String token){
        try {
            userService.logOut(token);
        } catch (InvalidTokenException e) {
            throw new RuntimeException("Invalid Token Exception");
        }
    }

    @GetMapping("/user/{name}")
    public String getDetails(@PathVariable("name") String name){
        return "Mahendra";
    }

    @PostMapping("/validatetoken/{token}")
    public boolean validateToken(@PathVariable("token") String token){
        return userService.validateToken(token);
    }
}

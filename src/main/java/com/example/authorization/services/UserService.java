package com.example.authorization.services;

import com.example.authorization.exceptions.InvalidTokenException;
import com.example.authorization.exceptions.InvalidUserException;
import com.example.authorization.exceptions.MultipleLoginException;
import com.example.authorization.models.Token;
import com.example.authorization.models.User;
import com.example.authorization.repositories.TokenRepository;
import com.example.authorization.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User signUp(String email, String password, String name){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Token logIn(String email, String password) throws InvalidUserException, MultipleLoginException {
        Optional<User> opt_user = userRepository.findByEmail(email);
        if(opt_user.isEmpty()){
            throw new InvalidUserException();
        }
        User user = opt_user.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new InvalidUserException();
        }
//        Check if User is already Logged in and throw exception if already logged in
//        this avoids multiple logins by same user

//        Optional<Token> opt_logged_user = tokenRepository.findByUserAndDeletedEqualsAndExpiryAtGreaterThan(
//                user.getId(), false, new Date());
//        if(!opt_logged_user.isEmpty()){
//            throw new MultipleLoginException();
//        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        add(Calendar.DAY_OF_MONTH, -5)
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        token.setExpiryAt(calendar.getTime());
        token.setDeleted(false);

        return tokenRepository.save(token);
    }

    public void logOut(String token) throws InvalidTokenException {
        Optional<Token> opt_token = tokenRepository.findByValueAndDeletedEquals(token, false);

        if(opt_token.isEmpty()){
            throw new InvalidTokenException();
        }

        Token token1 = opt_token.get();
        token1.setDeleted(true);

        tokenRepository.save(token1);
    }

    public boolean validateToken(String token) {
        Optional<Token> opt_token = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan
                (token, false, new Date());

        if(opt_token.isEmpty()){
            return false;
        }
        return true;
    }
}

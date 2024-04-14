package com.example.authorization.repositories;

import com.example.authorization.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {


    Optional<Token> findByValueAndDeletedEquals(String value, boolean isDeleted);

    Optional<Token> findByValueAndDeletedEqualsAndExpiryAtGreaterThan(String token, boolean b, Date date);

//    Optional<Token> findByUserAndDeletedEqualsAndExpiryAtGreaterThan(Long id, boolean deleted, Date date);
}

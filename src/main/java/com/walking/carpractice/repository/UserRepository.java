package com.walking.carpractice.repository;

import com.walking.carpractice.domain.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class UserRepository {
    @SuppressWarnings("unchecked")
    public Optional<User> findByUsername(String username, EntityManager em) {
        return em.createNativeQuery("select * from \"user\" where username = :username", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }
}

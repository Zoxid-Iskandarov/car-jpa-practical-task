package com.walking.carpractice.repository;

import com.walking.carpractice.domain.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class UserRepository {
    public User findById(Long id, EntityManager em) {
        return em.createQuery("select u from User u left join fetch u.cars where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<User> findByUsername(String username, EntityManager em) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }
}

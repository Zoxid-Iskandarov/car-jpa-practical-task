package com.walking.carpractice.repository;

import com.walking.carpractice.domain.Car;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CarRepository {
    public Car findById(Long id, EntityManager em) {
        return em.createQuery("select c from Car c left join fetch c.owners where c.id = :id", Car.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Car> findAllByIds(List<Long> ids, EntityManager em) {
        return em.createQuery("select c from Car c where c.id in (:ids)", Car.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public List<Car> findAllByUserId(Long userId, EntityManager em) {
        return em.createQuery("select c from Car c join fetch c.owners o where o.id = :userId", Car.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void updateSetTechnicalInspectionFalseByYearLassThen(int year, EntityManager em) {
        em.createQuery("update Car c set c.actualTechnicalInspection = false where c.year < :year")
                .setParameter("year", year)
                .executeUpdate();
    }
}

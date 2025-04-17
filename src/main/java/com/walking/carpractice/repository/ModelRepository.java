package com.walking.carpractice.repository;

import com.walking.carpractice.domain.Model;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ModelRepository {
    @SuppressWarnings("unchecked")
    public List<Model> findAllByBrandId(Long brandId, EntityManager em) {
        return em.createNativeQuery("select * from model where brand_id = :brandId", Model.class)
                .setParameter("brandId", brandId)
                .getResultList();
    }
}

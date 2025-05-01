package com.walking.carpractice.repository;

import com.walking.carpractice.domain.Model;
import com.walking.carpractice.model.projection.BrandStatistics;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ModelRepository {
    public List<Model> findAllByBrandId(Long brandId, EntityManager em) {
        return em.createQuery("select m from Model m where m.brandId = :brandId", Model.class)
                .setParameter("brandId", brandId)
                .getResultList();
    }

    public List<BrandStatistics> countGropingByBrandId(EntityManager em) {
        var jpql = """
                select new com.walking.carpractice.model.projection.BrandStatistics(m.brandId, count(m)) 
                from Model m
                group by m.brandId
                """;

        return em.createQuery(jpql, BrandStatistics.class)
                .getResultList();
    }
}

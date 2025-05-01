package com.walking.carpractice.service;

import com.walking.carpractice.domain.Brand;
import com.walking.carpractice.model.projection.BrandStatistics;
import com.walking.carpractice.repository.ModelRepository;

import java.util.List;

public class BrandService {
    private final EntityManagerHelper entityManagerHelper;
    private final ModelRepository modelRepository;

    public BrandService(EntityManagerHelper entityManagerHelper, ModelRepository modelRepository) {
        this.entityManagerHelper = entityManagerHelper;
        this.modelRepository = modelRepository;
    }

    public Brand getById(Long id) {
        return entityManagerHelper.runTransaction(em -> em.find(Brand.class, id));
    }

    public Brand create(Brand brand) {
        return entityManagerHelper.runTransaction(em -> {
            em.persist(brand);

            return brand;
        });
    }

    public Brand update(Brand brand) {
        return entityManagerHelper.runTransaction(em -> {
            var old = em.find(Brand.class, brand.getId());

            old.setName(brand.getName());

            return old;
        });
    }

    public void delete(Long id) {
        entityManagerHelper.runTransactionalNoResult(em -> {
            var brand = em.find(Brand.class, id);
            em.remove(brand);
        });
    }

    public List<BrandStatistics> getStatistics() {
        return entityManagerHelper.runTransaction(modelRepository::countGropingByBrandId);
    }
}

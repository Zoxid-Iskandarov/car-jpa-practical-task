package com.walking.carpractice.service;

import com.walking.carpractice.domain.Brand;

public class BrandService {
    private final EntityManagerHelper entityManagerHelper;

    public BrandService(EntityManagerHelper entityManagerHelper) {
        this.entityManagerHelper = entityManagerHelper;
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
}

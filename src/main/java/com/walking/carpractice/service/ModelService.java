package com.walking.carpractice.service;

import com.walking.carpractice.domain.Brand;
import com.walking.carpractice.domain.Model;
import com.walking.carpractice.repository.ModelRepository;

import java.util.List;

public class ModelService {
    private final EntityManagerHelper entityManagerHelper;
    private final ModelRepository modelRepository;

    public ModelService(EntityManagerHelper entityManagerHelper, ModelRepository modelRepository) {
        this.entityManagerHelper = entityManagerHelper;
        this.modelRepository = modelRepository;
    }

    public Model getById(Long id) {
        return entityManagerHelper.runTransaction(em -> em.find(Model.class, id));
    }

    public List<Model> getAllByBrandId(Long brandId) {
        return entityManagerHelper.runTransaction(em -> modelRepository.findAllByBrandId(brandId, em));
    }

    public Model create(Model model) {
        return entityManagerHelper.runTransaction(em -> {
            var brand = em.find(Brand.class, model.getBrandId());
            model.setBrand(brand);

            em.persist(model);

            return model;
        });
    }

    public Model update(Model model) {
        return entityManagerHelper.runTransaction(em -> {
            Model old = em.find(Model.class, model.getId());

            old.setName(model.getName());

            return old;
        });
    }

    public void delete(Long id) {
        entityManagerHelper.runTransactionalNoResult(em -> {
            var model = em.find(Model.class, id);
            em.remove(model);
        });
    }
}

package com.walking.carpractice.service;

import com.walking.carpractice.domain.Car;
import com.walking.carpractice.domain.Model;
import com.walking.carpractice.repository.CarRepository;

import java.util.List;

public class CarService {
    private final EntityManagerHelper entityManagerHelper;
    private final CarRepository carRepository;

    public CarService(EntityManagerHelper entityManagerHelper, CarRepository carRepository) {
        this.entityManagerHelper = entityManagerHelper;
        this.carRepository = carRepository;
    }

    public Car getById(Long id) {
        return entityManagerHelper.runTransaction(em -> carRepository.findById(id, em));
    }

    public List<Car> getAllByUser(Long userId) {
        return entityManagerHelper.runTransaction(em -> carRepository.findAllByUserId(userId, em));
    }

    public Car create(Car car) {
        return entityManagerHelper.runTransaction(em -> {
            var model = em.find(Model.class, car.getModelId());
            car.setModel(model);

            em.persist(car);

            return car;
        });
    }

    public Car update(Car updatedCar) {
        return entityManagerHelper.runTransaction(em -> {
            var oldCar = carRepository.findById(updatedCar.getId(), em);

            oldCar.setColor(updatedCar.getColor());
            oldCar.setNumber(updatedCar.getNumber());
            oldCar.setActualTechnicalInspection(updatedCar.getActualTechnicalInspection());

            return oldCar;
        });
    }

    public void delete(Long id) {
        entityManagerHelper.runTransactionalNoResult(em -> {
            var car = em.find(Car.class, id);
            em.remove(car);
        });
    }

    public void resetTechnicalInspectionByYear(int year) {
        entityManagerHelper.runTransactionalNoResult(em ->
                carRepository.updateSetTechnicalInspectionFalseByYearLassThen(year, em));
    }
}

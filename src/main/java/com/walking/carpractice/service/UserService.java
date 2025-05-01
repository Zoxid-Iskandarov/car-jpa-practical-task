package com.walking.carpractice.service;

import com.walking.carpractice.domain.Car;
import com.walking.carpractice.domain.User;
import com.walking.carpractice.exception.AuthException;
import com.walking.carpractice.exception.DuplicateUserException;
import com.walking.carpractice.repository.CarRepository;
import com.walking.carpractice.repository.UserRepository;

import java.util.List;

public class UserService {
    private final EntityManagerHelper entityManagerHelper;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final EncodingService encodingService;

    public UserService(EntityManagerHelper entityManagerHelper, UserRepository userRepository, CarRepository carRepository, EncodingService encodingService) {
        this.entityManagerHelper = entityManagerHelper;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.encodingService = encodingService;
    }

    public User getById(Long id) {
        return entityManagerHelper.runTransaction(em -> userRepository.findById(id, em));
    }

    public User auth(String username, String password) {
        User user = entityManagerHelper.runTransaction(em -> userRepository.findByUsername(username, em))
                .orElseThrow(AuthException::new);

        if (!encodingService.match(password, user.getPassword())) {
            throw new AuthException();
        }

        return user;
    }

    public User create(User user) {
        return entityManagerHelper.runTransaction(em -> {
            userRepository.findByUsername(user.getUsername(), em)
                    .ifPresent(u -> {
                        throw new DuplicateUserException();
                    });

            var encodedPassword = encodingService.encode(user.getPassword());
            user.setPassword(encodedPassword);

            em.persist(user);

            return user;
        });
    }

    public User update(User user, List<Long> carsIds) {
        return entityManagerHelper.runTransaction(em -> {
            var oldUser = userRepository.findById(user.getId(), em);

            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());

            List<Car> cars = carsIds.isEmpty()
                    ? List.of()
                    : carRepository.findAllByIds(carsIds, em);

            oldUser.getCars().clear();
            oldUser.getCars().addAll(cars);

            return oldUser;
        });
    }
}

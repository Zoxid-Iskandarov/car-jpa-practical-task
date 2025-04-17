package com.walking.carpractice.service;

import com.walking.carpractice.exception.CommonAppException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class EntityManagerHelper {
    private final EntityManagerFactory entityManagerFactory;

    public EntityManagerHelper(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void runTransactionalNoResult(Consumer<EntityManager> task) {
        Function<EntityManager, Object> noResultTask = em -> {
            task.accept(em);
            return null;
        };

        runTransaction(noResultTask);
    }

    public <T> T runTransaction(Function<EntityManager, T> task) {
        try (var em = getEntityManager()) {
            try {
                var transaction = em.getTransaction();
                transaction.begin();

                T result = task.apply(em);

                transaction.commit();

                return result;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }

            throw new CommonAppException("Ошибка при обработке транзакции", e);
        }
    }

    public synchronized EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}

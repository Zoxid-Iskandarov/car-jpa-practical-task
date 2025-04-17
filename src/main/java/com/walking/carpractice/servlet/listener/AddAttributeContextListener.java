package com.walking.carpractice.servlet.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.brand.BrandConverter;
import com.walking.carpractice.converter.brand.CreateBrandRequestConverter;
import com.walking.carpractice.converter.brand.UpdateBrandRequestConverter;
import com.walking.carpractice.converter.car.CarConverter;
import com.walking.carpractice.converter.car.CreateCarRequestConverter;
import com.walking.carpractice.converter.car.UpdateCarRequestConverter;
import com.walking.carpractice.converter.model.CreateModelRequestConverter;
import com.walking.carpractice.converter.model.ModelConverter;
import com.walking.carpractice.converter.model.UpdateModelRequestConverter;
import com.walking.carpractice.converter.user.CreateUserRequestConverter;
import com.walking.carpractice.converter.user.UpdateUserRequestConverter;
import com.walking.carpractice.converter.user.UserConverter;
import com.walking.carpractice.repository.CarRepository;
import com.walking.carpractice.repository.ModelRepository;
import com.walking.carpractice.repository.UserRepository;
import com.walking.carpractice.service.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;

public class AddAttributeContextListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(AddAttributeContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("Запущена инициализация атрибутов глобального контекста");

        var servletContext = event.getServletContext();

        var carConverter = new CarConverter();
        servletContext.setAttribute(ContextAttributeNames.CAR_CONVERTER, carConverter);

        var createCarRequestConverter = new CreateCarRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_CAR_REQUEST_CONVERTER, createCarRequestConverter);

        var updateCarRequestConverter = new UpdateCarRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_CAR_REQUEST_CONVERTER, updateCarRequestConverter);

        var userConverter = new UserConverter();
        servletContext.setAttribute(ContextAttributeNames.USER_CONVERTER, userConverter);

        var createUserRequestConverter = new CreateUserRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_USER_REQUEST_CONVERTER, createUserRequestConverter);

        var updateUserRequestConverter = new UpdateUserRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_USER_REQUEST_CONVERTER, updateUserRequestConverter);

        var brandConverter = new BrandConverter();
        servletContext.setAttribute(ContextAttributeNames.BRAND_CONVERTER, brandConverter);

        var createBrandRequestConverter = new CreateBrandRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_BRAND_REQUEST_CONVERTER, createBrandRequestConverter);

        var updateBrandRequestConverter = new UpdateBrandRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_BRAND_REQUEST_CONVERTER, updateBrandRequestConverter);

        var modelConverter = new ModelConverter();
        servletContext.setAttribute(ContextAttributeNames.MODEL_CONVERTER, modelConverter);

        var createModelRequestConverter = new CreateModelRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.CREATE_MODEL_REQUEST_CONVERTER, createModelRequestConverter);

        var updateModelRequestConverter = new UpdateModelRequestConverter();
        servletContext.setAttribute(ContextAttributeNames.UPDATE_MODEL_REQUEST_CONVERTER, updateModelRequestConverter);

        var carRepository = new CarRepository();
        servletContext.setAttribute(ContextAttributeNames.CAR_REPOSITORY, carRepository);

        var userRepository = new UserRepository();
        servletContext.setAttribute(ContextAttributeNames.USER_REPOSITORY, userRepository);

        var modelRepository = new ModelRepository();
        servletContext.setAttribute(ContextAttributeNames.MODEL_REPOSITORY, modelRepository);

        var entityManagerFactory = Persistence.createEntityManagerFactory("Hibernate");
        servletContext.setAttribute(ContextAttributeNames.ENTITY_MANAGER_FACTORY, entityManagerFactory);

        var entityManagerHelper = new EntityManagerHelper(entityManagerFactory);
        servletContext.setAttribute(ContextAttributeNames.ENTITY_MANAGER_HELPER, entityManagerHelper);

        var carService = new CarService(entityManagerHelper, carRepository);
        servletContext.setAttribute(ContextAttributeNames.CAR_SERVICE, carService);

        var encodingService = new EncodingService();
        servletContext.setAttribute(ContextAttributeNames.ENCODING_SERVICE, encodingService);

        var userService = new UserService(entityManagerHelper, userRepository, carRepository, encodingService);
        servletContext.setAttribute(ContextAttributeNames.USER_SERVICE, userService);

        var brandService = new BrandService(entityManagerHelper);
        servletContext.setAttribute(ContextAttributeNames.BRAND_SERVICE, brandService);

        var modelService = new ModelService(entityManagerHelper, modelRepository);
        servletContext.setAttribute(ContextAttributeNames.MODEL_SERVICE, modelService);

        var dataSource = getDataSource(entityManagerFactory);
        var migrationService = new MigrationService(dataSource);
        servletContext.setAttribute(ContextAttributeNames.MIGRATION_SERVICE, migrationService);

        var objectMapper = getObjectMapper();
        servletContext.setAttribute(ContextAttributeNames.OBJECT_MAPPER, objectMapper);

        log.info("Завершена инициализация атрибутов глобального контекста");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        var entityManagerFactory = (EntityManagerFactory) sce.getServletContext()
                .getAttribute(ContextAttributeNames.ENTITY_MANAGER_FACTORY);

        entityManagerFactory.close();
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        return objectMapper;
    }

    private DataSource getDataSource(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactoryServiceRegistry.class)
                .getService(ConnectionProvider.class)
                .unwrap(DataSource.class);
    }
}

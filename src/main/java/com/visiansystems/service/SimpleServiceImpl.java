package com.visiansystems.service;

import com.visiansystems.model.SimpleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * The GreetingServiceBean encapsulates all business behaviors operating on the Greeting entity model.
 */
@Service
public class SimpleServiceImpl implements SimpleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The <code>CounterService</code> captures metrics for Spring Actuator.
     */
    @Autowired
    private CounterService counterService;

    /**
     * The Spring Data repository for Greeting entities.
     */
    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<SimpleObject> findAll() {
        logger.info("> findAll");

        counterService.increment("method.invoked.greetingServiceBean.findAll");

        Collection<SimpleObject> greetings = greetingRepository.findAll();

        logger.info("< findAll");
        return greetings;
    }

    @Cacheable(
            value = "greetings",
            key = "#id")
    @Override
    public SimpleObject findOne(Long id) {
        logger.info("> findOne {}", id);

        counterService.increment("method.invoked.greetingServiceBean.findOne");

        SimpleObject greeting = greetingRepository.findOne(id);

        logger.info("< findOne {}", id);
        return greeting;
    }

    @CachePut(
            value = "greetings",
            key = "#result.id")
    @Transactional
    @Override
    public SimpleObject create(SimpleObject greeting) {
        logger.info("> create");

        counterService.increment("method.invoked.greetingServiceBean.create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            logger.error("Attempted to create a Greeting, but id attribute was not null.");
            logger.info("< create");
            throw new EntityExistsException(
                    "Cannot create new Greeting with supplied id.  The id attribute must be null to create an entity.");
        }

        SimpleObject savedGreeting = greetingRepository.save(greeting);

        logger.info("< create");
        return savedGreeting;
    }

    @CachePut(
            value = "greetings",
            key = "#greeting.id")
    @Transactional
    @Override
    public SimpleObject update(SimpleObject greeting) {
        logger.info("> update {}", greeting.getId());

        counterService.increment("method.invoked.greetingServiceBean.update");

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        SimpleObject greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            logger.error("Attempted to update a Greeting, but the entity does not exist.");
            logger.info("< update {}", greeting.getId());
            throw new NoResultException("Requested Greeting not found.");
        }

        Greeting updatedGreeting = greetingRepository.save(greeting);

        logger.info("< update {}", greeting.getId());
        return updatedGreeting;
    }

    @CacheEvict(
            value = "greetings",
            key = "#id")
    @Transactional
    @Override
    public void delete(Long id) {
        logger.info("> delete {}", id);

        counterService.increment("method.invoked.greetingServiceBean.delete");

        greetingRepository.delete(id);

        logger.info("< delete {}", id);
    }

    @CacheEvict(
            value = "greetings",
            allEntries = true)
    @Override
    public void evictCache() {
        logger.info("> evictCache");

        counterService
                .increment("method.invoked.greetingServiceBean.evictCache");

        logger.info("< evictCache");
    }

}

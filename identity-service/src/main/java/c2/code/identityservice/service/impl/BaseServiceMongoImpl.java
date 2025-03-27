package c2.code.identityservice.service.impl;

import c2.code.identityservice.enums.ErrorCodeEnum;
import c2.code.identityservice.exceptions.AppException;
import c2.code.identityservice.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BaseServiceMongoImpl<T, ID extends Serializable, R extends MongoRepository<T, ID>> implements IBaseService<T, ID> {

    protected R repository;

    public BaseServiceMongoImpl(R repo) {
        this.repository = repo;
    }

    @Override
    public Optional<T> findById(ID id) {
        log.info("MongoDB - Finding entity by ID: {}", id);
        Optional<T> entity = repository.findById(id);
        if (entity.isEmpty()) {
            log.error("MongoDB - Entity not found for ID: {}", id);
            throw new AppException(ErrorCodeEnum.DATA_NOT_FOUND, "Entity not found for ID: " + id);
        }
        return entity;
    }

    @Override
    public List<T> findAll() {
        try {
            log.info("MongoDB - Fetching all entities");
            return repository.findAll();
        } catch (Exception e) {
            log.error("MongoDB - Error fetching all entities: {}", e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.DATA_NOT_FOUND, "No entities found");
        }
    }

    @Override
    public T save(T entity) {
        try {
            log.info("MongoDB - Saving entity: {}", entity);
            return repository.save(entity);
        } catch (DataIntegrityViolationException  e) {
            log.error("MongoDB - Duplicate key error: Entity: {}, Message: {}",
                    entity.toString(), e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.DUPLICATE_DATA, "Entity already exists");
//        } catch (DuplicateKeyException e) {
//            log.error("MongoDB - Duplicate key error: Entity: {}, Message: {}",
//                    entity.toString(), e.getMessage(), e);
//            throw new AppException(ErrorCodeEnum.DUPLICATE_DATA, "Entity already exists");
        } catch (Exception e) {
            log.error("MongoDB - Error saving entity: {}, Message: {}", entity, e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.INVALID_KEY, "Internal server error");
        }
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        try {
            log.info("MongoDB - Saving multiple entities");
            return new ArrayList<>(repository.saveAll(entities));
        } catch (DuplicateKeyException e) {
            log.error("MongoDB - Duplicate key error while saving multiple entities: {}", e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.DUPLICATE_DATA, "Some entities already exist");
        } catch (Exception e) {
            log.error("MongoDB - Error saving multiple entities: {}", e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.INVALID_KEY, "Internal server error");
        }
    }

    @Override
    public T update(T updatedEntity) {
        try {
            log.info("MongoDB - Updating entity: {}", updatedEntity);
            return repository.save(updatedEntity);
        } catch (DuplicateKeyException e) {
            log.error("MongoDB - Duplicate key error while updating entity: {}", e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.DUPLICATE_DATA, "Entity already exists");
        } catch (Exception e) {
            log.error("MongoDB - Error updating entity: {}", e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.INVALID_KEY, "Internal server error");
        }
    }

    @Override
    public void deleteById(ID id) {
        log.info("MongoDB - Deleting entity with ID: {}", id);
        Optional<T> entity = repository.findById(id);
        if (entity.isEmpty()) {
            log.error("MongoDB - Entity not found for ID: {}", id);
            throw new AppException(ErrorCodeEnum.DATA_NOT_FOUND, "Entity not found for ID: " + id);
        }
        try {
            repository.deleteById(id);
            log.info("MongoDB - Entity with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("MongoDB - Error deleting entity with ID {}: {}", id, e.getMessage(), e);
            throw new AppException(ErrorCodeEnum.DATABASE_SAVE_ERROR, "Error deleting entity");
        }
    }
}

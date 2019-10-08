package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * AbstractService for implement default service operations for a specific type.
 *
 * @param <T> the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 */
@Slf4j
public abstract class AbstractService<T, ID> {
    private JpaRepository<T, ID> repository;
    private ModelMapper modelMapper;
    private Class<T> entityClass;

    @Autowired
    public void setRepository(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void init() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void delete(ID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(id);
        }

    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> notFindException(id));
    }

    public Optional<T> findByIdOptional(ID id) {
        return repository.findById(id);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public T merge(ID id, Object dto){
        T entity;
        Optional<T> optionalCalendar = findByIdOptional(id);

        if (optionalCalendar.isPresent()) {
            entity = optionalCalendar.get();
            modelMapper.map(dto, entity);
        } else {
            entity = modelMapper.map(dto, entityClass);
            save(entity);
        }

        return entity;
    }
    /**
     * @throws ServiceException if entity not exists.
     */
    void checkExists(ID id) {
        repository.findById(id).orElseThrow(() -> notFindException(id));
    }

    protected ServiceException notFindException(ID id){
        return new ServiceException(notFindMessage(id));
    }

    protected abstract String notFindMessage(ID id);

}

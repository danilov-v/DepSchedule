package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

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

    @Autowired
    public void setRepository(JpaRepository<T, ID> repository) {
        this.repository = repository;
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

    //<editor-fold desc="Метод merge() (Пока сложно реализовать в абстрактном классе)">
    //    public T merge(ID id, Object dto){
//        T entity;
//        Optional<T> optionalCalendar = findByIdOptional(id);
//
//        if (optionalCalendar.isPresent()) {
//            entity = optionalCalendar.get();
//            modelMapper.map(dto, entity);
//        } else {
//            entity = modelMapper.map(dto, entityClass);
//            entity.set
//            save(entity);
//        }
//
//        return entity;
//    }
    //</editor-fold>

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

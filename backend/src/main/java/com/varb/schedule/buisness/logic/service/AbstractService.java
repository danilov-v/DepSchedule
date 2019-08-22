package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public abstract class AbstractService<T, ID> {
    private final JpaRepository<T, ID> repository;
    private final ModelMapperCustomize modelMapper;

    public void delete(ID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(id);
        }

    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public Optional<T> get(ID id) {
        return repository.findById(id);
    }

    T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> notFindException(id));
    }

    Optional<T> findByIdOptional(ID id) {
        return repository.findById(id);
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

    abstract String notFindMessage(ID id);

}

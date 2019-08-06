package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractService<E, ID> {
    private final JpaRepository<E, ID> repository;
    private final ModelMapperCustomize modelMapper;

    public void delete(ID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(id);
        }

    }

    public List<E> getAll() {
        return repository.findAll();
    }

    public Optional<E> get(ID id) {
        return repository.findById(id);
    }

    E findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> notFindException(id));
    }

    Optional<E> findByIdOptional(ID id) {
        return repository.findById(id);
    }


    /**
     * @throws ServiceException if entity not exists.
     */
    void checkExists(ID id) {
        repository.findById(id).orElseThrow(() -> notFindException(id));
    }

    abstract ServiceException notFindException(ID id);

}

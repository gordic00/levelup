package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.TypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends CrudRepository<TypeEntity, Integer> {
    boolean existsById(Integer id);

    List<TypeEntity> findAll();
}

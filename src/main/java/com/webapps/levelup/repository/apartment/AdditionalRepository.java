package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.AdditionalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalRepository extends CrudRepository<AdditionalEntity, Integer> {
    boolean existsById(Integer id);

    List<AdditionalEntity> findAll();
}

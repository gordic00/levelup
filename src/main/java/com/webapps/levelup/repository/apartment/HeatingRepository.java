package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.HeatingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeatingRepository extends CrudRepository<HeatingEntity, Integer> {
    List<HeatingEntity> findAll();
}

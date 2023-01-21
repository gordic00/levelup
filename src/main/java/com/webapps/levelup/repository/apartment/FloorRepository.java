package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.FloorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends CrudRepository<FloorEntity, Integer> {
    List<FloorEntity> findAll();
}

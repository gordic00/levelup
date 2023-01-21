package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.ConstructionTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionTypeRepository extends CrudRepository<ConstructionTypeEntity, Integer> {
    List<ConstructionTypeEntity> findAll();
}

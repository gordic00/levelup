package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.StructureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructureRepository extends CrudRepository<StructureEntity, Integer> {
    List<StructureEntity> findAll();
}

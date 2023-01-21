package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.IncludedEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncludedRepository extends CrudRepository<IncludedEntity, Integer> {
    List<IncludedEntity> findAll();
}

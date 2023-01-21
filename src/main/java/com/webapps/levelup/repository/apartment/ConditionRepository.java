package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.ConditionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends CrudRepository<ConditionEntity, Integer> {
    List<ConditionEntity> findAll();
}

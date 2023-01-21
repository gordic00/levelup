package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.FurnishedEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnishedRepository extends CrudRepository<FurnishedEntity, Integer> {
    List<FurnishedEntity> findAll();
}

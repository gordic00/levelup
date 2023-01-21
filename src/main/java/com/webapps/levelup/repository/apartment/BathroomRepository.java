package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.BathroomsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BathroomRepository extends CrudRepository<BathroomsEntity, Integer> {
    List<BathroomsEntity> findAll();
}

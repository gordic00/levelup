package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.AdvertiserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiserRepository extends CrudRepository<AdvertiserEntity, Integer> {
    List<AdvertiserEntity> findAll();
}

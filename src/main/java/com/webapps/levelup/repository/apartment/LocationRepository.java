package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Integer> {
    boolean existsById(Integer id);

    List<LocationEntity> findByFullNameIsContainingIgnoreCaseOrLocationCodeIsContainingIgnoreCase(String name, String code);
}

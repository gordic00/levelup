package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.ApartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Integer> {
    @Transactional
    void deleteById(Integer id);
}

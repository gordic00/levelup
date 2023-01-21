package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartement_images.ApartmentImagesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApartmentImageRepository extends CrudRepository<ApartmentImagesEntity, Integer> {

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void deleteAllByApartmentId(Integer apartmentId);
}

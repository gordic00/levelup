package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartement_images.ApartmentImagesResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentImageResponseRepository extends CrudRepository<ApartmentImagesResponse, Integer> {
    List<ApartmentImagesResponse> findAll();

    List<ApartmentImagesResponse> findAllByApartmentId(Integer apartmentId);

    Optional<ApartmentImagesResponse> findById(Integer id);
}

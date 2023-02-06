package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.ApartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentResponseRepository extends CrudRepository<ApartmentResponse, Integer> {

    List<ApartmentResponse> findByOrderByIdDesc();

    boolean existsById(Integer id);

    Optional<ApartmentResponse> findById(Integer id);

    Page<ApartmentResponse> findAll(Pageable pageable);

    Page<ApartmentResponse> findByTypeEntity_IdAndAdCodeContainsIgnoreCase(Integer typeCodeId, String adCode, Pageable pageable);

    List<ApartmentResponse> findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdIn
            (List<Integer> typeIds, List<Integer> structureIds,
             List<Integer> constructionTypeIds);

    List<ApartmentResponse> findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdInAndAdCodeContainsIgnoreCase
            (List<Integer> typeIds, List<Integer> structureIds,
             List<Integer> constructionTypeIds, String adCode);

    List<ApartmentResponse> findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdInAndLocationEntity_LocationCodeContainsIgnoreCase
            (List<Integer> typeIds, List<Integer> structureIds,
             List<Integer> constructionTypeIds, String location);

    List<ApartmentResponse> findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdInAndLocationEntity_LocationCodeContainsIgnoreCaseAndAdCodeContainsIgnoreCase
            (List<Integer> typeIds, List<Integer> structureIds,
             List<Integer> constructionTypeIds, String location, String adCode);
}

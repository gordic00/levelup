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

    Page<ApartmentResponse> findDistinctByTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
            (List<Integer> typeIds, List<Integer> structureIds, List<Integer> floorIds, List<Integer> furnishedIds, List<Integer> heatingIds,
             List<Integer> constructionTypeIds, List<Integer> includedIds, Long from, Long to, Pageable pageable);

    Page<ApartmentResponse> findDistinctByAdCodeContainsAndTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
            (String adCode, List<Integer> typeIds, List<Integer> structureIds, List<Integer> floorIds, List<Integer> furnishedIds, List<Integer> heatingIds,
             List<Integer> constructionTypeIds, List<Integer> includedIds, Long from, Long to, Pageable pageable);

    List<ApartmentResponse> findDistinctByTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
            (List<Integer> typeIds, List<Integer> structureIds, List<Integer> floorIds, List<Integer> furnishedIds, List<Integer> heatingIds,
             List<Integer> constructionTypeIds, List<Integer> includedIds, Long from, Long to);

    List<ApartmentResponse> findDistinctByAdCodeContainsAndTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
            (String adCode, List<Integer> typeIds, List<Integer> structureIds, List<Integer> floorIds, List<Integer> furnishedIds, List<Integer> heatingIds,
             List<Integer> constructionTypeIds, List<Integer> includedIds, Long from, Long to);
}

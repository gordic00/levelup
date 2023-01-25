package com.webapps.levelup.service.apartment;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.webapps.levelup.configuration.ModelMapperConfig;
import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.helper.DateHelper;
import com.webapps.levelup.helper.TokenHelper;
import com.webapps.levelup.model.apartment.*;
import com.webapps.levelup.model.apartment_xml.RealEstate;
import com.webapps.levelup.model.apartment_xml.LevelUpRealEstates;
import com.webapps.levelup.model.dto.*;
import com.webapps.levelup.repository.apartment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentService {
    private final ApartmentRepository repo;
    private final ApartmentResponseRepository repoResponse;
    private final TypeRepository repoType;
    private final AdditionalRepository additionalRepo;
    private final ConditionRepository conditionRepository;
    private final AdvertiserRepository advertiserRepository;
    private final BathroomRepository bathroomRepository;
    private final FloorRepository floorRepository;
    private final FurnishedRepository furnishedRepository;
    private final HeatingRepository heatingRepository;
    private final IncludedRepository includedRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final StructureRepository structureRepository;
    private final ConstructionTypeRepository constructionTypeRepository;
    private final ApartmentImagesService apartmentImagesService;
    private final ModelMapperConfig mmc;
    private final TokenHelper tokenHelper;

    /*
    Creates new apartment.
     */
    @Transactional
    public ResponseEntity<ApartmentResponse> create(HttpServletRequest request, ApartmentCreateDto model) {
        if (!repoType.existsById(model.getTypeId())) {
            throw new CustomException("Apartment Type with given ID does not exists.");
        }
        ApartmentEntity newApartment = prepareEntityForDb(model);
        try {
            newApartment.setCreatedBy(tokenHelper.getEmailFromToken(request.getHeader("jwt-token")));
            newApartment.setCreatedDate(Calendar.getInstance().getTime());
            ApartmentEntity save = repo.save(newApartment);

            Optional<ApartmentResponse> result = repoResponse.findById(save.getId());

            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /*
    Map Entity without ID.
     */
    private ApartmentEntity prepareEntityForDb(ApartmentCreateDto model) {
        ModelMapper map = new ModelMapper();
        map.addMappings(new PropertyMap<ApartmentCreateDto, ApartmentEntity>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
        ApartmentEntity newApartment = map.map(model, ApartmentEntity.class);
        newApartment.setAdCode(nextAdCode());

        prepareParams(newApartment, model.getAdditional(), model.getHeating(), model.getIncluded());

        return newApartment;
    }

    /*
    Return next ad code
     */
    private String nextAdCode() {
        List<ApartmentResponse> lastApartment = repoResponse.findByOrderByIdDesc();

        String adCode = "7512";
        return lastApartment.isEmpty() ? adCode + 1 : adCode + lastApartment.get(0).getId();
    }

    /*
    Populate Lists from IDs.
     */
    private void prepareParams(
            ApartmentEntity newApartment,
            List<Integer> additional, List<Integer> heating, List<Integer> included) {
        if (!additional.isEmpty()) {
            List<AdditionalEntity> add = additionalRepo.findAll();
            add.forEach(a -> additional.forEach(ma -> {
                if (a.getId().equals(ma)) {
                    newApartment.getAdditional().add(a);
                }
            }));
        }
        if (!heating.isEmpty()) {
            List<HeatingEntity> add = heatingRepository.findAll();
            add.forEach(a -> heating.forEach(ma -> {
                if (a.getId().equals(ma)) {
                    newApartment.getHeating().add(a);
                }
            }));
        }
        if (!included.isEmpty()) {
            List<IncludedEntity> add = includedRepository.findAll();
            add.forEach(a -> included.forEach(ma -> {
                if (a.getId().equals(ma)) {
                    newApartment.getIncluded().add(a);
                }
            }));
        }
    }

    /*
    Updates apartment.
     */
    @Transactional
    public ResponseEntity<HttpStatus> update(HttpServletRequest request, ApartmentUpdateDto model) {
        Optional<ApartmentResponse> apartment = repoResponse.findById(model.getId());
        if (apartment.isEmpty()) {
            throw new CustomException("There is no Apartment in data base with given ID.");
        }
        if (!repoType.existsById(model.getTypeId())) {
            throw new CustomException("Apartment Type with given ID does not exists.");
        }
        try {
            ApartmentEntity save = mmc.map(model, ApartmentEntity.class);
            prepareParams(save, model.getAdditional(), model.getHeating(), model.getIncluded());
            save.setLastModifiedBy(tokenHelper.getEmailFromToken(request.getHeader("jwt-token")));
            save.setLastModifiedDate(Calendar.getInstance().getTime());
            repo.save(save);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
    Read apartment by ID.
     */
    public ResponseEntity<ApartmentResponse> read(Integer apartmentId) {
        Optional<ApartmentResponse> response = repoResponse.findById(apartmentId);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response.get());
        }
    }

    /*
    Read all apartments.
     */
    public ResponseEntity<Page<ApartmentResponse>> readAll(ApartmentReadDto apartmentReadDto) {
        Pageable pageable = PageRequest.of(apartmentReadDto.getPage(), apartmentReadDto.getSize());
        Page<ApartmentResponse> result;
        List<ApartmentResponse> query;

        SearchParamsDto searchParams =
                prepareParamsForSearch(apartmentReadDto.getTypeIds(), apartmentReadDto.getStructureIds(),
                        apartmentReadDto.getFloorIds(), apartmentReadDto.getFurnishedIds(),
                        apartmentReadDto.getHeatingIds(), apartmentReadDto.getConstructionTypeIds(),
                        apartmentReadDto.getIncludedIds());

        System.out.println();
        if (apartmentReadDto.getLocation() == null) {
            if (apartmentReadDto.getAdCode() == null) {
                result = repoResponse.
                        findDistinctByTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
                                (
                                        searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getFloorIds(), searchParams.getFurnishedIds(),
                                        searchParams.getHeatingIds(), searchParams.getConstructionTypeIds(),
                                        searchParams.getIncludedIds(), apartmentReadDto.getPriceFrom(), apartmentReadDto.getPriceTo(), pageable
                                );
            } else {
                result = repoResponse.
                        findDistinctByAdCodeContainsAndTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
                                (
                                        apartmentReadDto.getAdCode(), searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getFloorIds(), searchParams.getFurnishedIds(),
                                        searchParams.getHeatingIds(), searchParams.getConstructionTypeIds(),
                                        searchParams.getIncludedIds(), apartmentReadDto.getPriceFrom(), apartmentReadDto.getPriceTo(), pageable
                                );
            }
        } else {
            if (apartmentReadDto.getAdCode() == null) {
                query = repoResponse.
                        findDistinctByTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
                                (
                                        searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getFloorIds(), searchParams.getFurnishedIds(),
                                        searchParams.getHeatingIds(), searchParams.getConstructionTypeIds(),
                                        searchParams.getIncludedIds(), apartmentReadDto.getPriceFrom(), apartmentReadDto.getPriceTo()
                                );
                query.removeIf(p ->
                        !p.getCity().toLowerCase().contains(apartmentReadDto.getLocation().toLowerCase()) &&
                                !p.getMunicipality().toLowerCase().contains(apartmentReadDto.getLocation().toLowerCase()) &&
                                !p.getAddress().toLowerCase().contains(apartmentReadDto.getLocation().toLowerCase()));
            } else {
                query = repoResponse.
                        findDistinctByAdCodeContainsAndTypeEntityIdInAndStructureIdInAndFloorEntity_IdInAndFurnished_IdInAndHeating_IdInAndConstructionType_IdInAndIncluded_IdInAndMonthlyUtilitiesBetween
                                (
                                        apartmentReadDto.getAdCode(), searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getFloorIds(), searchParams.getFurnishedIds(),
                                        searchParams.getHeatingIds(), searchParams.getConstructionTypeIds(),
                                        searchParams.getIncludedIds(), apartmentReadDto.getPriceFrom(), apartmentReadDto.getPriceTo()
                                );
                query.removeIf(p ->
                        !p.getCity().toLowerCase().contains(apartmentReadDto.getLocation().toLowerCase()) &&
                                !p.getMunicipality().toLowerCase().contains(apartmentReadDto.getLocation().toLowerCase()) &&
                                !p.getAddress().toLowerCase().contains(apartmentReadDto.getLocation().toLowerCase()));
            }
            result = new PageImpl<>(query, pageable, query.size());
        }

        return result == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    /*
    Populate parameters for search.
     */
    private SearchParamsDto prepareParamsForSearch(
            List<Integer> typeIds, List<Integer> structureIds, List<Integer> floorIds,
            List<Integer> furnishedIds, List<Integer> heatingIds,
            List<Integer> constructionTypeIds, List<Integer> includedIds) {
        SearchParamsDto searchParamsDto = new SearchParamsDto();
        //Populate lists of params.
        ListsDto allLists = readAllLists().getBody();
        assert allLists != null;
        //Populate list of types.
        List<TypeEntity> typeEntities = repoType.findAll();
        //Type
        if (typeIds == null || typeIds.isEmpty()) {
            typeIds = new ArrayList<>();
            for (TypeEntity var : typeEntities) {
                typeIds.add(var.getId());
            }
        }
        searchParamsDto.setTypeIds(typeIds);
        //Structure
        if (structureIds == null || structureIds.isEmpty()) {
            structureIds = new ArrayList<>();
            for (StructureEntity var : allLists.getStructure()) {
                structureIds.add(var.getId());
            }
        }
        searchParamsDto.setStructureIds(structureIds);
        //Floors
        if (floorIds == null || floorIds.isEmpty()) {
            floorIds = new ArrayList<>();
            for (FloorEntity var : allLists.getFloor()) {
                floorIds.add(var.getId());
            }
        }
        searchParamsDto.setFloorIds(floorIds);
        //Furnished
        if (furnishedIds == null || furnishedIds.isEmpty()) {
            furnishedIds = new ArrayList<>();
            for (FurnishedEntity var : allLists.getFurnished()) {
                furnishedIds.add(var.getId());
            }
        }
        searchParamsDto.setFurnishedIds(furnishedIds);
        //Heating
        if (heatingIds == null || heatingIds.isEmpty()) {
            heatingIds = new ArrayList<>();
            for (HeatingEntity var : allLists.getHeating()) {
                heatingIds.add(var.getId());
            }
        }
        searchParamsDto.setHeatingIds(heatingIds);
        //Construction Types
        if (constructionTypeIds == null || constructionTypeIds.isEmpty()) {
            constructionTypeIds = new ArrayList<>();
            for (ConstructionTypeEntity var : allLists.getConstructionType()) {
                constructionTypeIds.add(var.getId());
            }
        }
        searchParamsDto.setConstructionTypeIds(constructionTypeIds);
        //Included
        if (includedIds == null || includedIds.isEmpty()) {
            includedIds = new ArrayList<>();
            for (IncludedEntity var : allLists.getIncluded()) {
                includedIds.add(var.getId());
            }
        }
        searchParamsDto.setIncludedIds(includedIds);
        return searchParamsDto;
    }

    /*
    Read all apartment types.
     */
    public ResponseEntity<List<TypeEntity>> readTypes() {
        List<TypeEntity> response = repoType.findAll();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    /*
    Read all params lists.
     */
    public ResponseEntity<ListsDto> readAllLists() {
        ListsDto response = new ListsDto();
        response.setAdditional(additionalRepo.findAll());
        response.setAdvertiser(advertiserRepository.findAll());
        response.setBathrooms(bathroomRepository.findAll());
        response.setConstructionType(constructionTypeRepository.findAll());
        response.setFloor(floorRepository.findAll());
        response.setFurnished(furnishedRepository.findAll());
        response.setHeating(heatingRepository.findAll());
        response.setIncluded(includedRepository.findAll());
        response.setPaymentType(paymentTypeRepository.findAll());
        response.setStructure(structureRepository.findAll());
        response.setCondition(conditionRepository.findAll());
        return ResponseEntity.ok(response);
    }

    /*
    Delete apartment by ID.
     */
    @Transactional
    public ResponseEntity<String> delete(Integer apartmentId) {
        if (repoResponse.existsById(apartmentId)) {
            try {
                apartmentImagesService.deleteAllByApartmentFromAws(apartmentId);
                repo.deleteById(apartmentId);
                return ResponseEntity.ok("Successfully deleted");
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        }
        return ResponseEntity.noContent().build();
    }

    /*
    Download XML file for Apartment by ID.
     */
    public void downloadXml(HttpServletResponse response) throws IOException {
//        Optional<ApartmentResponse> apartment = repoResponse.findById(apartmentId);
        List<ApartmentResponse> apartmentResponses = repoResponse.findByOrderByIdDesc();
        if (apartmentResponses.isEmpty()) {
            throw new CustomException("There are no apartments.");
        }
        String date = DateHelper.currentDate("MM-dd-YYYY");
        LevelUpRealEstates realEstates = new LevelUpRealEstates();
        for (ApartmentResponse apartment : apartmentResponses) {
            RealEstate realEstate = mapResponseXmlDto(apartment);
            realEstates.getRealEstate().add(realEstate);
        }
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File("apartments_" + date + ".xml"), realEstates);
        File file = new File("apartments_" + date + ".xml");
        assertNotNull(file);
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/xml";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        File local = new File("apartments_" + date + ".xml");
        if (local.exists()) {
            FileUtils.forceDelete(local);
        }
    }

    /*
    Prepare XML DTO.
     */
    private RealEstate mapResponseXmlDto(ApartmentResponse apartment) {
        RealEstate responseXml = mmc.map(apartment, RealEstate.class);
        responseXml.setBathrooms_code(apartment.getBathrooms().getCode());
        responseXml.setCondition_code(apartment.getConditionEntity2().getCode());
        responseXml.setFloor_code(apartment.getFloorEntity().getCode());
        responseXml.setNumberStoreys_code(apartment.getNumberStoreys().getCode());
        responseXml.setFurnished_code(apartment.getFurnished().getCode());
        responseXml.setStructure_code(apartment.getStructure().getCode());
        responseXml.setAdvertiser_code(apartment.getAdvertiser().getCode());
        responseXml.setConstructionType_code(apartment.getConstructionType().getCode());
        responseXml.setPaymentType_code(apartment.getPaymentType().getCode());
        return responseXml;
    }
}

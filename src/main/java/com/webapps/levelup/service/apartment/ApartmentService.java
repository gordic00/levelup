package com.webapps.levelup.service.apartment;

import com.webapps.levelup.configuration.ModelMapperConfig;
import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.external.AmazonClient;
import com.webapps.levelup.helper.FileHelper;
import com.webapps.levelup.helper.TokenHelper;
import com.webapps.levelup.model.apartement_images.ApartmentImagesResponse;
import com.webapps.levelup.model.apartment.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URLConnection;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentService {
    private final ApartmentRepository repo;
    private final ApartmentResponseRepository repoResponse;
    private final TypeRepository repoType;
    private final LocationRepository repoLocation;
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
    private final AmazonClient amazonClient;

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
            List<Integer> additional, Integer heatingId, List<Integer> included) {
        if (!additional.isEmpty()) {
            List<AdditionalEntity> add = additionalRepo.findAll();
            add.forEach(a -> additional.forEach(ma -> {
                if (a.getId().equals(ma)) {
                    newApartment.getAdditional().add(a);
                }
            }));
        }
        if (heatingId != null) {
            List<HeatingEntity> add = heatingRepository.findAll();
            add.forEach(a -> {
                if (a.getId().equals(heatingId)) {
                    Set<HeatingEntity> heat = new HashSet<>();
                    heat.add(a);
                    newApartment.setHeating(heat);
                }
            });
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
            save.setAdCode(apartment.get().getAdCode());
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


    /**
     * Read all apartments by type code id and ad Code.
     *
     * @param typeCodeId Integer
     * @param adCode     String
     * @param page       Integer
     * @param size       Integer
     * @return Page<ApartmentResponse>
     */
    public ResponseEntity<Page<ApartmentResponse>> readByTypeCodeAndAdCode
    (Integer typeCodeId, String adCode, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ApartmentResponse> responses = typeCodeId == null ? repoResponse.findAll(pageable) :
                repoResponse.findByTypeEntity_IdAndAdCodeContainsIgnoreCase(typeCodeId, adCode, pageable);

        return responses.getContent().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(responses);
    }

    /*
    Read all apartments.
     */
    public ResponseEntity<Page<ApartmentResponse>> readAll(ApartmentReadDto apartmentReadDto) {
        Page<ApartmentResponse> result;
        List<ApartmentResponse> query;

        SearchParamsDto searchParams = prepareParamsForSearch(
                apartmentReadDto.getTypeIds(),
                apartmentReadDto.getStructureIds(),
                apartmentReadDto.getConstructionTypeIds());

        if (apartmentReadDto.getLocation() == null) {
            if (apartmentReadDto.getAdCode() == null) {
                query = repoResponse.
                        findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdIn(
                                searchParams.getTypeIds(),
                                searchParams.getStructureIds(),
                                searchParams.getConstructionTypeIds()
                        );
            } else {
                query = repoResponse.
                        findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdInAndAdCodeContainsIgnoreCase
                                (
                                        searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getConstructionTypeIds(),
                                        apartmentReadDto.getAdCode()
                                );
            }
        } else {
            if (apartmentReadDto.getAdCode() == null) {
                query = repoResponse.
                        findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdInAndLocationEntity_LocationCodeContainsIgnoreCase
                                (
                                        searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getConstructionTypeIds(),
                                        apartmentReadDto.getLocation()
                                );
            } else {
                query = repoResponse.
                        findAllByTypeEntityIdInAndStructureIdInAndConstructionTypeIdInAndLocationEntity_LocationCodeContainsIgnoreCaseAndAdCodeContainsIgnoreCase
                                (
                                        searchParams.getTypeIds(), searchParams.getStructureIds(),
                                        searchParams.getConstructionTypeIds(),
                                        apartmentReadDto.getLocation(),
                                        apartmentReadDto.getAdCode()
                                );
            }
        }
        result = filterResult(apartmentReadDto, query);

        return result.getContent().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    /*
    Removes from query if not contains search params.
     */
    private Page<ApartmentResponse> filterResult(ApartmentReadDto apartmentReadDto, List<ApartmentResponse> query) {
        Pageable pageable = PageRequest.of(apartmentReadDto.getPage(), apartmentReadDto.getSize());
        HashSet<ApartmentResponse> remove = new HashSet<>();
        for (ApartmentResponse q : query) {
            if (!apartmentReadDto.getFloorIds().isEmpty() && !apartmentReadDto.getFloorIds().contains(q.getFloorEntity().getId())) {
                remove.add(q);
            }
            if (!apartmentReadDto.getFurnishedIds().isEmpty() && !apartmentReadDto.getFurnishedIds().contains(q.getFurnished().getId())) {
                remove.add(q);
            }
            if (q.getIncluded() != null && !apartmentReadDto.getIncludedIds().isEmpty()) {
                List<Integer> ids = new ArrayList<>();
                q.getIncluded().forEach(i -> ids.add(i.getId()));
                ids.retainAll(apartmentReadDto.getIncludedIds());
                if (ids.isEmpty()) {
                    remove.add(q);
                }
            }
            if (q.getHeating() != null && !apartmentReadDto.getHeatingIds().isEmpty()) {
                List<Integer> ids = new ArrayList<>();
                q.getHeating().forEach(h -> ids.add(h.getId()));
                ids.retainAll(apartmentReadDto.getHeatingIds());
                if (ids.isEmpty()) {
                    remove.add(q);
                }
            }
            if (apartmentReadDto.getPriceFrom() != null){
                if (q.getMonthlyUtilities() != null && q.getMonthlyUtilities() < apartmentReadDto.getPriceFrom()) {
                    remove.add(q);
                }
                if (q.getMonthlyUtilities() == null) {
                    remove.add(q);
                }
            }
            if (apartmentReadDto.getPriceTo() != null) {
                if (q.getMonthlyUtilities() != null && q.getMonthlyUtilities() > apartmentReadDto.getPriceTo()) {
                    remove.add(q);
                }
                if (q.getMonthlyUtilities() == null) {
                    remove.add(q);
                }
            }
        }
        remove.forEach(query::remove);

        return new PageImpl<>(pageableResponse(
                query, apartmentReadDto.getPage(), apartmentReadDto.getSize()),
                pageable,
                query.size());
    }

    /*
    Returns sublist from page and size.
    */
    private <E> List<E> pageableResponse(List<E> response, Integer page, Integer size) {
        int from = Math.min(page * size, response.size());
        int to = Math.min(from + size, response.size());

        return response.subList(from, to);
    }

    /*
    Populate parameters for search.
     */
    private SearchParamsDto prepareParamsForSearch(
            List<Integer> typeIds,
            List<Integer> structureIds,
            List<Integer> constructionTypeIds
    ) {
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
        //Construction Types
        if (constructionTypeIds == null || constructionTypeIds.isEmpty()) {
            constructionTypeIds = new ArrayList<>();
            for (ConstructionTypeEntity var : allLists.getConstructionType()) {
                constructionTypeIds.add(var.getId());
            }
        }
        searchParamsDto.setConstructionTypeIds(constructionTypeIds);
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
    Read all locations by location name.
     */
    public ResponseEntity<List<LocationEntity>> readAllLocations(String location) {
        location = location.replace(" ", "-");
        List<LocationEntity> response =
                repoLocation.findByFullNameIsContainingIgnoreCaseOrLocationCodeIsContainingIgnoreCase(location, location);

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
        File file = prepareXmlFile();
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
        file.delete();
    }

    public String uploadXmlFile() {
        File file = prepareXmlFile();
        try {
            String url = amazonClient.uploadFile(file);
            file.delete();
            return url;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public void deleteFromAws(String url) {
        amazonClient.deleteFileFromS3Bucket(url);
    }

    private File prepareXmlFile() {
        List<ApartmentResponse> apartmentResponses = repoResponse.findByOrderByIdDesc();
        if (apartmentResponses.isEmpty()) {
            throw new CustomException("There are no apartments.");
        }
        String xmlFile = prepareXml(apartmentResponses);
        try {
            File file = new File("/var/lib/tomcat9/webapps/files/level_up.xml");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            bufferedWriter.write(xmlFile);
            bufferedWriter.close();
            return file;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private String prepareXml(List<ApartmentResponse> apartmentResponses) {
        StringBuilder xmlFile = new StringBuilder(Objects.requireNonNull(FileHelper.readResource("xml_validator/templateXml.txt")));
        for (ApartmentResponse apartment : apartmentResponses) {
            String xmlContent = FileHelper.readResource("xml_validator/templateXmlContent.txt");
            assert xmlContent != null;
            xmlContent = populateFirstPartXml(xmlContent, apartment);
            xmlContent = populatePhotos(xmlContent, apartment);
            xmlContent = populateSecondPartXml(xmlContent, apartment);
            xmlContent = populateHeating(xmlContent, apartment);
            xmlContent = populateThirdPartXml(xmlContent, apartment);
            xmlContent = populateLists(xmlContent, apartment);
            xmlContent = populateForthPartXml(xmlContent, apartment);
            xmlContent = xmlContent + "</realEstate>";
            xmlFile.append(xmlContent);
        }
        xmlFile.append("</realEstates>");
        return xmlFile.toString();
    }

    private String populateLists(String xmlContent, ApartmentResponse apartment) {

        if (apartment.getAdditional() != null) {
            for (AdditionalEntity additional : apartment.getAdditional()) {
                xmlContent = xmlContent + "<additional>" + additional.getCode() + "</additional>";
            }
        }
        if (apartment.getIncluded() != null) {
            for (IncludedEntity included : apartment.getIncluded()) {
                xmlContent = xmlContent + "<included>" + included.getCode() + "</included>";
            }
        }
        return xmlContent;
    }

    private String populateHeating(String xmlContent, ApartmentResponse apartment) {
        if (apartment.getHeating() != null) {
            xmlContent = xmlContent + "<heating>" + apartment.getHeating().stream().toList().get(0).getCode() + "</heating>";
        }
        return xmlContent;
    }

    private String populatePhotos(String xmlContent, ApartmentResponse apartment) {
        if (apartment.getApartmentImagesEntities() != null) {
            for (ApartmentImagesResponse image : apartment.getApartmentImagesEntities()) {
                xmlContent = xmlContent + "<photo><url>" + image.getPath() + "</url></photo>";
            }
        }
        return xmlContent;
    }

    private String populateFirstPartXml(String xmlContent, ApartmentResponse apartment) {
        return xmlContent
                .replace("{id}", nullR(apartment.getId()).toString())
                .replace("{category}", "stanovi-iznajmljivanje")
                .replace("{price}", nullR(apartment.getMonthlyUtilities()).toString())
                .replace("{county_name}", "Srbija")
                .replace("{location_id}", nullR(apartment.getLocationEntity().getLocationId()).toString())
                .replace("{address}", nullR(apartment.getAddress()).toString())
                .replace("{address_no}", nullR(apartment.getAddressNo()).toString())
                .replace("{description_rs}", nullR(apartment.getDescriptionSr()).toString())
                .replace("{description_en}", apartment.getDescriptionEn() == null ? "" : "<description_en>" + apartment.getDescriptionEn() + "</description_en>");
    }

    private String populateSecondPartXml(String xmlContent, ApartmentResponse apartment) {
        return xmlContent + "<area>" + nullR(apartment.getQuadrature()) + "</area>" +
                "<construction_type>" + nullR(apartment.getConstructionType().getCode()) + "</construction_type>" +
                "<furnished>" + nullR(apartment.getFurnished().getCode()) + "</furnished>" +
                "<bathrooms>" + nullR(apartment.getBathrooms().getCode()) + "</bathrooms>";
    }

    private String populateThirdPartXml(String xmlContent, ApartmentResponse apartment) {
        return xmlContent + "<condition>" + nullR(apartment.getConditionEntity().getCode()) + "</condition>" +
                "<payment_type>" + nullR(apartment.getPaymentType().getCode()) + "</payment_type>" +
                "<monthly_utilities>" + nullR(apartment.getMonthlyUtilities()) + "</monthly_utilities>" +
                "<advertiser>" + nullR(apartment.getAdvertiser().getCode()) + "</advertiser>" +
                "<floor>" + nullR(apartment.getFloorEntity().getCode()) + "</floor>" +
                "<number_storeys>" + nullR(apartment.getNumberStoreys().getCode()) + "</number_storeys>";

    }

    private String populateForthPartXml(String xmlContent, ApartmentResponse apartment) {
        xmlContent = xmlContent + "<structure>" + nullR(apartment.getStructure().getCode()) + "</structure>";
        if (apartment.getRooms() != null) {
            switch (apartment.getRooms()) {
                case 2 -> xmlContent = xmlContent + "<number_rooms>number_rooms_two_rooms</number_rooms>";
                case 3 -> xmlContent = xmlContent + "<number_rooms>number_rooms_three_rooms</number_rooms>";
                case 4 -> xmlContent = xmlContent + "<number_rooms>number_rooms_four_rooms</number_rooms>";
                case 5 -> xmlContent = xmlContent + "<number_rooms>number_rooms_five_or_more_rooms</number_rooms>";
                default -> xmlContent = xmlContent + "<number_rooms>number_rooms_one_room</number_rooms>";
            }
        }
        return xmlContent;
    }

    private Object nullR(Object obj) {
        return obj == null ? "" : obj;
    }

    public Boolean validateXMLSchema(MultipartFile xmlFile) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File local = new File(Objects.requireNonNull(xmlFile.getOriginalFilename()));
            FileOutputStream fos = new FileOutputStream(local);
            fos.write(xmlFile.getBytes());
            fos.close();
            Schema schema = factory.newSchema(new File("src/main/resources/xml_validator/custom-xml-format-new.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(local));
            if (local.exists()) {
                FileUtils.forceDelete(local);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        } catch (SAXException e1) {
            System.out.println("SAX Exception: " + e1.getMessage());
            return false;
        }
        return true;
    }
}

package com.webapps.levelup.controller.apartment;

import com.webapps.levelup.configuration.TokenAuth;
import com.webapps.levelup.model.apartment.ApartmentResponse;
import com.webapps.levelup.model.apartment.LocationEntity;
import com.webapps.levelup.model.apartment.TypeEntity;
import com.webapps.levelup.model.dto.ApartmentCreateDto;
import com.webapps.levelup.model.dto.ApartmentReadDto;
import com.webapps.levelup.model.dto.ApartmentUpdateDto;
import com.webapps.levelup.model.dto.ListsDto;
import com.webapps.levelup.service.apartment.ApartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "api/apartment")
@RequiredArgsConstructor
@Tag(name = "Apartments")
public class ApartmentController {
    private final ApartmentService service;

    /**
     * Creates new apartment.
     *
     * @param request HttpServletRequest
     * @param model   ApartmentEntity
     * @return ApartmentResponse
     */
    @TokenAuth
    @PostMapping(path = "/create")
    public ResponseEntity<ApartmentResponse> create(
            HttpServletRequest request,
            @Valid @RequestBody ApartmentCreateDto model
    ) {
        return service.create(request, model);
    }

    /**
     * Update Apartment.
     *
     * @param request HttpServletRequest
     * @param model   ApartmentUpdateDto
     * @return HttpStatus
     */
    @TokenAuth
    @PutMapping(path = "/update")
    public ResponseEntity<HttpStatus> update(
            HttpServletRequest request,
            @Valid @RequestBody ApartmentUpdateDto model
    ) {
        return service.update(request, model);
    }

    /**
     * Return Apartment by ID.
     *
     * @param apartmentId Integer
     * @return ApartmentResponse
     */
    @GetMapping(path = "/read-by-id")
    public ResponseEntity<ApartmentResponse> read(
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId
    ) {
        return service.read(apartmentId);
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
    @GetMapping(path = "/read-by-type-and-ad-code")
    public ResponseEntity<Page<ApartmentResponse>> readByTypeCodeAndAdCode(
            @RequestParam(value = "type_code_id", required = false)
            Integer typeCodeId,
            @RequestParam(value = "ad_code", required = false, defaultValue = "")
            String adCode,
            @RequestParam(value = "page", required = false, defaultValue = "0")
            Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10")
            Integer size

    ) {
        return service.readByTypeCodeAndAdCode(typeCodeId, adCode, page, size);
    }

    /**
     * Return all Apartments.
     *
     * @return List<ApartmentResponse>
     */
    @PostMapping(path = "/read-all-apartments")
    public ResponseEntity<Page<ApartmentResponse>> readAll(
            @RequestBody ApartmentReadDto apartmentReadDto
    ) {
        return service.readAll(apartmentReadDto);
    }

    /**
     * Return all available apartment types.
     *
     * @return List<TypeEntity>
     */
    @GetMapping(path = "/read-types")
    public ResponseEntity<List<TypeEntity>> readTypes() {
        return service.readTypes();
    }

    /**
     * Read all params lists.
     *
     * @return ListsDto
     */
    @GetMapping(path = "/read-all-lists")
    public ResponseEntity<ListsDto> readAllLists() {
        return service.readAllLists();
    }

    /**
     * Read all params lists.
     *
     * @return ListsDto
     */
    @GetMapping(path = "/read-all-locations")
    public ResponseEntity<List<LocationEntity>> readAllLocations(
            @RequestParam(value = "location", required = false, defaultValue = "beograd")
            String location
    ) {
        return service.readAllLocations(location);
    }

    /**
     * Delete Apartment by ID.
     *
     * @param request     HttpServletRequest
     * @param apartmentId Integer
     * @return String
     */
    @TokenAuth
    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> delete(
            HttpServletRequest request,
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId
    ) {
        return service.delete(apartmentId);
    }

    /**
     * Download XML file by Apartment ID.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException e
     */
    @TokenAuth
    @PostMapping(path = "download-xml")
    public void downloadXml(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.downloadXml(response);
    }

    @TokenAuth
    @PostMapping(path = "upload-xml")
    public String uploadXml(
            HttpServletRequest request) {
        return service.uploadXmlFile();
    }

    @TokenAuth
    @DeleteMapping(path = "delete-from-aws-by-url")
    public void deleteFromAsw(
            HttpServletRequest request,
            @RequestParam("url") String url) {
        service.deleteFromAws(url);
    }

    /**
     * Validate XML file based on xsd schema.
     *
     * @param request HttpServletRequest
     * @param xmlFile MultipartFile
     * @return Boolean
     */
    @TokenAuth
    @PostMapping(path = "validate-xml")
    public Boolean downloadXml(
            HttpServletRequest request,
            @RequestPart("xml_file")
            MultipartFile xmlFile
    ) {
        return service.validateXMLSchema(xmlFile);
    }
}

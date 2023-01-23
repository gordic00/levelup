package com.webapps.levelup.controller.apartment;

import com.webapps.levelup.configuration.TokenAuth;
import com.webapps.levelup.model.apartment.ApartmentResponse;
import com.webapps.levelup.model.apartment.TypeEntity;
import com.webapps.levelup.model.dto.ApartmentCreateDto;
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
     * Return all Apartments.
     *
     * @return List<ApartmentResponse>
     */
    @GetMapping(path = "/read-all-apartments")
    public ResponseEntity<Page<ApartmentResponse>> readAll(
            @RequestParam(name = "type_ids", required = false)
            List<Integer> typeIds,
            @RequestParam(name = "location", required = false)
            String location,
            @RequestParam(name = "structure_ids", required = false)
            List<Integer> structureIds,
            @RequestParam(name = "price_from", required = false, defaultValue = "0")
            Long priceFrom,
            @RequestParam(name = "price_to", required = false, defaultValue = "999999999")
            Long priceTo,
            @RequestParam(name = "ad_code", required = false)
            String adCode,
            @RequestParam(name = "floor_ids", required = false)
            List<Integer> floorIds,
            @RequestParam(name = "furnished_ids", required = false)
            List<Integer> furnishedIds,
            @RequestParam(name = "heating_ids", required = false)
            List<Integer> heatingIds,
            @RequestParam(name = "construction_type_ids", required = false)
            List<Integer> constructionTypeIds,
            @RequestParam(name = "included_ids", required = false)
            List<Integer> includedIds,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10")
            Integer size
    ) {
        return service.readAll(typeIds, location, structureIds, priceFrom, priceTo, adCode,
                floorIds, furnishedIds, heatingIds, constructionTypeIds, includedIds, page, size);
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
    @TokenAuth
    @GetMapping(path = "/read-all-lists")
    public ResponseEntity<ListsDto> readAllLists() {
        return service.readAllLists();
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
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param apartmentId Integer
     * @throws IOException e
     */
    @TokenAuth
    @PostMapping(path = "download-xml")
    public void downloadXml(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("apartment_id")
            Integer apartmentId) throws IOException {
        service.downloadXml(response, apartmentId);
    }
}

package com.webapps.levelup.controller.apartment;

import com.webapps.levelup.configuration.TokenAuth;
import com.webapps.levelup.model.apartement_images.ApartmentImagesResponse;
import com.webapps.levelup.service.apartment.ApartmentImagesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "api/apartment-images")
@RequiredArgsConstructor
@Tag(name = "Apartment Images")
public class ApartmentImagesController {
    private final ApartmentImagesService service;

    @TokenAuth
    @PostMapping(
            path = "/upload/new-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> newStaticFiles(
            HttpServletRequest request,
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId,
            @NotEmpty
            @RequestPart("images")
            MultipartFile[] images) throws IOException {
        return service.uploadNewImage(apartmentId, images);
    }

    @TokenAuth
    @PostMapping(
            path = "/upload/new-image-to-aws",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> newFilesToAws(
            HttpServletRequest request,
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId,
            @NotEmpty
            @RequestPart("images")
            MultipartFile[] images) {
        return service.uploadNewImageToAws(apartmentId, images);
    }

    @TokenAuth
    @GetMapping(path = "/read-all-by-apartment-id")
    public ResponseEntity<List<ApartmentImagesResponse>> readAllByApartmentId(
            HttpServletRequest request,
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId
    ) {
        return service.readAllImagesByApartmentId(apartmentId);
    }

    @TokenAuth
    @PutMapping("/update-sorting-by-ids")
    public ResponseEntity<String> updateSorting(
            HttpServletRequest request,
            @Valid @RequestBody List<ApartmentImagesResponse> apartmentImages
    ) {
        return service.updateSorting(apartmentImages);
    }


    @TokenAuth
    @DeleteMapping(path = "/delete-by-id-list")
    public ResponseEntity<String> delete(
            HttpServletRequest request,
            @NotNull
            @RequestParam("image_ids")
            List<Integer> imageIds
    ) {
        return service.deleteByList(imageIds);
    }

    @TokenAuth
    @DeleteMapping(path = "/delete-by-id-list-from-aws")
    public ResponseEntity<String> deleteFromAws(
            HttpServletRequest request,
            @NotNull
            @RequestParam("image_ids")
            List<Integer> imageIds
    ) {
        return service.deleteByListFromAws(imageIds);
    }

    @TokenAuth
    @DeleteMapping(path = "/delete-all-by-apartment-id")
    public ResponseEntity<String> deleteAllByApartment(
            HttpServletRequest request,
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId
    ) {
        return service.deleteAllByApartment(apartmentId);
    }

    @TokenAuth
    @DeleteMapping(path = "/delete-all-by-apartment-id-from-aws")
    public ResponseEntity<String> deleteAllByApartmentFromAws(
            HttpServletRequest request,
            @NotNull
            @RequestParam("apartment_id")
            Integer apartmentId
    ) {
        return service.deleteAllByApartmentFromAws(apartmentId);
    }

    @TokenAuth
    @PostMapping(
            path = "/upload/new-watermark",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> newWatermark(
            HttpServletRequest request,
            @RequestPart("watermark")
            MultipartFile watermark) throws IOException {
        return service.uploadNewWatermark(watermark);
    }
}

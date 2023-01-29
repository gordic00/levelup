package com.webapps.levelup.service.apartment;

import com.webapps.levelup.configuration.AppProperties;
import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.external.AmazonClient;
import com.webapps.levelup.model.apartement_images.ApartmentImagesEntity;
import com.webapps.levelup.model.apartement_images.ApartmentImagesResponse;
import com.webapps.levelup.model.apartment.ApartmentResponse;
import com.webapps.levelup.repository.apartment.ApartmentImageRepository;
import com.webapps.levelup.repository.apartment.ApartmentImageResponseRepository;
import com.webapps.levelup.repository.apartment.ApartmentResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentImagesService {
    private final AppProperties appProperties;
    private final ApartmentResponseRepository repoApartmentResponse;
    private final ApartmentImageRepository repoImage;
    private final ApartmentImageResponseRepository repoResponseImage;
    private final AmazonClient amazonClient;

    @Transactional
    public ResponseEntity<HttpStatus> uploadNewImage(Integer apartmentId, MultipartFile[] images) throws IOException {
        Optional<ApartmentResponse> apartment = repoApartmentResponse.findById(apartmentId);
        if (apartment.isEmpty()) {
            throw new CustomException("There is no Apartment in data base with given ID.");
        }
        File localesDir = new File(appProperties.getImagePath() + "/" + apartmentId);
        if (!localesDir.exists()) {
            localesDir.mkdirs();
        }
        validateImages(images, localesDir);
        for (MultipartFile image : images) {
            File convFile = new File(localesDir.getAbsolutePath() + "/" + image.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(image.getBytes());
            fos.close();
            ApartmentImagesEntity imagesEntity = new ApartmentImagesEntity();
            imagesEntity.setApartmentId(apartmentId);
            imagesEntity.setName(image.getOriginalFilename());
            imagesEntity.setPath(convFile.getAbsolutePath());
            repoImage.save(imagesEntity);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Upload files to AWS.
     *
     * @param apartmentId Integer
     * @param images      MultipartFile[]
     * @return HttpStatus
     */
    public ResponseEntity<HttpStatus> uploadNewImageToAws(Integer apartmentId, MultipartFile[] images) {
        Optional<ApartmentResponse> apartment = repoApartmentResponse.findById(apartmentId);
        if (apartment.isEmpty()) {
            throw new CustomException("There is no Apartment in data base with given ID.");
        }

        for (MultipartFile image : images) {
            if (allowedExtension(image)) {
                throw new CustomException("File is not valid type (jpeg, jpg, img, png");
            }
            try {
                String imageUrl = amazonClient.uploadFile(image);
                ApartmentImagesEntity imagesEntity = new ApartmentImagesEntity();
                imagesEntity.setApartmentId(apartmentId);
                imagesEntity.setName(image.getOriginalFilename());
                imagesEntity.setPath(imageUrl);
                repoImage.save(imagesEntity);
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void validateImages(MultipartFile[] images, File localesDir) {
        for (MultipartFile image : images) {
            if (allowedExtension(image)) {
                throw new CustomException("File is not valid type (jpeg, jpg, img, png)");
            }
            File[] apartmentImages = localesDir.listFiles();
            if (apartmentImages != null) {
                for (File f : apartmentImages) {
                    if (f.getName().equals(image.getOriginalFilename())) {
                        throw new CustomException
                                ("Picture with this name: " + image.getOriginalFilename() + " is already uploaded");
                    }
                }
            }
        }
    }

    private boolean allowedExtension(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        List<String> allowed = List.of("jpeg", "jpg", "img", "png");

        return !allowed.contains(extension);
    }

    @Transactional
    public ResponseEntity<String> deleteByList(List<Integer> imageIds) {
        for (Integer id : imageIds) {
            Optional<ApartmentImagesResponse> image = repoResponseImage.findById(id);
            if (image.isPresent()) {
                try {
                    repoImage.deleteById(id);
                    deleteImageInLocalStorage(image.get().getPath());
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }
            }
        }
        return ResponseEntity.ok("Successfully deleted");
    }

    @Transactional
    public ResponseEntity<String> deleteByListFromAws(List<Integer> imageIds) {
        for (Integer id : imageIds) {
            Optional<ApartmentImagesResponse> image = repoResponseImage.findById(id);
            if (image.isPresent()) {
                try {
                    repoImage.deleteById(id);
                    amazonClient.deleteFileFromS3Bucket(image.get().getPath());
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }
            }
        }
        return ResponseEntity.ok("Successfully deleted");
    }

    @Transactional
    public ResponseEntity<String> deleteAllByApartment(Integer apartmentId) {
        try {
            repoImage.deleteAllByApartmentId(apartmentId);
            deleteLocalStorageByApartmentId(apartmentId);
            return ResponseEntity.ok("Successfully deleted");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteAllByApartmentFromAws(Integer apartmentId) {
        List<ApartmentImagesResponse> images = repoResponseImage.findAllByApartmentId(apartmentId);
        List<Integer> ids = new ArrayList<>();
        images.forEach(i -> ids.add(i.getId()));
        return deleteByListFromAws(ids);
    }

    private void deleteImageInLocalStorage(String path) {
        File localImage = new File(path);
        localImage.delete();
    }

    public void deleteLocalStorageByApartmentId(Integer apartmentId) {
        File localesDir = new File(appProperties.getImagePath() + "/" + apartmentId);
        if (localesDir.exists()) {
            if (localesDir.listFiles() != null) {
                for (File file : Objects.requireNonNull(localesDir.listFiles())) {
                    file.delete();
                }
            }
            localesDir.delete();
        }
    }

    /**
     * Return All images by Apartment Id.
     *
     * @param apartmentId Integer
     * @return List<ApartmentImagesResponse>
     */
    public ResponseEntity<List<ApartmentImagesResponse>> readAllImagesByApartmentId(Integer apartmentId) {
        Optional<ApartmentResponse> apartment = repoApartmentResponse.findById(apartmentId);
        if (apartment.isEmpty()) {
            throw new CustomException("There is no Apartment in data base with given ID.");
        }
        List<ApartmentImagesResponse> response = repoResponseImage.findAllByApartmentId(apartmentId);

        return response == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    /**
     * Update sorting.
     *
     * @param apartmentImages List<ApartmentImagesResponse>
     * @return String
     */
    @Transactional
    public ResponseEntity<String> updateSorting(List<ApartmentImagesResponse> apartmentImages) {
        apartmentImages.forEach(image -> {
            Optional<ApartmentImagesResponse> save = repoResponseImage.findById(image.getId());
            if (save.isPresent()) {
                try {
                    save.get().setSorting(image.getSorting());
                    repoResponseImage.save(save.get());
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }
            }
        });
        return ResponseEntity.ok("Successfully updated.");
    }
}

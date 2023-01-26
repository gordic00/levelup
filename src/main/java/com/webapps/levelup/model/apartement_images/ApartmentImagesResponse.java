package com.webapps.levelup.model.apartement_images;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(schema = "level_up", name = "apartment_images")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ApartmentImagesResponse {
    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "1")
    @Min(value = 1, message = "Apartment_id must be greater than or equal to 1")
    @NotNull(message = "apartment_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("apartment_id")
    @Column(name = "apartment_id", nullable = false)
    private Integer apartmentId;

    @Schema(example = "someImage.jpeg")
    @NotNull(message = "Name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "img_room_1")
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(example = "src/uploads/folder...")
    @NotNull(message = "Path cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "src/images/1/...")
    @JsonProperty("url")
    @Column(name = "path", nullable = false)
    private String path;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "1")
    @JsonProperty("sorting")
    @Column(name = "sorting")
    private Integer sorting;
}

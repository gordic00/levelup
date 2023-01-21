package com.webapps.levelup.model.apartement_images;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ApartmentImagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", length = 11)
    private Integer id;

    @Min(value = 1, message = "Apartment_id must be greater than or equal to 1")
    @NotNull(message = "apartment_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("apartment_id")
    @Column(name = "apartment_id", nullable = false)
    private Integer apartmentId;

    @NotNull(message = "Name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "img_room_1")
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Path cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "src/images/1/...")
    @JsonProperty("path")
    @Column(name = "path", nullable = false)
    private String path;
}

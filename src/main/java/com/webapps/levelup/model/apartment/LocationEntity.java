package com.webapps.levelup.model.apartment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(schema = "level_up", name = "smsg_locations")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class LocationEntity {

    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "beograd-stari-grad-dorcol")
    @NotNull(message = "location_code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "beograd-stari-grad-dorcol")
    @JsonProperty("location_code")
    @Column(name = "location_code", nullable = false)
    private String locationCode;

    @Schema(example = "Beograd / Stari grad / Dorćol")
    @NotNull(message = "full_name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Beograd / Stari grad / Dorćol")
    @JsonProperty("full_name")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Schema(example = "913")
    @NotNull(message = "location_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "913")
    @JsonProperty("location_id")
    @Column(name = "location_id", nullable = false)
    private Integer locationId;
}

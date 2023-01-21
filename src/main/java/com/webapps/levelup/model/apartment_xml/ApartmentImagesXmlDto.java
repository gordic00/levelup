package com.webapps.levelup.model.apartment_xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class ApartmentImagesXmlDto {
    @Schema(example = "src/uploads/folder...")
    @NotNull(message = "Path cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "src/images/1/...")
    @JsonProperty("photo_url")
    @Column(name = "path", nullable = false)
    private String path;
}

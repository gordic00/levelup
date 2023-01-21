package com.webapps.levelup.model.apartment_xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdditionalXmlDto {
    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("additional")
    private String code;
}

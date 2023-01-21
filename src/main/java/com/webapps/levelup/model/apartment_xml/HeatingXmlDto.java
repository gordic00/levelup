package com.webapps.levelup.model.apartment_xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HeatingXmlDto {
    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("heating")
    private String code;
}

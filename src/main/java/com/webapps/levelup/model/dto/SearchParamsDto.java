package com.webapps.levelup.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SearchParamsDto {
    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("type_ids")
    private List<Integer> typeIds;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("structure_ids")
    private List<Integer> structureIds;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("floor_ids")
    private List<Integer> floorIds;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("furnished_ids")
    private List<Integer> furnishedIds;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("heating_ids")
    private List<Integer> heatingIds;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("construction_type_ids")
    private List<Integer> constructionTypeIds;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @JsonProperty("included_ids")
    private List<Integer> includedIds;
}

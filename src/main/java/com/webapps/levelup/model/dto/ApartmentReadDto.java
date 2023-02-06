package com.webapps.levelup.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ApartmentReadDto {

    @NotNull
    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("type_ids")
    private List<Integer> typeIds;

    @Schema(example = "Dorcol")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Dorcol")
    @JsonProperty("location")
    private String location;

    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("structure_ids")
    private List<Integer> structureIds;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "1")
    @JsonProperty(value = "price_from")
    private Long priceFrom = 0L;

    @Schema(example = "100")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "100")
    @JsonProperty(value = "price_to")
    private Long priceTo = 99999999L;

    @Schema(example = "721352")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "721352")
    @JsonProperty("ad_code")
    private String adCode;

    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("floor_ids")
    private List<Integer> floorIds = new ArrayList<>();

    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("furnished_ids")
    private List<Integer> furnishedIds = new ArrayList<>();

    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("heating_ids")
    private List<Integer> heatingIds = new ArrayList<>();

    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("construction_type_ids")
    private List<Integer> constructionTypeIds;

    @Schema(example = "[1, 2]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "[1, 2]")
    @JsonProperty("included_ids")
    private List<Integer> includedIds = new ArrayList<>();

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("page")
    private Integer page = 0;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("size")
    private Integer size = 10;
}
















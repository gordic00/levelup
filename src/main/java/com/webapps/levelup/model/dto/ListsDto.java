package com.webapps.levelup.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webapps.levelup.model.apartment.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ListsDto {
    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("additional")
    private List<AdditionalEntity> additional;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("advertiser")
    private List<AdvertiserEntity> advertiser;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("bathrooms")
    private List<BathroomsEntity> bathrooms;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("construction_type")
    private List<ConstructionTypeEntity> constructionType;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("floor")
    private List<FloorEntity> floor;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("furnished")
    private List<FurnishedEntity> furnished;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("heating")
    private List<HeatingEntity> heating;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("included")
    private List<IncludedEntity> included;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("payment_type")
    private List<PaymentTypeEntity> paymentType;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("structure")
    private List<StructureEntity> structure;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("condition")
    private List<ConditionEntity> condition;
}

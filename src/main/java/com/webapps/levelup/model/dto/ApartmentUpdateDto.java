package com.webapps.levelup.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ApartmentUpdateDto {

    @Schema(example = "1")
    @Min(value = 1, message = "type_id must be greater than or equal to 1")
    @NotNull(message = "type_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("id")
    private Integer id;

    @Schema(example = "1")
    @Min(value = 1, message = "type_id must be greater than or equal to 1")
    @NotNull(message = "type_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("type_id")
    private Integer typeId;

    @Schema(example = "Odlican Stan na Dorcolu")
    @NotNull(message = "title cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Stan na Dorcolu")
    @JsonProperty("title_sr")
    private String titleSr;

    @Schema(example = "Oтличная квартира на Дорколе")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Oтличная квартира на Дорколе")
    @JsonProperty("title_ru")
    private String titleRu;

    @Schema(example = "Excellent apartment on Dorcol")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Excellent apartment on Dorcol")
    @JsonProperty("title_en")
    private String titleEn;

    @Schema(example = "Beograd")
    @NotNull(message = "city cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Beograd")
    @JsonProperty("city")
    private String city;

    @Schema(example = "Stari Grad")
    @NotNull(message = "municipality cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Stari Grad")
    @JsonProperty("municipality")
    private String municipality;

    @Schema(example = "Kraljice Marije 10")
    @NotNull(message = "address cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Kneginje Ljubice")
    @JsonProperty("address")
    private String address;

    @Schema(example = "70")
    @Min(value = 1, message = "rooms must be greater than or equal to 1")
    @NotNull(message = "quadrature cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "76")
    @JsonProperty("quadrature")
    private Integer quadrature;

    @Schema(example = "Odlican svetao stan, blizu trga...")
    @NotNull(message = "description cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Komforan funkcionalan stan...")
    @JsonProperty("description_sr")
    private String descriptionSr;

    @Schema(example = "Отличная светлая квартира...")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Отличная светлая квартира...")
    @JsonProperty("description_ru")
    private String descriptionRu;

    @Schema(example = "Excellent bright apartment...")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Excellent bright apartment...")
    @JsonProperty("description_en")
    private String descriptionEn;

    @Schema(example = "2")
    @Min(value = 1, message = "rooms must be greater than or equal to 1")
    @NotNull(message = "rooms cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("rooms")
    private Integer rooms;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "floor_id")
    private Integer floorId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "number_storeys_id")
    private Integer numberStoreysId;

    @Schema(example = "1")
    @Min(value = 1, message = "structure_id must be greater than or equal to 1")
    @NotNull(message = "structure_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("structure_id")
    private Integer structureId;

    @Schema(example = "1")
    @Min(value = 1, message = "advertiser_id must be greater than or equal to 1")
    @NotNull(message = "advertiser_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("advertiser_id")
    private Integer advertiserId;

    @Schema(example = "1")
    @Min(value = 1, message = "construction_type_id must be greater than or equal to 1")
    @NotNull(message = "construction_type_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("construction_type_id")
    private Integer constructionTypeId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "condition_id")
    private Integer conditionId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "bathrooms_id")
    private Integer bathroomsId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "furnished_id")
    private Integer furnishedId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "payment_type_id")
    private Integer paymentTypeId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "1")
    @JsonProperty(value = "monthly_utilities")
    private Long monthlyUtilities;

    @Schema(example = "[1,2,3]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "[1,2,3]")
    @JsonProperty(value = "additional")
    private List<Integer> additional;

    @Schema(example = "[1,2,3]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "[1,2,3]")
    @JsonProperty(value = "heating")
    private List<Integer> heating;

    @Schema(example = "[1,2,3]")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "[1,2,3]")
    @JsonProperty(value = "included")
    private List<Integer> included;
}

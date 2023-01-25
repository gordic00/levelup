package com.webapps.levelup.model.apartment_xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "realEstate")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class RealEstate {
    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "1")
    @JsonProperty("id")
    private Integer id;

    @Schema(example = "Odlican Stan na Dorcolu")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Stan na Dorcolu")
    @JsonProperty("title_sr")
    private String titleSr;

    @Schema(example = "Beograd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Beograd")
    @JsonProperty("city_name")
    private String city;

    @Schema(example = "Stari Grad")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Stari Grad")
    @JsonProperty("city_area_name")
    private String municipality;

    @Schema(example = "Kraljice Marije 10")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Kneginje Ljubice")
    @JsonProperty("address")
    private String address;

    @Schema(example = "Odlican svetao stan, blizu trga...")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Komforan funkcionalan stan...")
    @JsonProperty("description_sr")
    private String descriptionSr;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty("photo")
    @JoinColumn(name = "apartment_id", insertable = false, updatable = false)
    @ToString.Exclude
    private List<ApartmentImagesXmlDto> apartmentImagesEntities;

    @Schema(example = "70")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "76")
    @JsonProperty("area")
    private Integer quadrature;

    @Schema(example = "estate_type_construction_old")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "estate_type_construction_old")
    @JsonProperty("constructionType")
    private String constructionType_code;

    @Schema(example = "furnished_full")
    @NotNull(message = "code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "furnished_full")
    @JsonProperty("furnished")
    private String furnished_code;

    @Schema(example = "bathrooms_1")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "bathrooms_1")
    @JsonProperty("bathrooms")
    private String bathrooms_code;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("heating")
    private List<HeatingXmlDto> heating;

    @Schema(example = "object_condition_original")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "object_condition_original")
    @JsonProperty("condition")
    private String condition_code;

    @Schema(example = "payment_type_monthly")
    @NotNull(message = "code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "payment_type_monthly")
    @JsonProperty("paymentType")
    private String paymentType_code;

    @Schema(example = "70")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "76")
    @JsonProperty("monthly_utilities")
    @Column(name = "monthly_utilities", nullable = false)
    private Long monthlyUtilities;

    @Schema(example = "advertiser_agency")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "advertiser_agency")
    @JsonProperty("advertiser")
    private String advertiser_code;

    @Schema(example = "floor_ground_floor")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "floor_ground_floor")
    @JsonProperty("floor")
    private String floor_code;

    @Schema(example = "floor_ground_floor")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "floor_ground_floor")
    @JsonProperty("numberStoreys")
    private String numberStoreys_code;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("additional")
    private List<AdditionalXmlDto> additional;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("included")
    private List<IncludedXmlDto> included;

    @Schema(example = "large_studio_estate_structure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "land_heating_central")
    @JsonProperty("structure")
    private String structure_code;

    @Schema(example = "2")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("number_rooms")
    private Integer rooms;
}



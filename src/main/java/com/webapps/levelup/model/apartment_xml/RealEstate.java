package com.webapps.levelup.model.apartment_xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "realEstate")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class RealEstate {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("city_name")
    private String city;

    @JsonProperty("city_area_name")
    private String municipality;

    @JsonProperty("address")
    private String address;

    @JsonProperty("description_rs")
    private String descriptionSr;

    @JsonProperty("photo")
    private List<ApartmentImagesXmlDto> apartmentImagesEntities;

    @JsonProperty("area")
    private Integer quadrature;

    @JsonProperty("construction_type")
    private String constructionType_code;

    @JsonProperty("furnished")
    private String furnished_code;

    @JsonProperty("bathrooms")
    private String bathrooms_code;

    @JsonProperty("heating")
    private List<HeatingXmlDto> heating;

    @JsonProperty("condition")
    private String condition_code;

    @JsonProperty("payment_type")
    private String paymentType_code;

    @JsonProperty("monthly_utilities")
    private Long monthlyUtilities;

    @JsonProperty("advertiser")
    private String advertiser_code;

    @JsonProperty("floor")
    private String floor_code;

    @JsonProperty("number_storeys")
    private String numberStoreys_code;

    @JsonProperty("additional")
    private List<AdditionalXmlDto> additional;

    @JsonProperty("included")
    private List<IncludedXmlDto> included;

    @JsonProperty("structure")
    private String structure_code;

    @JsonProperty("number_rooms")
    private Integer rooms;
}



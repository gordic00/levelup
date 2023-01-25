package com.webapps.levelup.model.apartment_xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.LinkedList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class LevelUpRealEstates {

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("realEstates")
    private List<RealEstate> realEstate = new LinkedList<>();

}



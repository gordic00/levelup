package com.webapps.levelup.model.apartment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "level_up", name = "apartment")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ApartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "1")
    @Min(value = 1, message = "type_id must be greater than or equal to 1")
    @NotNull(message = "type_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("type_id")
    @Column(name = "type_id")
    private Integer typeId;

    @Schema(example = "7854985")
    @NotNull(message = "Ad Code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "7854985")
    @JsonProperty("ad_code")
    @Column(name = "ad_code")
    private String adCode;

    @Schema(example = "Odlican Stan na Dorcolu")
    @NotNull(message = "title cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Stan na Dorcolu")
    @JsonProperty("title_sr")
    @Column(name = "title_sr")
    private String titleSr;

    @Schema(example = "Oтличная квартира на Дорколе")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Oтличная квартира на Дорколе")
    @JsonProperty("title_ru")
    @Column(name = "title_ru")
    private String titleRu;

    @Schema(example = "Excellent apartment on Dorcol")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Excellent apartment on Dorcol")
    @JsonProperty("title_en")
    @Column(name = "title_en")
    private String titleEn;

    @Schema(example = "1")
    @Min(value = 1, message = "location_id must be greater than or equal to 1")
    @NotNull(message = "location_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("location_id")
    @Column(name = "location_id")
    private Integer locationId;

    @Schema(example = "Kraljice Marije 10")
    @NotNull(message = "address cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Kneginje Ljubice")
    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    @Schema(example = "70")
    @NotNull(message = "Address number cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "70")
    @JsonProperty("address_no")
    @Column(name = "address_no")
    private Integer addressNo;

    @Schema(example = "70")
    @Min(value = 1, message = "rooms must be greater than or equal to 1")
    @NotNull(message = "quadrature cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "76")
    @JsonProperty("quadrature")
    @Column(name = "quadrature", nullable = false)
    private Integer quadrature;

    @Schema(example = "Odlican svetao stan, blizu trga...")
    @NotNull(message = "description cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Komforan funkcionalan stan...")
    @JsonProperty("description_sr")
    @Column(name = "description_sr", nullable = false)
    private String descriptionSr;

    @Schema(example = "Отличная светлая квартира...")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Отличная светлая квартира...")
    @JsonProperty("description_ru")
    @Column(name = "description_ru")
    private String descriptionRu;

    @Schema(example = "Excellent bright apartment...")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Excellent bright apartment...")
    @JsonProperty("description_en")
    @Column(name = "description_en")
    private String descriptionEn;

    @Schema(example = "2")
    @Min(value = 1, message = "rooms must be greater than or equal to 1")
    @NotNull(message = "rooms cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("rooms")
    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "floor_id")
    @Column(name = "floor_id")
    private Integer floorId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "number_storeys_id")
    @Column(name = "number_storeys_id")
    private Integer numberStoreysId;

    @Schema(example = "1")
    @Min(value = 1, message = "structure_id must be greater than or equal to 1")
    @NotNull(message = "structure_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("structure_id")
    @Column(name = "structure_id")
    private Integer structureId;

    @Schema(example = "1")
    @Min(value = 1, message = "advertiser_id must be greater than or equal to 1")
    @NotNull(message = "advertiser_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("advertiser_id")
    @Column(name = "advertiser_id")
    private Integer advertiserId;

    @Schema(example = "1")
    @Min(value = 1, message = "construction_type_id must be greater than or equal to 1")
    @NotNull(message = "construction_type_id cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("construction_type_id")
    @Column(name = "construction_type_id")
    private Integer constructionTypeId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "condition_id")
    @Column(name = "condition_id")
    private Integer conditionId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "bathrooms_id")
    @Column(name = "bathrooms_id")
    private Integer bathroomsId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "furnished_id")
    @Column(name = "furnished_id")
    private Integer furnishedId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty(value = "payment_type_id")
    @Column(name = "payment_type_id")
    private Integer paymentTypeId;

    @Schema(example = "1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "1")
    @JsonProperty(value = "monthly_utilities")
    @Column(name = "monthly_utilities")
    private Long monthlyUtilities;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @ManyToMany
    @JoinTable(
            name = "apartment_additional",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "additional_id"))
    private Set<AdditionalEntity> additional;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @ManyToMany
    @JoinTable(
            name = "apartment_heating",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "heating_id"))
    private Set<HeatingEntity> heating;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @ManyToMany
    @JoinTable(
            name = "apartment_included",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "included_id"))
    private Set<IncludedEntity> included;

    @Schema(example = "system")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "system")
    @JsonProperty(value = "created_by")
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Schema(example = "system")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "system")
    @JsonProperty(value = "last_modified_by")
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @Schema(example = "13-04-2022")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "13-04-2022")
    @JsonProperty(value = "created_date")
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Schema(example = "13-04-2022")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "13-04-2022")
    @JsonProperty(value = "last_modified_date")
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;
}

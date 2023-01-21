package com.webapps.levelup.model.apartment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webapps.levelup.model.apartement_images.ApartmentImagesResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(schema = "level_up", name = "apartment")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ApartmentResponse {
    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id", length = 11)
    private Integer id;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.OBJECT, pattern = "1")
    @JsonProperty("type")
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private TypeEntity typeEntity;

    @Schema(example = "7854985")
    @NotNull(message = "Ad Code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "7854985")
    @JsonProperty("ad_code")
    @Column(name = "ad_code")
    private String adCode;

    @Schema(example = "Odlican Stan na Dorcolu")
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

    @Schema(example = "Beograd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Beograd")
    @JsonProperty("city")
    @Column(name = "city", nullable = false)
    private String city;

    @Schema(example = "Stari Grad")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Stari Grad")
    @JsonProperty("municipality")
    @Column(name = "municipality", nullable = false)
    private String municipality;

    @Schema(example = "Kraljice Marije 10")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Kneginje Ljubice")
    @JsonProperty("address")
    @Column(name = "address", nullable = false)
    private String address;

    @Schema(example = "70")
    @NotNull(message = "Address number cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "70")
    @JsonProperty("address_no")
    @Column(name = "address_no")
    private Integer addressNo;

    @Schema(example = "70")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "76")
    @JsonProperty("quadrature")
    @Column(name = "quadrature", nullable = false)
    private Integer quadrature;

    @Schema(example = "Odlican svetao stan, blizu trga...")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Komforan funkcionalan stan...")
    @JsonProperty("description_sr")
    @Column(name = "description_sr")
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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("rooms")
    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("floor")
    @JoinColumn(name = "floor_id", insertable = false, updatable = false)
    private FloorEntity floorEntity;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("number_storeys")
    @JoinColumn(name = "number_storeys_id", insertable = false, updatable = false)
    private NumberStoreysEntity numberStoreys;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("structure")
    @JoinColumn(name = "structure_id", insertable = false, updatable = false)
    private StructureEntity structure;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("advertiser")
    @JoinColumn(name = "advertiser_id", insertable = false, updatable = false)
    private AdvertiserEntity advertiser;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("construction_type")
    @JoinColumn(name = "construction_type_id", insertable = false, updatable = false)
    private ConstructionTypeEntity constructionType;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("condition")
    @JoinColumn(name = "condition_id", insertable = false, updatable = false)
    private ConditionEntity conditionEntity2;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("bathrooms")
    @JoinColumn(name = "bathrooms_id", insertable = false, updatable = false)
    private BathroomsEntity bathrooms;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("furnished_id")
    @JoinColumn(name = "furnished_id", insertable = false, updatable = false)
    private FurnishedEntity furnished;

    @OneToOne
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "1")
    @JsonProperty("payment_type_id")
    @JoinColumn(name = "payment_type_id", insertable = false, updatable = false)
    private PaymentTypeEntity paymentType;

    @Schema(example = "70")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT, pattern = "76")
    @JsonProperty("monthly_utilities")
    @Column(name = "monthly_utilities", nullable = false)
    private Long monthlyUtilities;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("additional")
    @ManyToMany
    @JoinTable(
            name = "apartment_additional",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "additional_id"))
    @ToString.Exclude
    private Set<AdditionalEntity> additional;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("heating")
    @ManyToMany
    @JoinTable(
            name = "apartment_heating",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "heating_id"))
    @ToString.Exclude
    private Set<HeatingEntity> heating;

    @Schema()
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonProperty("included")
    @ManyToMany
    @JoinTable(
            name = "apartment_included",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "included_id"))
    @ToString.Exclude
    private Set<IncludedEntity> included;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty("images")
    @JoinColumn(name = "apartment_id", insertable = false, updatable = false)
    @ToString.Exclude
    private List<ApartmentImagesResponse> apartmentImagesEntities;
}

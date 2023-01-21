package com.webapps.levelup.model.apartment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(schema = "level_up", name = "construction_type")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class ConstructionTypeEntity {

    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "estate_type_construction_old")
    @NotNull(message = "code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "estate_type_construction_old")
    @JsonProperty("code")
    @Column(name = "code", nullable = false)
    private String code;

    @Schema(example = "starogradnja")
    @NotNull(message = "name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "starogradnja")
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;
}

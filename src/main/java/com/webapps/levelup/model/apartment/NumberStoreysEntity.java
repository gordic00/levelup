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
@Table(schema = "level_up", name = "number_storeys")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class NumberStoreysEntity {

    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "floor_ground_floor")
    @NotNull(message = "code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "floor_ground_floor")
    @JsonProperty("code")
    @Column(name = "code", nullable = false)
    private String code;

    @Schema(example = "prizemlje")
    @NotNull(message = "name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "prizemlje")
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;
}

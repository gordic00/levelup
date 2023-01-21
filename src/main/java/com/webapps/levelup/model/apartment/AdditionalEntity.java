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
@Table(schema = "level_up", name = "additional")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class AdditionalEntity {

    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "realestate_additional_ready_to_move_in")
    @NotNull(message = "code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "realestate_additional_ready_to_move_in")
    @JsonProperty("code")
    @Column(name = "code", nullable = false)
    private String code;

    @Schema(example = "Odmah useljiv")
    @NotNull(message = "name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Odmah useljiv")
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;
}

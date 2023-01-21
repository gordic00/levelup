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
@Table(schema = "level_up", name = "type")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class TypeEntity {

    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "Stan")
    @NotNull(message = "title cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "stan")
    @JsonProperty("title")
    @Column(name = "title", nullable = false)
    private String title;
}

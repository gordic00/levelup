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
@Table(schema = "level_up", name = "payment_type")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class PaymentTypeEntity {

    @Schema(example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;

    @Schema(example = "payment_type_monthly")
    @NotNull(message = "code cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "payment_type_monthly")
    @JsonProperty("code")
    @Column(name = "code", nullable = false)
    private String code;

    @Schema(example = "Mesecno")
    @NotNull(message = "name cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "Mesecno")
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;
}

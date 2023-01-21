package com.webapps.levelup.model.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(schema = "level_up", name = "user")
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", length = 11)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("email")
    @Column(name = "email")
    private String email;
}

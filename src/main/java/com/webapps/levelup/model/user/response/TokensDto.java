package com.webapps.levelup.model.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class TokensDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("token")
    String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("refresh_token")
    String refreshToken;
}

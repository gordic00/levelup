package com.webapps.levelup.controller.user;

import com.webapps.levelup.configuration.TokenAuth;
import com.webapps.levelup.model.user.response.TokensDto;
import com.webapps.levelup.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping(path = "api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping(path = "/login")
    public ResponseEntity<TokensDto> logIn(
            @RequestParam(value = "username")
            String username,
            @RequestParam(value = "password")
            String password
    ) {
        return service.login(username, password);
    }

    @TokenAuth
    @PostMapping(path = "/reset-token")
    public ResponseEntity<TokensDto> resetToken(
            HttpServletRequest request,
            @RequestParam(value = "token")
            @NotNull(message = "Token be null")
            String token) {
        return service.resetToken(token);
    }
}

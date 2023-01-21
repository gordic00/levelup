package com.webapps.levelup.service.user;

import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.helper.TokenHelper;
import com.webapps.levelup.model.user.request.UserEntity;
import com.webapps.levelup.model.user.response.TokensDto;
import com.webapps.levelup.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repo;
    private final TokenHelper tokenHelper;

    public ResponseEntity<TokensDto> login(String email, String password) {
        Optional<UserEntity> user = repo.findByEmail(email);
        if (user.isEmpty()) {
            log.info("Invalid email {}.", email);
            throw new CustomException("User with email: " + email + " does not exist.");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            log.info("Password is invalid for customer {}.", user.get());
            throw new CustomException("You have entered an invalid username or password");
        }

        return ResponseEntity.ok(createSecurityTokens(user.get()));
    }

    private TokensDto createSecurityTokens(UserEntity userEntity) {
        TokensDto tokens = new TokensDto();
        tokens.setToken(tokenHelper.getToken(userEntity));
        tokens.setRefreshToken(tokenHelper.getRefreshToken(userEntity));
        return tokens;
    }

    public ResponseEntity<TokensDto> resetToken(String refreshToken) {
        String email = tokenHelper.getEmailFromToken(refreshToken);
        Optional<UserEntity> user = repo.findByEmail(email);
        if (user.isEmpty()) {
            log.info("Invalid email {}.", email);
            throw new CustomException("User with email: " + email + " does not exist.");
        }
        return ResponseEntity.ok(createSecurityTokens(user.get()));
    }
}

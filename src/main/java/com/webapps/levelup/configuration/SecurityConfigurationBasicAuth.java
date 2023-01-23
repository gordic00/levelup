package com.webapps.levelup.configuration;

import com.webapps.levelup.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.boot.actuate.flyway.FlywayEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LogFileWebEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.session.SessionsEndpoint;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigurationBasicAuth {
    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Auth
        EndpointRequest.EndpointRequestMatcher rmAuth = EndpointRequest.to(
                MappingsEndpoint.class,
                LoggersEndpoint.class,
                LogFileWebEndpoint.class,
                BeansEndpoint.class,
                CachesEndpoint.class,
                EnvironmentEndpoint.class,
                FlywayEndpoint.class,
                InfoEndpoint.class,
                SessionsEndpoint.class
        );

        http.csrf().disable()
                .authorizeHttpRequests((auths) -> {
                            try {
                                auths
                                        .antMatchers("/**").permitAll()
                                        .requestMatchers(rmAuth).authenticated()
                                        .anyRequest().permitAll()
                                        .and().cors()
                                        .and().httpBasic()
                                        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new CustomException(e.getMessage());
                            }
                        }
                ).httpBasic(withDefaults());
        return http.build();
    }

    /**
     * CORS configuration.
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList(appProperties.getSecurityMethods()));
        configuration.setAllowedHeaders(Arrays.asList(appProperties.getSecurityHeaders()));
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

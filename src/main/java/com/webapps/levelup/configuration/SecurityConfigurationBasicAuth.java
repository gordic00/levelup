package com.webapps.levelup.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigurationBasicAuth {
    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Auth
//        EndpointRequest.EndpointRequestMatcher rmAuth = EndpointRequest.to(
//                MappingsEndpoint.class,
//                LoggersEndpoint.class,
//                LogFileWebEndpoint.class,
//                BeansEndpoint.class,
//                CachesEndpoint.class,
//                EnvironmentEndpoint.class,
//                FlywayEndpoint.class,
//                InfoEndpoint.class,
//                SessionsEndpoint.class
//        );
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().cors();

//        http.csrf().disable()
//                .authorizeHttpRequests((auths) -> {
//                            try {
//                                auths
//                                        .antMatchers("/**").permitAll()
//                                        .requestMatchers(rmAuth).permitAll()
//                                        .anyRequest().permitAll()
//                                        .and().cors()
//                                        .and().httpBasic()
//                                        .and().sessionManagement()
//                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                            } catch (Exception e) {
//                                throw new CustomException(e.getMessage());
//                            }
//                        }
//                ).httpBasic(withDefaults());
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
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowCredentials(false);
        configuration.setAllowedMethods(Arrays.asList(appProperties.getSecurityMethods()));
        configuration.setAllowedHeaders(Arrays.asList(appProperties.getSecurityHeaders()));
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

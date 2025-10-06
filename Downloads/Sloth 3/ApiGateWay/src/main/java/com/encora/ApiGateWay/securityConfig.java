package com.encora.ApiGateWay;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class securityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();

        http.authorizeExchange()
            // Admin can access all endpoints
            .pathMatchers("/**").hasRole("ADMIN")

            // User role access GET requests and place/cancel order
            .pathMatchers(HttpMethod.GET, "/productservices/**", "/customerservice/**").hasAnyRole("USER", "ADMIN")
            .pathMatchers(HttpMethod.POST, "/customerservice/placeorder", "/customerservice/cancelorder/**").hasAnyRole("USER", "ADMIN")
            
            // Deny other requests for USER role
            .anyExchange().denyAll()

            .and()
            .httpBasic();

        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin123")
            .roles("ADMIN")
            .build();

        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("user123")
            .roles("USER")
            .build();

        return new MapReactiveUserDetailsService(admin, user);
    }
}

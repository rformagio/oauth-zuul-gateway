package br.com.rformagio.person;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
//@EnableResourceServer
@RestController
@EnableDiscoveryClient
@EnableCircuitBreaker
@Slf4j
public class PersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
/*
    @GetMapping("/api/v1/test")
    public String resource(@AuthenticationPrincipal Jwt jwt) {
        log.trace("***** JWT Headers: {}", jwt.getHeaders());
        log.trace("***** JWT Claims: {}", jwt.getClaims().toString());
        log.trace("***** JWT Token: {}", jwt.getTokenValue());
        return String.format("Resource accessed by: %s (with subjectId: %s)" ,
                jwt.getClaims().get("user_name"),
                jwt.getSubject());
    }
*/
}

package com.fercevik.programservice;

import com.fercevik.tokenlib.KeycloakJwtRolesConverter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Data
@EnableWebFluxSecurity
@SpringBootApplication(scanBasePackages = {"com.fercevik.programservice", "com.fercevik.tokenlib"})
public class ProgramServiceApplication {

	private final KeycloakJwtRolesConverter converter;
	private static final String USER_ROLE = "SCOPE_openid";
	public static void main(String[] args) {
		SpringApplication.run(ProgramServiceApplication.class, args);
	}
}

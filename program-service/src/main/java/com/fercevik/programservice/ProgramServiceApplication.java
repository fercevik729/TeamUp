package com.fercevik.programservice;

import com.fercevik.tokenlib.KeycloakJwtRolesConverter;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Data
@EnableWebSecurity
@SpringBootApplication(scanBasePackages = {"com.fercevik.programservice", "com.fercevik.tokenlib"})
public class ProgramServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramServiceApplication.class, args);
	}
}

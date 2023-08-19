package com.fercevik.tokenlib;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "converter")
public class ConverterProperties {
    private String resourceId;
    private String principalAttribute;
}

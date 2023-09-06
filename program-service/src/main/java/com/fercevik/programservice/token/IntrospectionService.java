package com.fercevik.programservice.token;

import com.fercevik.programservice.config.KeycloakProperties;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@Service
public class IntrospectionService {
    private final IntrospectionClient feignClient;
    private final KeycloakProperties properties;

    /**
     * Introspects an opaque token sent by a user and extracts its claim
     *
     * @param token an opaque token as a string
     * @return a UserInfoDTO instance containing the extracted claims
     */
    public UserInfoDTO introspect(String token) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        // TODO: filter this data out of the logs
        formData.add("client_id", properties.getClientId());
        formData.add("client_secret", properties.getClientSecret());
        formData.add("token", token);

        return feignClient.introspect(formData);
    }

}

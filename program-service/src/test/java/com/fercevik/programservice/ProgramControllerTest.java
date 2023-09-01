package com.fercevik.programservice;


import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.controller.ProgramController;
import com.fercevik.programservice.services.OpaqueTokenService;
import com.fercevik.programservice.services.ProgramService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProgramController.class)
public class ProgramControllerTest {

    @MockBean
    ProgramService programService;

    @MockBean
    OpaqueTokenService opaqueTokenService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenRequestIsAnonymous_whenGetEcho_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/echo").with(anonymous())).andExpect(status().isUnauthorized());
    }

    @Test
    void givenUserIsAuthenticated_whenGetEcho_thenOk() throws Exception {
        var message = "you have reached a secure endpoint";

        //when(opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE)).thenReturn(true);
        mockMvc.perform(get("/echo").with(opaqueToken().authorities())).andExpect(status().isOk())
                .andExpect(content().string(message));

    }
}

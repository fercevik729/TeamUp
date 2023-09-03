package com.fercevik.programservice;


import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.controller.ProgramController;
import com.fercevik.programservice.services.ProgramService;
import com.fercevik.programservice.token.UserInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProgramController.class)
public class ProgramControllerTest {

    @MockBean
    ProgramService programService;

    @Autowired
    MockMvc mockMvc;


    @Test
    void givenRequestIsAnonymous_whenGetEcho_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/programs/echo").with(anonymous())).andExpect(status().isUnauthorized());
    }

    @Test
    void givenUserIsAuthenticated_whenGetEcho_thenOk() throws Exception {
        var message = "You have reached a secure endpoint.";

        mockMvc.perform(get("/programs/echo").with(
                        opaqueToken().authorities(List.of(new SimpleGrantedAuthority(KeycloakConstants.USER_ROLE)))))
                .andExpect(status().isOk()).andExpect(content().string(message));

    }

    @Test
    void givenUserIsAnonymous_whenGetAllPrograms_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/programs").with(anonymous())).andExpect(status().isUnauthorized());
    }

    @Test
    void givenUserIsAuthenticated_whenGetAllPrograms_thenOk() throws Exception {
        // Create a mock principal
        var principal = UserUtils.createMockUser();
        var emptyPrograms = "[]";
        mockMvc.perform(get("/programs").with(opaqueToken().principal(principal))).andExpect(status().isOk())
                .andExpect(content().string(emptyPrograms));
    }

    @Test
    void givenUserIsAdmin_whenGetAllPrograms_thenOk() throws Exception {
        var principal = UserUtils.createMockAdmin();
        var emptyPrograms = "[]";
        System.out.println(principal.getAuthorities());
        mockMvc.perform(get("/programs").with(opaqueToken().principal(principal))).andExpect(status().isOk())
                .andExpect(content().string(emptyPrograms));
    }


}

class UserUtils {
    static UserInfoDTO createMockUser() {
        var principal = new UserInfoDTO();
        var realmRoles = new HashMap<String, List<String>>();
        realmRoles.put("roles", List.of(KeycloakConstants.USER_ROLE));
        principal.setSub(UUID.randomUUID().toString());
        principal.setRealmAccess(realmRoles);
        principal.setScope("email");
        return principal;
    }

    static UserInfoDTO createMockAdmin() {
        var principal = new UserInfoDTO();
        var realmRoles = new HashMap<String, List<String>>();
        realmRoles.put("roles", List.of(KeycloakConstants.ADMIN_ROLE, KeycloakConstants.USER_ROLE));
        principal.setSub(UUID.randomUUID().toString());
        principal.setRealmAccess(realmRoles);
        principal.setScope("email");
        return principal;
    }
}

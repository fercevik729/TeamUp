package com.fercevik.programservice;


import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.controller.ProgramController;
import com.fercevik.programservice.dto.ProgramDTO;
import com.fercevik.programservice.exceptions.ProgramAlreadyExistsException;
import com.fercevik.programservice.exceptions.ProgramNotFoundException;
import com.fercevik.programservice.services.ProgramService;
import com.fercevik.programservice.utils.RepoUtils;
import com.fercevik.programservice.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProgramController.class)
@TestPropertySource(properties = "spring.cloud.vault.enabled=false")
@ActiveProfiles("test")
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
        mockMvc.perform(get("/programs").with(opaqueToken().principal(principal))).andExpect(status().isOk())
                .andExpect(content().string(emptyPrograms));
    }

    @Test
    void givenUserIsAuthenticated_whenCreateProgram_thenThrowsAlreadyExists() throws Exception {
        var principal = UserUtils.createMockUser();
        UUID ownerId = UUID.fromString(principal.getSub());

        ProgramDTO program = RepoUtils.createProgramDTO();
        String jsonReq = RepoUtils.asJSONStr(program);

        Mockito.doThrow(new ProgramAlreadyExistsException("program with the same id already exists"))
                .when(programService).createProgram(ownerId, program);
        mockMvc.perform(post("/programs").contentType(MediaType.APPLICATION_JSON).content(jsonReq)
                        .with(opaqueToken().principal(principal))).andExpect(status().isBadRequest()).andExpect(
                        result -> assertTrue((result.getResolvedException() instanceof ProgramAlreadyExistsException)))
                .andExpect(result -> assertEquals("program with the same id already exists",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void givenUserIsAuthenticated_whenCreateProgram_thenThrowsInvalidArgs() throws Exception {
        var principal = UserUtils.createMockUser();

        var set = RepoUtils.createSetDTO();
        String jsonReq = RepoUtils.asJSONStr(set);
        mockMvc.perform(post("/programs").contentType(MediaType.APPLICATION_JSON).content(jsonReq)
                .with(opaqueToken().principal(principal))).andExpect(status().isBadRequest()).andExpect(
                result -> assertTrue((result.getResolvedException() instanceof MethodArgumentNotValidException)));
    }

    @Test
    void givenUserIsAuthenticated_whenGetNonexistentProgramThrows() throws Exception {
        var principal = UserUtils.createMockUser();
        UUID ownerId = UUID.fromString(principal.getSub());

        Mockito.doThrow(new ProgramNotFoundException("no active program could be found for user")).when(programService)
                .getActiveProgram(ownerId);
        mockMvc.perform(get("/programs/active").with(opaqueToken().principal(principal)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProgramNotFoundException))
                .andExpect(result -> assertEquals("no active program could be found for user",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }


}


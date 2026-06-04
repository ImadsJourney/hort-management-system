package com.schoolms.school_management.auth;

import com.schoolms.school_management.auth.dto.AuthResponse;
import com.schoolms.school_management.auth.dto.LoginRequest;
import com.schoolms.school_management.auth.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AuthService authService;

  @Test
  void shouldRegisterUser() throws Exception {
    RegisterRequest request = new RegisterRequest("max", "password123");

    AuthResponse response = new AuthResponse(
        1L,
        "max",
        "fake-jwt-token",
        "Registration successful");

    when(authService.register(any(RegisterRequest.class))).thenReturn(response);

    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.username").value("max"))
        .andExpect(jsonPath("$.message").value("Registration successful"));
  }

  @Test
  void shouldLoginUser() throws Exception {
    LoginRequest request = new LoginRequest("max", "password123");

    AuthResponse response = new AuthResponse(
        1L,
        "max",
        "fake-jwt-token",
        "Login successful");

    when(authService.login(any(LoginRequest.class))).thenReturn(response);

    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.username").value("max"))
        .andExpect(jsonPath("$.message").value("Login successful"));
  }

  @Test
  void shouldReturnBadRequestWhenRegisterRequestIsInvalid() throws Exception {
    RegisterRequest request = new RegisterRequest("", "password123");

    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(authService);
  }

  @Test
  void shouldReturnBadRequestWhenLoginRequestIsInvalid() throws Exception {
    LoginRequest request = new LoginRequest("", "password123");

    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(authService);
  }
}

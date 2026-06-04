package com.schoolms.school_management.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.schoolms.school_management.hortgroup.HortGroupController;
import com.schoolms.school_management.hortgroup.HortGroupService;
import com.schoolms.school_management.hortgroup.dto.CreateHortGroupRequest;
import com.schoolms.school_management.hortgroup.dto.HortGroupResponse;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(HortGroupController.class)
@AutoConfigureMockMvc(addFilters = false)
class HortGroupControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private HortGroupService hortGroupService;

  @Test
  void shouldCreateGroup() throws Exception {
    CreateHortGroupRequest request = new CreateHortGroupRequest(
        "ALöcher",
        "3. Klasse",
        "Herr Müller");

    HortGroupResponse response = createGroupResponse();

    when(hortGroupService.createGroup(any(CreateHortGroupRequest.class))).thenReturn(response);

    mockMvc.perform(post("/groups")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("ALöcher"))
        .andExpect(jsonPath("$.gradeLevel").value("3. Klasse"))
        .andExpect(jsonPath("$.supervisorName").value("Herr Müller"));
  }

  @Test
  void shouldReturnAllGroups() throws Exception {
    HortGroupResponse response = createGroupResponse();

    when(hortGroupService.getAllGroups()).thenReturn(List.of(response));

    mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("ALöcher"))
        .andExpect(jsonPath("$[0].gradeLevel").value("3. Klasse"))
        .andExpect(jsonPath("$[0].supervisorName").value("Herr Müller"));
  }

  @Test
  void shouldReturnBadRequestWhenGroupNameIsBlank() throws Exception {
    CreateHortGroupRequest request = new CreateHortGroupRequest(
        "",
        "3. Klasse",
        "Herr Müller");

    mockMvc.perform(post("/groups")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(hortGroupService);
  }

  private HortGroupResponse createGroupResponse() {
    return new HortGroupResponse(
        1L,
        "ALöcher",
        "3. Klasse",
        "Herr Müller");
  }
}

package com.schoolms.school_management.child;

import com.schoolms.school_management.child.dto.ChildResponse;
import com.schoolms.school_management.child.dto.CreateChildRequest;
import com.schoolms.school_management.child.dto.UpdateAttendanceRequest;
import com.schoolms.school_management.child.dto.UpdateNotesRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChildController.class)
class ChildControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ChildService childService;

  @Test
  void shouldCreateChild() throws Exception {
    CreateChildRequest request = new CreateChildRequest(
        "Emma",
        "Schmidt",
        "Wird um 15 Uhr abgeholt",
        1L);

    ChildResponse response = createChildResponse();

    when(childService.createChild(any(CreateChildRequest.class))).thenReturn(response);

    mockMvc.perform(post("/children")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("Emma"))
        .andExpect(jsonPath("$.lastName").value("Schmidt"))
        .andExpect(jsonPath("$.attendanceStatus").value("NOT_RECORDED"))
        .andExpect(jsonPath("$.notes").value("Wird um 15 Uhr abgeholt"))
        .andExpect(jsonPath("$.hortGroupId").value(1))
        .andExpect(jsonPath("$.hortGroupName").value("Bärengruppe"));
  }

  @Test
  void shouldReturnAllChildren() throws Exception {
    ChildResponse response = createChildResponse();

    when(childService.getAllChildren()).thenReturn(List.of(response));

    mockMvc.perform(get("/children"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].firstName").value("Emma"))
        .andExpect(jsonPath("$[0].lastName").value("Schmidt"))
        .andExpect(jsonPath("$[0].attendanceStatus").value("NOT_RECORDED"))
        .andExpect(jsonPath("$[0].hortGroupName").value("Bärengruppe"));
  }

  @Test
  void shouldReturnChildrenByGroup() throws Exception {
    ChildResponse response = createChildResponse();

    when(childService.getChildrenByGroupId(1L)).thenReturn(List.of(response));

    mockMvc.perform(get("/groups/1/children"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].firstName").value("Emma"))
        .andExpect(jsonPath("$[0].hortGroupId").value(1))
        .andExpect(jsonPath("$[0].hortGroupName").value("Bärengruppe"));
  }

  @Test
  void shouldUpdateAttendance() throws Exception {
    UpdateAttendanceRequest request = new UpdateAttendanceRequest(AttendanceStatus.PRESENT);

    ChildResponse response = new ChildResponse(
        1L,
        "Emma",
        "Schmidt",
        AttendanceStatus.PRESENT,
        "Wird um 15 Uhr abgeholt",
        1L,
        "Bärengruppe");

    when(childService.updateAttendance(eq(1L), any(UpdateAttendanceRequest.class)))
        .thenReturn(response);

    mockMvc.perform(patch("/children/1/attendance")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.attendanceStatus").value("PRESENT"));
  }

  @Test
  void shouldUpdateNotes() throws Exception {
    UpdateNotesRequest request = new UpdateNotesRequest("Neue Notiz");

    ChildResponse response = new ChildResponse(
        1L,
        "Emma",
        "Schmidt",
        AttendanceStatus.NOT_RECORDED,
        "Neue Notiz",
        1L,
        "Bärengruppe");

    when(childService.updateNotes(eq(1L), any(UpdateNotesRequest.class)))
        .thenReturn(response);

    mockMvc.perform(patch("/children/1/notes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.notes").value("Neue Notiz"));
  }

  @Test
  void shouldReturnBadRequestWhenCreateChildIsInvalid() throws Exception {
    CreateChildRequest request = new CreateChildRequest(
        "",
        "Schmidt",
        "Wird um 15 Uhr abgeholt",
        1L);

    mockMvc.perform(post("/children")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(childService);
  }

  private ChildResponse createChildResponse() {
    return new ChildResponse(
        1L,
        "Emma",
        "Schmidt",
        AttendanceStatus.NOT_RECORDED,
        "Wird um 15 Uhr abgeholt",
        1L,
        "Bärengruppe");
  }
}

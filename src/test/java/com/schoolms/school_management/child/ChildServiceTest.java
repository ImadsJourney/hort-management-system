package com.schoolms.school_management.child;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.schoolms.school_management.child.dto.ChildResponse;
import com.schoolms.school_management.child.dto.CreateChildRequest;
import com.schoolms.school_management.child.dto.UpdateAttendanceRequest;
import com.schoolms.school_management.child.dto.UpdateNotesRequest;
import com.schoolms.school_management.hortgroup.HortGroup;
import com.schoolms.school_management.hortgroup.HortGroupRepository;

/**
 * ChildServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class ChildServiceTest {
  @Mock
  private ChildRepository childRepository;

  @Mock
  private HortGroupRepository hortGroupRepository;

  @InjectMocks
  private ChildService childService;

  private HortGroup createTestGroup() {
    HortGroup group = new HortGroup();
    group.setId(1L);
    group.setName("Bärengruppe");
    group.setGradeLevel("1. Klasse");
    group.setSupervisorName("Frau Müller");
    return group;
  }

  private Child createTestChild(HortGroup group) {
    Child child = new Child();
    child.setId(1L);
    child.setFirstName("Emma");
    child.setLastName("Schmidt");
    child.setNotes("Wird um 15 Uhr abgeholt");
    child.setAttendanceStatus(AttendanceStatus.NOT_RECORDED);
    child.setHortGroup(group);
    return child;
  }

  @Test
  void shouldCreateChild() {
    CreateChildRequest request = new CreateChildRequest(
        "Emma",
        "Schmidt",
        "Wird um 15 Uhr abgeholt",
        1L);

    HortGroup group = createTestGroup();
    Child savedChild = createTestChild(group);

    when(hortGroupRepository.findById(1L)).thenReturn(Optional.of(group));
    when(childRepository.save(any(Child.class))).thenReturn(savedChild);

    ChildResponse response = childService.createChild(request);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.firstName()).isEqualTo("Emma");
    assertThat(response.lastName()).isEqualTo("Schmidt");
    assertThat(response.notes()).isEqualTo("Wird um 15 Uhr abgeholt");
    assertThat(response.attendanceStatus()).isEqualTo(AttendanceStatus.NOT_RECORDED);
    assertThat(response.hortGroupId()).isEqualTo(1L);
    assertThat(response.hortGroupName()).isEqualTo("Bärengruppe");

    verify(hortGroupRepository).findById(1L);
    verify(childRepository).save(any(Child.class));
  }

  @Test
  void shouldUpdateAttendance() {
    HortGroup group = createTestGroup();
    Child child = createTestChild(group);

    UpdateAttendanceRequest request = new UpdateAttendanceRequest(AttendanceStatus.PRESENT);

    when(childRepository.findById(1L)).thenReturn(Optional.of(child));
    when(childRepository.save(any(Child.class))).thenReturn(child);

    ChildResponse response = childService.updateAttendance(1L, request);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.attendanceStatus()).isEqualTo(AttendanceStatus.PRESENT);

    verify(childRepository).findById(1L);
    verify(childRepository).save(any(Child.class));
  }

  @Test
  void shouldUpdateNotes() {
    HortGroup group = createTestGroup();
    Child child = createTestChild(group);

    UpdateNotesRequest request = new UpdateNotesRequest("Neue Notiz");

    when(childRepository.findById(1L)).thenReturn(Optional.of(child));
    when(childRepository.save(any(Child.class))).thenReturn(child);

    ChildResponse response = childService.updateNotes(1L, request);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.notes()).isEqualTo("Neue Notiz");

    verify(childRepository).findById(1L);
    verify(childRepository).save(any(Child.class));
  }
}

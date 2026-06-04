package com.schoolms.school_management.child;

import com.schoolms.school_management.child.dto.ChildResponse;
import com.schoolms.school_management.child.dto.CreateChildRequest;
import com.schoolms.school_management.child.dto.UpdateAttendanceRequest;
import com.schoolms.school_management.child.dto.UpdateNotesRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChildController {

  private final ChildService childService;

  @PostMapping("/children")
  @ResponseStatus(HttpStatus.CREATED)
  public ChildResponse createChild(@Valid @RequestBody CreateChildRequest request) {
    return childService.createChild(request);
  }

  @GetMapping("/children")
  public List<ChildResponse> getAllChildren() {
    return childService.getAllChildren();
  }

  @GetMapping("/groups/{groupId}/children")
  public List<ChildResponse> getChildrenByGroup(@PathVariable Long groupId) {
    return childService.getChildrenByGroupId(groupId);
  }

  @PatchMapping("/children/{id}/attendance")
  public ChildResponse updateAttendance(
      @PathVariable Long id,
      @Valid @RequestBody UpdateAttendanceRequest request) {

    return childService.updateAttendance(id, request);
  }

  @PatchMapping("/children/{id}/notes")
  public ChildResponse updateNotes(
      @PathVariable Long id,
      @Valid @RequestBody UpdateNotesRequest request) {
    return childService.updateNotes(id, request);
  }

}

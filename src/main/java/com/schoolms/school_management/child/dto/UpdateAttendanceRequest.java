package com.schoolms.school_management.child.dto;

import com.schoolms.school_management.child.AttendanceStatus;

import jakarta.validation.constraints.NotNull;

/**
 * UpdateAttendanceRequest
 * This is for updating the attendanceRequest later on,
 * if the teacher chose to change it from default or to change it in general
 *
 */
public record UpdateAttendanceRequest(
    @NotNull(message = "Attendance status is required!") AttendanceStatus attendanceStatus) {
}

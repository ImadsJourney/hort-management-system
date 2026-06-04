package com.schoolms.school_management.child;

import com.schoolms.school_management.hortgroup.HortGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Child
 */

@Entity
@Table(name = "children")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Child {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private AttendanceStatus attendanceStatus = AttendanceStatus.NOT_RECORDED;

  @Column(length = 1000)
  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hort_group_id", nullable = false)
  private HortGroup hortGroup;

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public AttendanceStatus getAttendanceStatus() {
    return attendanceStatus;
  }

  public String getNotes() {
    return notes;
  }

  public HortGroup getHortGroup() {
    return hortGroup;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
    this.attendanceStatus = attendanceStatus;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setHortGroup(HortGroup hortGroup) {
    this.hortGroup = hortGroup;
  }

}

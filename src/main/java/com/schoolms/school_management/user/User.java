package com.schoolms.school_management.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * User
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  /**
   * Aus irgendeinem Grund erkennt mein LSP nicht das ich @Setter benutze und
   * deshalb werden die Setter mir nicht angezeigt in UserService.java
   * Sprich ich musste sie doch jetzt aufschreiben ;;;;)
   *
   */

  public void setId(Long id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

}

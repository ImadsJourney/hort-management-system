package com.schoolms.school_management.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController
 */

@RestController
public class HealthController {

  @GetMapping("/health")
  public String sayHello() {
    return "Backend läuft.";
  }

}

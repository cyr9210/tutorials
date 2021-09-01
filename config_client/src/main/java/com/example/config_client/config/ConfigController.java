package com.example.config_client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ConfigController {

  private final ConfigProperties configProperties;

  @GetMapping("/config")
  public ResponseEntity getConfig() {
    return ResponseEntity.ok(configProperties.getMessage());
  }

}

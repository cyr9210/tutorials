package com.example.config_client.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "custom")
@RefreshScope
@Getter
@Component
public class ConfigProperties {

  private String message;

}

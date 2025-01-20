package com.diginamic.mission_note_de_frais;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is used to store environment variables that are read from the application.properties file.
 * The prefix "app.env" should be used first before the variable name.
 */
@Component
@ConfigurationProperties(prefix = "app.env")
@Getter
@Setter
@ToString
public class EnvironmentVariables {
  private String jwtSecret;
  private Long jwtExpirySeconds;
}

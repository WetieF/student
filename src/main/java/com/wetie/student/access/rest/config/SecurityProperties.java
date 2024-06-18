package com.wetie.student.access.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public record SecurityProperties(String username, String password) {
}

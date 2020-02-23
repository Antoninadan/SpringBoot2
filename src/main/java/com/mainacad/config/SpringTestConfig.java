package com.mainacad.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.lang.management.ManagementFactory;

@Configuration
@EnableAutoConfiguration(exclude= {FlywayAutoConfiguration.class, SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@Profile("test")
public class SpringTestConfig {

}

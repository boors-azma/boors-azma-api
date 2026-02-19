package com.ernoxin.boorsazmaapi;

import com.ernoxin.boorsazmaapi.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@PropertySource(value = "file://${CONFIG}/properties/boors-azma-api.properties", ignoreResourceNotFound = true)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

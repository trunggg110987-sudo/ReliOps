package com.smarthomerentalplatform;

import com.smarthomerentalplatform.configuration.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SmartHomeRentalPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeRentalPlatformApplication.class, args);
    }

}

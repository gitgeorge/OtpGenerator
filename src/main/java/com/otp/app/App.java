package com.otp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.otp.configurations.OtpProperties;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.otp"})
@EnableConfigurationProperties(OtpProperties.class)
@EntityScan("com.otp.entity")
@EnableJpaRepositories("com.otp.dao")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

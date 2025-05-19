package com.Security.demo;

import com.Security.demo.Utils.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SecurityTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityTemplateApplication.class, args);
	}

}

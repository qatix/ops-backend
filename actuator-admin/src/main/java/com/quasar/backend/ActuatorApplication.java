package com.quasar.backend;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableAdminServer
@SpringBootApplication
public class ActuatorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ActuatorApplication.class);
	}
}

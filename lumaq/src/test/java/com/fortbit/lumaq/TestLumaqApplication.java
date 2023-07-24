package com.fortbit.lumaq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestLumaqApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>("postgres:latest");
	}

	@Bean
	@ServiceConnection
	RabbitMQContainer rabbitContainer() {
		return new RabbitMQContainer("rabbitmq:latest");
	}

	@Bean
	@ServiceConnection(name = "redis")
	GenericContainer<?> redisContainer() {
		return new GenericContainer<>("redis:latest").withExposedPorts(6379);
	}

	public static void main(String[] args) {
		SpringApplication.from(LumaqApplication::main).with(TestLumaqApplication.class).run(args);
	}

}

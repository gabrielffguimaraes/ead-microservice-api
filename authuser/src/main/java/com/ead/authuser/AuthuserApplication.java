package com.ead.authuser;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthuserApplication {
	@Value("${teste}")
	private String teste;

	public static void main(String[] args) {
		SpringApplication.run(AuthuserApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			log.info(teste);
		};
	}
	// teste
}

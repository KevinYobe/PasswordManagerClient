package com.passwordmanager.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.passwordmanager.client.config.AppConfig;

@Import(AppConfig.class)
@SpringBootApplication
public class PasswordmanagerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordmanagerClientApplication.class, args);
	}

}

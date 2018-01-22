package br.com.fr.cupuama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application to run boot application.
 * 
 */
@SpringBootApplication
@ComponentScan(basePackages = {"br.com.fr.cupuama"})
@EnableScheduling
public class Application {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
}